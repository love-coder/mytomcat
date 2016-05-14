package org.hanchun.utils;

import java.io.File;  
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;  
import org.dom4j.io.SAXReader;  

/**
 * 解析web.xml
 * @author HanChun
 * @version 1.0 2016-5-14
 */
public class DOMParser { 
  
	//web.xml映射规则
	private static Map<String,String> servletMapping;
	
	private static void webParser() throws DocumentException{  
		servletMapping = new HashMap<String,String>();
        SAXReader reader = new SAXReader();  
        String webURL = System.getProperty("user.dir")+File.separator+"src"+
    	        File.separator+"test"+File.separator+"resources"+File.separator+"web.xml";
        Document document = reader.read(new File(webURL));  
        Element node = document.getRootElement();  
        //servlet-mapping
        Map<String, String> temServetMapping = new HashMap<String, String>();
        List<Element> servletMappingElements = node.elements("servlet-mapping");
        for(Element e : servletMappingElements) {
        	Element servletName = e.element("servlet-name");
        	Element servletClass = e.element("url-pattern");
        	temServetMapping.put(servletName.getTextTrim(), servletClass.getTextTrim());
        }
        //servlet
        Map<String, String> temServetClass = new HashMap<String, String>();
        List<Element> servletClassElements = node.elements("servlet");
        for(Element e : servletClassElements) {
        	Element servletName = e.element("servlet-name");
        	Element servletClass = e.element("servlet-class");
        	temServetClass.put(servletName.getTextTrim(), servletClass.getTextTrim());
        }
        //组装配置规则
        for(Map.Entry<String, String> entry : temServetMapping.entrySet()){  
        	servletMapping.put(entry.getValue(), temServetClass.get(entry.getKey()));
       }
    }

	public static Map<String, String> getServletMapping() {
		if(servletMapping == null) {
			 try {
				webParser();
			} catch (DocumentException e) {
				System.out.println("web.xml解析失败");
				e.printStackTrace();
			}
		}
		return servletMapping;
	}  
       
}