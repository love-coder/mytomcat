package org.hanchun.mytomcat.container;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hanchun.mytomcat.connector.Request;
import org.hanchun.mytomcat.connector.Response;
import org.hanchun.utils.Constants;
import org.hanchun.utils.DOMParser;

/**
 * @author HanChun
 * @version 1.0 2016-5-14
 */
public class SimpleContainer implements Container{

	//web.xml映射信息
	private Map<String,String> servletMapping;

	public void invoke(Request request, Response response) {

		String uri = request.getUri();
		String servletClassName = findServlet(uri);
		
		//加载servlet的class，创建servlet对象
		URLClassLoader loader = null;
		try {
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			//File classPath = new File(Constants.WEB_ROOT);
			File classPath = new File(Constants.WEB_CLASSES_ROOT);
			String repository = (new URL("file", null, classPath.getCanonicalPath()
					+ File.separator)).toString();
			urls[0] = new URL(null, repository, streamHandler);
			loader = new URLClassLoader(urls);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		Class servletClass = null;
		try {
			servletClass = loader.loadClass(servletClassName);
		} catch (ClassNotFoundException e) {
			System.out.println("没有找到相应的Servlet");
			e.printStackTrace();
		}

		Servlet servlet = null;
		try {
			servlet = (Servlet) servletClass.newInstance();
			servlet.service((ServletRequest) request,(ServletResponse) response);
		} catch (Exception e) {
			e.printStackTrace();
			//TODO 
		} catch (Throwable e) {
			e.printStackTrace();
			//TODO 
		}
	}

	public void start() {
		long now=System.currentTimeMillis();
		//web.xml映射
		servletMapping = DOMParser.getServletMapping();
		if(servletMapping != null){
			System.out.println("启动容器:"+(System.currentTimeMillis()-now)+"ms");
		}
	}
	
	public String findServlet(String name){
		return servletMapping.get(name);
	}
	
}
