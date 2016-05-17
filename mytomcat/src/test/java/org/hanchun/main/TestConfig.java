package org.hanchun.main;

import org.junit.Test;
import org.xidian.utils.ConfigUtil;
import org.xidian.utils.Constants;
import org.xidian.utils.WarUtil;

public class TestConfig {
	
//	@Test
//	public void testConf(){
//		System.out.println(ConfigUtil.getConfig("port"));
//	}
	
	@Test
	public void testWar(){
		
		WarUtil.unzip("c:/war/mytomcat.war","c:/war");
		
	}
	
	

}
