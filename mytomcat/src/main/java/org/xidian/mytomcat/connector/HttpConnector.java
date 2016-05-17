package org.xidian.mytomcat.connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.xidian.mytomcat.container.Container;
import org.xidian.utils.ConfigUtil;

/**
 * 连接器类
 * @author HanChun
 * @version 1.0 2016-5-14
 */
public class HttpConnector implements Runnable {

	/** shutdown 命令 */
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;
	private Container container;

	/**
	 * 
	 */
	public void run() {
		ServerSocket serverSocket = null;
		int port = Integer.parseInt(ConfigUtil.getConfig("port"));
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			System.out.println("服务器启动失败！");
			e.printStackTrace();
			System.exit(1);
		}
		while (!shutdown) {
			//获取连接进来的Client socket
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				//创建request
				Request request = new Request(input);
				request.parse();
				//创建response
				Response response = new Response(output);
				response.setRequest(request);
				String uri = request.getUri();
				//判断该uri是否已配置
				if (container!=null && container.findServlet(uri)!=null) {
					container.invoke(request, response);
				}else{
					response.sendStaticResource();
				}
				socket.close();
				//浏览器中输入http://localhost:8080/SHUTDOWN
				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
			} catch (Exception e) {
				System.err.println("在" + ConfigUtil.getConfig("port") + "端口监听失败！");
				e.printStackTrace();
				continue;
			}
		}
	}
	
	/**
	 * 启动监听
	 */
	public void start(){
		long now=System.currentTimeMillis();
		Thread thread=new Thread(this);
		thread.start();
		System.out.println("启动连接耗时："+(System.currentTimeMillis()-now)+"ms");
	}
	
	/**
	 * get容器
	 * @return 容器
	 */
	public Container getContainer() {
		return container;
	}
	
	/**
	 * set容器
	 * @param container
	 */
	public void setContainer(Container container) {
		this.container = container;
	}
}