package org.xidian.main;

import java.io.File;

import org.xidian.mytomcat.connector.HttpConnector;
import org.xidian.mytomcat.container.Container;
import org.xidian.mytomcat.container.SimpleContainer;
import org.xidian.utils.ConfigUtil;
import org.xidian.utils.Constants;
import org.xidian.utils.WarUtil;

/**
 * 入口
 * @author HanChun
 * @version 1.0 2016-5-14
 */
public class Main {

	public static void main(String[] args) {
		
//		//1.解压war包到mytomcat的webapp下
//		WarUtil.unzip(Constants.WEB_APPS+ConfigUtil.getConfig("war_name"), Constants.WEB_APPS +
//				File.separator + ConfigUtil.getConfig("warname"));
		//2.启动容器 .
		Container simpleContainer = new SimpleContainer();
		simpleContainer.start();
		//3.在服务端启动监听
		HttpConnector connector = new HttpConnector();
		connector.setContainer(simpleContainer);
		connector.start();
	}
}
