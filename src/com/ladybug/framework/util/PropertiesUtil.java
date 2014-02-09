package com.ladybug.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * 
 * @author james.li
 *
 */
public class PropertiesUtil {

	/**
	 * 
	 * @param fileName properties
	 * @return
	 */
	public static Properties getProperties(String fileName){
		Properties prop = new Properties();
		
		ClassPathResource cp = new ClassPathResource(fileName);
		try {
			InputStream is = cp.getInputStream();
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return prop;
	}
	
	/**
	 *
	 * @param keyName 
	 * @param fileName properties
	 * @return
	 */
	public String getPropertyValue(String keyName,String fileName){
		Properties prop = getProperties(fileName);
		return prop.getProperty(keyName);
	}
	
	/**
	 * 
	 * @param fileName  properties
	 */
	public static Set<String> getDBNameSet(String fileName){
		Set<String> nameSet = new LinkedHashSet<String>();
		Properties prop = getProperties(fileName);
		String beforeKey = "";
		for(Object o : prop.keySet()){
			String key = (String)o;
			if(key.contains("jdbc") && key.split("\\.").length>=3){
				if(!key.equals(beforeKey)){
					
					nameSet.add(key.split("\\.")[0]);
				}
				beforeKey = key;
			}
		}
		return nameSet;
	}
	
	public static Set<String> getDBNameSet(Properties prop){
		Set<String> nameSet = new LinkedHashSet<String>();
	
		String beforeKey = "";
		for(Object o : prop.keySet()){
			String key = (String)o;
			if(key.contains("jdbc") && key.split("\\.").length>=3){
				if(!key.equals(beforeKey)){
					
					nameSet.add(key.split("\\.")[0]);
				}
				beforeKey = key;
			}
		}
		return nameSet;
	}
	
	/**
	 * 
	 * @param fileName  properties
	 * @return
	 */
	public static List<String> getDBNameList(String fileName){
		List<String> nameList = new ArrayList<String>();
		Set<String> set = getDBNameSet(fileName);
		Iterator<String> it = set.iterator();
		while (it.hasNext()){
			nameList.add(it.next());
		}
		return nameList;
	}
	
	public static List<String> getDBNameList(Properties prop){
		List<String> nameList = new ArrayList<String>();
		Set<String> set = getDBNameSet(prop);
		Iterator<String> it = set.iterator();
		while (it.hasNext()){
			nameList.add(it.next());
		}
		return nameList;
	}
	
	public static void main(String[] args){
		String fileName = "test.properties";
	
		List<String> nameList = getDBNameList(fileName);
		for(String name : nameList){
			System.out.println(name);
		}
		
	}
	
}
