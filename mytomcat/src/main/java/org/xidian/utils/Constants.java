package org.xidian.utils;

import java.io.File;

/**
 * 一些常量
 * @author HanChun
 * @version 1.0 2016-5-14
 */
public class Constants {
	
	/** web项目根目录 */
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator  + "webapps";
  
	/** 资源目录 */
	public static final String WEB_RESOURCES = System.getProperty("user.dir") + File.separator  + "resources";
  
	/** 配置文件目录 */
	public static final String WEB_CONF = System.getProperty("user.dir") + File.separator  + "conf";
	
	/** 项目部署文件目录 */
	public static final String WEB_APPS = System.getProperty("user.dir") + File.separator  + "webapps";
  
	/** classes文件目录 */
	//for test   
	public static final String WEB_CLASSES_ROOT = System.getProperty("user.dir") + File.separator  + "WEB-INF"
		  + File.separator  + "classes";
  
}