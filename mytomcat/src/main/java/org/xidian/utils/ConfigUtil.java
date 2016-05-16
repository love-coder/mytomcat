package org.xidian.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * 配置文件相关类
 * @author HanChun
 * @version 1.0 2016-5-16
 */
public class ConfigUtil {

	private static long lastModified = 0L;
	private static File configFile = null;
	private static Properties props = new Properties();

	//staic 
	static {
		loadProperty();
	}

	/**
	 * some problems for different OS ? hanchun2016-5-16
	 */
	private static void loadProperty() {
		try {
			//System.out.print(ConfigUtil.class.getResource(CONFIG_FILE).getPath());
			String path = Constants.WEB_CONF + File.separator+"config.properties";
//			if (System.getProperty("os.name").startsWith("Windows")) {
//				path = path.substring(1);
//				path = path.replaceAll("%20", " ");
//			}
			File f = new File(path);
			lastModified = f.lastModified();
			configFile = f;
			props.load(new FileInputStream(f));
		   (new ReloadThread()).start();
		} catch (Exception e) {
			System.out.println("配置文件加载失败");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * 检查是否配置文件被更新
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
					System.out.println("reload config file:" + configFile.getAbsolutePath());
				} catch (Exception e) {
					System.out.println("failed to reload config file: " + configFile.getAbsolutePath());
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param name 属性名
	 * @param defaultValue
	 * @return defaultValue null
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

	/**
	 * @param name 属性名
	 * @return String value
	 */
	public static String getConfig(String name) {
		return getConfig(name, null);
	}

	/**
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
							System.out.println("config file changed, reload: " + configFile.getAbsolutePath());
						} catch (Exception e) {
							System.out.println("failed to reload config file: " + configFile.getAbsolutePath());
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(5000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else
					break;
			}
		}
	}

}
