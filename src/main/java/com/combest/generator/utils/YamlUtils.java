package com.combest.generator.utils;

import org.yaml.snakeyaml.Yaml;


/**
* @author lhb
* @date 创建时间：2020年11月2日 下午1:37:19
* @Description 
*/
public class YamlUtils {
	
	private static Yaml yaml = new Yaml();
	
	private static final String FILE_NAME = "generatorConfig.yml";
	
	private static final String DATA_SOURCE_CONFIG_FILE_NAME = "dataSourceConfig.yml";
	
	static {
		
	}
	
	public static Yaml getYaml(String fileName) {
		return (Yaml) yaml.load(YamlUtils.class.getClassLoader().getResourceAsStream(FILE_NAME));
	}
	

}
