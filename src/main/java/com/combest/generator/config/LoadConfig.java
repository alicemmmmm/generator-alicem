package com.combest.generator.config;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.combest.generator.configBean.CommonGlobalConfig;
import com.combest.generator.configBean.CustomConfig;
import com.combest.generator.configBean.MDataSourceConfig;
import com.combest.generator.convert.GeneratorConvert;
import com.combest.generator.utils.StringUtils;

/**
* @author lhb
* @date 创建时间：2020年11月7日 下午4:14:49
* @Description 
*/
public class LoadConfig {

	private static final Logger logger = LoggerFactory.getLogger(LoadConfig.class);

	public static CommonGlobalConfig COMMON_GLOBAL_CONFIG;
	public static CustomConfig DEFAULT_CUSTOM_CONFIG;
	
	private Yaml yaml;	
	private static ClassLoader THIS_CLASS_LOADER;
	private static GeneratorConvert CONVERT	= GeneratorConvert.INSTANCE;	
	private static String PROJECT_PATH;		
	private static final String DATA_SOURCE_CONFIG_FILE_NAME = "dataSourceConfig.yml";	
	private static final String GENERATOR_CONFIG_FILE_NAME = "generatorConfig.yml";
	
	private static Map<String, Object> CONFIG_MAP;
	
	public LoadConfig() {
		
		THIS_CLASS_LOADER = this.getClass().getClassLoader();
		PROJECT_PATH = System.getProperty("user.dir");
		
		//加载通用全局配置
		InputStream inputStream = THIS_CLASS_LOADER.getResourceAsStream(GENERATOR_CONFIG_FILE_NAME);		
		yaml = new Yaml();
		@SuppressWarnings("unchecked")
		Map<String, Object> configMap = (Map<String, Object>) yaml.load(inputStream);
		CONFIG_MAP = configMap;
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) configMap.get("commonGlobalConfig");
		CommonGlobalConfig commonGlobalConfig = JSONObject.parseObject(JSONObject.toJSONString(map), CommonGlobalConfig.class);
		if(commonGlobalConfig == null) {
			commonGlobalConfig = new CommonGlobalConfig();
		}
		COMMON_GLOBAL_CONFIG = CONVERT.commonGlobalConfig2CommonGlobalConfig(commonGlobalConfig);
		logger.info("加载通用全局配置:{}",COMMON_GLOBAL_CONFIG);

		//自定义配置类 默认类值
		DEFAULT_CUSTOM_CONFIG = new CustomConfig();
		DEFAULT_CUSTOM_CONFIG.setModelName(COMMON_GLOBAL_CONFIG.getModuleName());
		logger.info("获取默认自定义配置类:{}",DEFAULT_CUSTOM_CONFIG);
	}
	
	
	/**
	 * <p>数据源配置<p>
	 * @return
	 */
	public DataSourceConfig loadDataSourceConfig() {
		logger.info("开始加载数据源配置");
		InputStream inputStream = THIS_CLASS_LOADER.getResourceAsStream(DATA_SOURCE_CONFIG_FILE_NAME);		
		yaml = new Yaml(new Constructor(MDataSourceConfig.class));		
		MDataSourceConfig mDataSourceConfig = (MDataSourceConfig) yaml.load(inputStream);		
		DataSourceConfig dataSourceConfig = CONVERT.MdataSource2(mDataSourceConfig);
		dataSourceConfig.setDbType(DbType.SQL_SERVER);//数据库类型  
		logger.info("数据源配置加载完成");
		return dataSourceConfig;
	}
	
	/**
	 * <p>自定义生成表配置<p>
	 * @return
	 */
	public List<CustomConfig> loadCustomConfigList(){
		logger.info("开始加载自定义生成表配置");
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> listMap = (List<Map<String, Object>>) CONFIG_MAP.get("CustomConfig");	
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(listMap);
		List<CustomConfig> customConfigList = jsonArray.toJavaList(CustomConfig.class);	
		List<CustomConfig> customConfigListAfterTreatment = new ArrayList<>();
		customConfigList.forEach(customConfig -> {
			if(StringUtils.isEmpty(customConfig.getTableName())) {
				logger.error("生成失败,读取表名为空!!!");
				throw new RuntimeException("生成失败,读取表名为空!!!");
			}
			customConfig = CONVERT.customConfig2CustomConfig(customConfig);
			customConfig.setModelName(COMMON_GLOBAL_CONFIG.getModuleName());
			if(customConfig.getJoinFlag()) {
				customConfig.setJoinName(StringUtils.takeTableAlias(customConfig.getTableName()));
			}
			customConfigListAfterTreatment.add(customConfig);
		});
		logger.info("加载自定义生成表配置完成");
		return customConfigListAfterTreatment;
	}

	
	/**
	 * <p>生成包配置<p>
	 * @return
	 */
	public PackageConfig loadPackageConfig() {
		logger.info("开始加载生成包配置");
		//生成包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(COMMON_GLOBAL_CONFIG.getParent());
        packageConfig.setEntity("bean"); //实体类包名
        packageConfig.setModuleName(COMMON_GLOBAL_CONFIG.getModuleName()); //模块名
        logger.info("加载生成包配置完成");
		return packageConfig;
	}
	
	
	/**
	 * <p>全局配置<p>
	 * @return
	 */
	public GlobalConfig loadGlobalConfig() {
		logger.info("开始加载全局配置");
		//全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(PROJECT_PATH + COMMON_GLOBAL_CONFIG.getProjectName() + "/src/main/java");
        globalConfig.setAuthor(COMMON_GLOBAL_CONFIG.getAuthor()); //开发人员
        globalConfig.setOpen(COMMON_GLOBAL_CONFIG.getOpen());//是否生成后直接打开
        globalConfig.setFileOverride(true); //是否覆盖已有文件
        globalConfig.setBaseResultMap(true); //是否生成mapping.xml 中返回map
        globalConfig.setBaseColumnList(true); //是否生成mapping.xml 中基础属性列
        globalConfig.setDateType(DateType.ONLY_DATE);
        globalConfig.setServiceName("%sService"); //UserService
        logger.info("加载全局配置完成");
		return globalConfig;
	}
	
	/**
	 * <p>模板路径配置项<p>
	 * @return
	 */
	public TemplateConfig loadTemplateConfig() {
		logger.info("开始加载模板路径配置项");
		TemplateConfig templateConfig = new TemplateConfig();
        //配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity(COMMON_GLOBAL_CONFIG.getTemplatePath() + "/entity.java");
        templateConfig.setMapper(COMMON_GLOBAL_CONFIG.getTemplatePath() + "/mapper.java");
        templateConfig.setService(COMMON_GLOBAL_CONFIG.getTemplatePath() + "/service.java");
        templateConfig.setServiceImpl(COMMON_GLOBAL_CONFIG.getTemplatePath() + "/serviceImpl.java");
        templateConfig.setController(COMMON_GLOBAL_CONFIG.getTemplatePath() + "/controller.java");
        templateConfig.setXml(null);
        logger.info("加载模板路径配置项完成");
		return templateConfig;
	}
	
	
	/**
	 * <p>策略配置项<p>
	 * @return
	 */
	public StrategyConfig loadStrategyConfig() {	
		logger.info("开始加载策略配置项");
		//策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);//表名映射到实体策略，带下划线的转成驼峰
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);//列名映射到类型属性策略，带下划线的转成驼峰
        //strategyConfig.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategyConfig.setEntityLombokModel(true);//实体类使用lombok
        //strategyConfig.setRestControllerStyle(true);
        //strategyConfig.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        //如果 setInclude() //设置表名不加参数, 会自动查找所有表
        //如需要制定单个表, 需填写参数如: strategyConfig.setInclude("user_info);
        //final String tableName;
        //strategyConfig.setInclude(tableName = "maintenance_level"); //为空生成所有
		//strategyConfig.setInclude(scanner("表名，多个英文逗号分割").split(",")); //可单独设置表名
        //strategyConfig.setSuperEntityColumns("id");
        //strategyConfig.setControllerMappingHyphenStyle(true);
        //自动将数据库中表名为 user_info 格式的转为 UserInfo 命名
        
        if(COMMON_GLOBAL_CONFIG.getIsRemoveTablePrefix()) {
        	strategyConfig.setTablePrefix(COMMON_GLOBAL_CONFIG.getModuleName() + "_");//表名映射到实体名称去掉前缀
        }
        strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);// Boolean类型字段是否移除is前缀处理
        logger.info("加载策略配置项完成");
		return strategyConfig;
	}
	
	
	/**
	 * <p>自定义配置项<p>
	 * @return
	 */
	public InjectionConfig loadInjectionConfig(Map<String, Object> map) {
		logger.info("开始注入自定义配置");
		// 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                super.setMap(map);
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = COMMON_GLOBAL_CONFIG.getTemplatePath() + "/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return PROJECT_PATH + COMMON_GLOBAL_CONFIG.getProjectName() + "/src/main/resources/mapper/"  + COMMON_GLOBAL_CONFIG.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        injectionConfig.setFileOutConfigList(focList);
        logger.info("注入自定义配置完成");
		return injectionConfig;
	}
	
	
	
}
