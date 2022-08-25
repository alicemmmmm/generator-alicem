package cn.alicem.generator.configBean;

import lombok.Data;

/**
* @author lhb
* @date 创建时间：2020年11月7日 下午6:25:20
* @Description 全局通用配置
*/
@Data
public class CommonGlobalConfig {
	
	private String author;//开发人员称呼,用于类上的注释
	
	private String projectName;//生成的项目名
	
	private String parent;//父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
	
	private String moduleName;//子包名,模块名 
	
	private Boolean open;//是否生成后自动打开文件所在文件夹
	
	private String templatePath;//模板所在路径名(用于定制多种策略)
	
	/**
	 * <p>表名映射到实体名称去掉前缀
	 * <p>例如:如果包名为cn.alicem.moon(子模块名)
	 * <p>要生成的表名为moon_sea
	 * <p>如果去除前缀,则生成的实体名为Sea,对应的controller为SeaController,service,mapper也是如此
	 */
	private Boolean isRemoveTablePrefix;
	/**
	 * 默认配置属性
	 */
	public CommonGlobalConfig(){
		this.author = "author";
		this.projectName = "/generator";
		this.parent = "cn.alicem";
		this.moduleName = "moon_sea";
		this.open = false;
		this.templatePath = "/templates";
		this.isRemoveTablePrefix = false;
	}
}
