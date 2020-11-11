package com.combest.generator.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.combest.generator.configBean.CommonGlobalConfig;
import com.combest.generator.configBean.CustomConfig;
import com.combest.generator.configBean.MDataSourceConfig;


/**
* @author lhb
* @date 创建时间：2020年11月7日 下午4:32:20
* @Description 
*/
@Mapper
public interface GeneratorConvert {
	GeneratorConvert INSTANCE = Mappers.getMapper(GeneratorConvert.class);
	
	DataSourceConfig MdataSource2(MDataSourceConfig md);
	
	CustomConfig customConfig2CustomConfig(CustomConfig customConfig);
	
	CommonGlobalConfig commonGlobalConfig2CommonGlobalConfig(CommonGlobalConfig commonGlobalConfig);
}
