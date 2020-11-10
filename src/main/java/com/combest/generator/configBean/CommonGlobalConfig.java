package com.combest.generator.configBean;

import lombok.Data;

/**
* @author lhb
* @date 创建时间：2020年11月7日 下午6:25:20
* @Description 
*/
@Data
public class CommonGlobalConfig {
	
	private String author;//开发人员称呼,用于类上的注释
	
	private String projectName;//生成的项目名
	
	private String parent;//父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
	
	private String moduleName;//子包名,模块名 
	
	private Boolean open;//是否生成后自动打开文件所在文件夹
}
