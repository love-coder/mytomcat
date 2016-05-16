package org.hanchun.main;

import org.hanchun.mytomcat.connector.HttpConnector;
import org.hanchun.mytomcat.container.Container;
import org.hanchun.mytomcat.container.SimpleContainer;

/**
 * 入口
 * @author HanChun
 * @version 1.0 2016-5-14
 */
public class Main {

	public static void main(String[] args) {
		
		//1.启动容器 .
		Container simpleContainer = new SimpleContainer();
		simpleContainer.start();
		//2.在服务端启动监听
		HttpConnector connector = new HttpConnector();
		connector.setContainer(simpleContainer);
		connector.start();
		
	}
}