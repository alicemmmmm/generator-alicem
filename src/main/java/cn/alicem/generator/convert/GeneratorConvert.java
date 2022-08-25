package cn.alicem.generator.convert;

import cn.alicem.generator.configBean.CommonGlobalConfig;
import cn.alicem.generator.configBean.CustomConfig;
import cn.alicem.generator.configBean.MDataSourceConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;


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
