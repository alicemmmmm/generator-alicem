package com.combest.generator;

import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import com.combest.generator.utils.StringUtils;

/**
* @author lhb
* @date 创建时间：2020年11月2日 上午11:41:54
* @Description 
*/
public class test {
	
	private static String FILENAME = "generatorConfig.yml";
	
	
	/**
	 * 获取yml文件中的值
	 * 
	 * @param fileName yml文件名，需要在claspath目录下,classpath下直接传文件名，
	 *        否则需要传递classpath下的相对路径，默认application.yml
	 * @param typeName yml文件中的key值
	 * @return
	 */
	public static String getTypePropertie(String typeName) {
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		yaml.setResources(new ClassPathResource(FILENAME,test.class.getClassLoader()));
		Properties properties = yaml.getObject();
		return properties.getProperty(typeName);
	}
	
	public static void main(String[] args) {
		String propertie = getTypePropertie("data.testString");
		System.out.println(propertie);
	}
}
