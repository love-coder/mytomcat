package org.hanchun.main;

import org.xidian.utils.ConfigUtil;

public class TestConfig {
	
	public static void main(String[] args) {
		System.out.println(ConfigUtil.getConfig("port"));;
//		System.out.println();
		//System.out.println(TestClass.class.getResource("TestConfig").getPath());;
	}
	
	

}
