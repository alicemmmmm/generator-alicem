package com.combest.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;

/**
 * <p>
 * MyBatis Plus Generator 配置执行类示例
 * </p>
 *
 * @author
 * @since
 */
public class MyBatisPlusGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //当前项目名
        String projectName = "/generator";

        globalConfig.setOutputDir(projectPath + projectName+"/src/main/java");
        globalConfig.setAuthor("lhb"); //开发人员
        globalConfig.setOpen(false);
        globalConfig.setFileOverride(true); //是否覆盖已有文件
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setDateType(DateType.ONLY_DATE);
        globalConfig.setServiceName("%sService"); //UserService
        autoGenerator.setGlobalConfig(globalConfig);


        // 数据源配置 需配置
		DataSourceConfig dataSourceConfig = new DataSourceConfig();


        dataSourceConfig.setDbType(DbType.SQL_SERVER);
		dataSourceConfig
				.setUrl("jdbc:sqlserver://192.168.10.201:1433;DatabaseName=device");
		// dataSourceConfig.setSchemaName("public");
		dataSourceConfig.setDriverName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		dataSourceConfig.setUsername("report");
		dataSourceConfig.setPassword("1q2w3e4r#");
		autoGenerator.setDataSource(dataSourceConfig);

        // 生成包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.combest");
        packageConfig.setEntity("bean"); //实体类包名
        //如果需要手动输入模块名
        packageConfig.setModuleName(scanner("模块名"));
        autoGenerator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
                Map<String,Object> map = new HashMap<>();
                map.put("e",5);
                super.setMap(map);
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {

                // 自定义输出文件名
                return projectPath + projectName+"/src/main/resources/mapper/" + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        injectionConfig.setFileOutConfigList(focList);
        autoGenerator.setCfg(injectionConfig);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity-test.java");
        // templateConfig.setService("templates/service.java");
        // templateConfig.setController("templates/controller.java");
        // templateConfig.setController("templates/controller.java");

        templateConfig.setXml(null);
        autoGenerator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);//表名映射到实体策略，带下划线的转成驼峰
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);//列名映射到类型属性策略，带下划线的转成驼峰
        // strategyConfig.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategyConfig.setEntityLombokModel(true);//实体类使用lombok
//        strategyConfig.setRestControllerStyle(true);
        // strategyConfig.setSuperControllerClass("com.baomidou.ant.common.BaseController");

        // 如果 setInclude() //设置表名不加参数, 会自动查找所有表
        // 如需要制定单个表, 需填写参数如: strategyConfig.setInclude("user_info);
        
//        strategyConfig.setInclude(); //为空生成所有
        strategyConfig.setInclude(scanner("表名，多个英文逗号分割").split(",")); //可单独设置表名
        
        // strategyConfig.setSuperEntityColumns("id");
//        strategyConfig.setControllerMappingHyphenStyle(true);

        //自动将数据库中表名为 user_info 格式的转为 UserInfo 命名
        strategyConfig.setTablePrefix(packageConfig.getModuleName() + "_");//表名映射到实体名称去掉前缀
        strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);// Boolean类型字段是否移除is前缀处理
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        System.out.println("===================== MyBatis Plus Generator ==================");

        autoGenerator.execute();

        System.out.println("================= MyBatis Plus Generator Execute Complete ==================");
    }

}