package cn.alicem.generator.config;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import cn.alicem.generator.configBean.CustomConfig;

/**
* @author lhb
* @date 创建时间：2020年11月7日 下午4:14:00
* @Description 
*/
public class GeneratorConfig {
	private static final Logger logger = LoggerFactory.getLogger(GeneratorConfig.class);
	private static LoadConfig LOAD_CONFIG;
	private static GlobalConfig GLOBAL_CONFIG;		
	private static DataSourceConfig DATASOURCE_CONFIG;
	private static PackageConfig PACKAGE_CONFIG;
	private static TemplateConfig TEMPLATE_CONFIG;
	
	static {
		LOAD_CONFIG = new LoadConfig();
		GLOBAL_CONFIG = LOAD_CONFIG.loadGlobalConfig();	
		DATASOURCE_CONFIG = LOAD_CONFIG.loadDataSourceConfig();
		PACKAGE_CONFIG = LOAD_CONFIG.loadPackageConfig();
		TEMPLATE_CONFIG = LOAD_CONFIG.loadTemplateConfig();
	}
	
//	private static GeneratorConvert CONVERT	= GeneratorConvert.INSTANCE;

	private static AutoGenerator getAutoGenerator() {
		// 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(GLOBAL_CONFIG);
        autoGenerator.setDataSource(DATASOURCE_CONFIG);
        autoGenerator.setPackageInfo(PACKAGE_CONFIG);
        autoGenerator.setTemplate(TEMPLATE_CONFIG);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        return autoGenerator;
		
	}
	
	public static void execute() {
		List<CustomConfig> customConfigList = LOAD_CONFIG.loadCustomConfigList();
		
		customConfigList.forEach(customConfig -> {	
			logger.info("开始加载:{}",customConfig);
			AutoGenerator autoGenerator = getAutoGenerator();
			
			StrategyConfig strategyConfig = LOAD_CONFIG.loadStrategyConfig();
			//单独配置每个表
			strategyConfig.setInclude(customConfig.getTableName());		
			String json = JSON.toJSONString(customConfig);
			@SuppressWarnings("unchecked")
			Map<String,Object> map = JSON.parseObject(json, Map.class);	
			
			InjectionConfig injectionConfig = LOAD_CONFIG.loadInjectionConfig(map);
			
	        autoGenerator.setCfg(injectionConfig);
	        autoGenerator.setStrategy(strategyConfig);
	        
	        //开始生成
	        autoGenerator.execute();
		});
	}
	
}
