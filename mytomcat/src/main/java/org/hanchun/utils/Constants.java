package org.hanchun.utils;

import java.io.File;

/**
 * 一些常量
 * @author HanChun
 * @version 1.0 2016-5-14
 */
public class Constants {
	
  public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator  + "webapps";
  
  public static final String WEB_RESOURCES = System.getProperty("user.dir") + File.separator  + "resources";
  
  //for test
  public static final String WEB_CLASSES_ROOT = System.getProperty("user.dir") + File.separator  + "target"
		  + File.separator  + "classes";
  //for true
  public static final String WEB_CLASSES_ROOT2 = System.getProperty("user.dir") + File.separator  + "target"
		  + File.separator  + "classes";
  
}