package org.xidian.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * 配置文件工具类
 */
public class ConfigUtil {

	public final static String CONFIG_FILE = "/resources/config.properties";
	private static long lastModified = 0L;
	private static File configFile = null;

	private static Properties props = new Properties();

	static {
		loadProperty();
	}

	private static void loadProperty() {
		try {
			System.out.print(ConfigUtil.class.getResource(CONFIG_FILE).getPath());
			String path = ConfigUtil.class.getResource(CONFIG_FILE).getFile();
			if (System.getProperty("os.name").startsWith("Windows")) {
				path = path.substring(1);
				path = path.replaceAll("%20", " ");
			}
			File f = new File(path);
			lastModified = f.lastModified();
			configFile = f;
			props.load(new FileInputStream(f));
			(new ReloadThread()).start();
		} catch (Exception e) {
			System.exit(-1);
		}
	}

	/**
	 * 检查是否更新过
	 */
	private static void checkUpdate() {
		if (configFile != null) {
			long m = configFile.lastModified();
			if (m != lastModified) {
				lastModified = m;
				try {
					Properties prop = new Properties();
					prop.load(new FileInputStream(configFile));
					props = prop;
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 得到具体属性
	 * @param name 属性名称
	 * @param defaultValue
	 * @return
	 */
	public static String getConfig(String name, String defaultValue) {
		checkUpdate();
		String ret = props.getProperty(name, defaultValue);
		if (ret == null) {
			return defaultValue;
		} else {
			return ret.trim();
		}
	}

	public static String getConfig(String name) {
		System.out.println("dd");
		return getConfig(name, null);
	}

	/**
	 * 如果配置文件做了修改，重新加载到内存中
	 * @author HanChun
	 */
	static class ReloadThread extends Thread {
		public void run() {
			while (true) {
				if (configFile != null) {
					long m = configFile.lastModified();
					if (m != lastModified) {
						lastModified = m;
						try {
							Properties prop = new Properties();
							prop.load(new FileInputStream(configFile));
							props = prop;
						} catch (Exception e) {
						}
					}
					try {
						Thread.sleep(5000);
					} catch (Exception e) {
					}
				} else
					break;
			}
		}
	}

}
