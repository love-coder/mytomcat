package org.xidian.mytomcat.container;

import org.xidian.mytomcat.connector.Request;
import org.xidian.mytomcat.connector.Response;

/**
 * 容器基本功能
 * @author HanChun
 * @version 1.0 2016-5-14
 */
public interface Container {

	/**
	 * 掉用相应的 Servlet
	 * @param request
	 * @param response
	 */
	public void invoke(Request request, Response response);
	
	/**
	 * 启动容器
	 */
	public void start();
	
	/**
	 * 找到对应的Servlet类
	 * @param name 映射路径值
	 * @return 类名
	 */
	public String findServlet(String name);
	
}
