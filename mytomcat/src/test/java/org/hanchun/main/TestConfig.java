package org.hanchun.main;

import org.junit.Test;
import org.xidian.utils.ConfigUtil;

public class TestConfig {
	
	public static void main(String[] args) {
		System.out.println(ConfigUtil.getConfig("port"));;
	}

}
