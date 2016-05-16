package org.hanchun.mytomcat.connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.hanchun.mytomcat.container.Container;

/**
 * @author HanChun
 * @version 1.0 2016-5-14
 */
public class HttpConnector implements Runnable {

	// shutdown 命令
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	private boolean shutdown = false;

	private Container container;

	public void run() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			//监听8080端口  
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

				//创建request，并且对socket的input进行处理。获取uri信息
				Request request = new Request(input);
				request.parse();

				//创建response
				Response response = new Response(output);
				response.setRequest(request);

				String uri = request.getUri();
				
				//判断该uri请求是否为我们配置的servlet，如果是则调用该servlet的service方法
				if (container!=null && container.findServlet(uri)!=null) {
					container.invoke(request, response);
				}else{
					//如果不是servlet，则调用静态资源方法处理请求
					response.sendStaticResource();
				}
				
				socket.close();
				
				//浏览器中输入http://localhost:8080/SHUTDOWN
				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	public Container getContainer() {
		return container;
	}
	public void setContainer(Container container) {
		this.container = container;
	}
	
	public void start(){
		long now=System.currentTimeMillis();
		
		Thread thread=new Thread(this);
		thread.start();
		
		System.out.println("启动连接耗时："+(System.currentTimeMillis()-now)+"ms");
	}
}