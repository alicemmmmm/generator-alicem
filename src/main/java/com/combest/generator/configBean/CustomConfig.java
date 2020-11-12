package com.combest.generator.configBean;

import com.combest.generator.utils.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
* @author lhb
* @date 创建时间：2020年11月7日 下午5:52:25
* @Description 
*/
@Data
@AllArgsConstructor
public class CustomConfig {
	
	private String tableName;//需要生成的表名
	
	private String modelName;//模块名,用于controller层访问路径添加最外一层
	
	//假删除
	private Boolean fdFlag;//是否启用假删除模式
	
	private String fdFieldName;//如启用假删除,需指定标记删除的属性名
	
	private String fdFieldType;//假删除的属性类型  int 或 varchar char  
	
//	private String fdBeanName;//假删除的实体属性名  
	
	private String fdValue;//假删除的值
	
	private Boolean deleteMethodFlag;//是否需要删除方法,如果启用假删除,可以设为false不需要删除方法
	
	//连表
	private Boolean joinFlag;//是否启用连表列
	
//	private String joinName;//连表,表的别名(默认取每个单词的首位)
	
	//swagger模式
	private Boolean swaggerFlag;//是否启用swagger模式
	
	//主子表
	private Boolean subTableFlag;//是否为子表(子表不生成 分页查询,保存等接口)
	private String mainTableIdName;//如果为子表,可配置主表的id名称(数据库中的字段名)
	
	private Boolean mainTableFlag;//是否为主表
	
	/**
	 * 默认配置属性
	 */
	public CustomConfig(){
		this.fdFlag = false;
		this.fdFieldName = "is_delete";
		this.fdFieldType = "varchar";
		this.fdValue = "1";
		
		this.deleteMethodFlag = true;
		
		this.joinFlag = false;
		
		this.swaggerFlag = false;
		
		this.subTableFlag = false;
		
		this.mainTableIdName = "";
		
		this.mainTableFlag = false;
	}
}
