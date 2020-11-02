package com.combest.generator.utils;

import org.yaml.snakeyaml.Yaml;


/**
* @author lhb
* @date 创建时间：2020年11月2日 下午1:37:19
* @Description 
*/
public class YamlUtils {
	
	private static final Yaml INSTANCE = new Yaml();
	
	private static final String FILE_NAME = "generatorConfig.yml";
	
	static {
		INSTANCE.load(YamlUtils.class.getClassLoader()
	            .getResourceAsStream(FILE_NAME));
	}
	
	
	

}
