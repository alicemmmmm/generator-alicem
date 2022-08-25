package cn.alicem.generator.convert;

import cn.alicem.generator.configBean.CommonGlobalConfig;
import cn.alicem.generator.configBean.CustomConfig;
import cn.alicem.generator.configBean.MDataSourceConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-11-11T16:39:41+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_161 (Oracle Corporation)"
)
public class GeneratorConvertA{
	
	
	private static volatile GeneratorConvertA INSTANCE = null;
	 
	static {
		INSTANCE = new GeneratorConvertA();
	}
	
    private GeneratorConvertA() {
    }

   
    public DataSourceConfig MdataSource2(MDataSourceConfig md) {
        if ( md == null ) {
            return null;
        }

        DataSourceConfig dataSourceConfig = new DataSourceConfig();

        dataSourceConfig.setUrl( md.getUrl() );
        dataSourceConfig.setDriverName( md.getDriverName() );
        dataSourceConfig.setUsername( md.getUsername() );
        dataSourceConfig.setPassword( md.getPassword() );

        return dataSourceConfig;
    }

   
    public CustomConfig customConfig2CustomConfig(CustomConfig customConfig) {
        if ( customConfig == null ) {
            return null;
        }

        CustomConfig customConfig1 = new CustomConfig();

        customConfig1.setTableName( customConfig.getTableName() );
        customConfig1.setModelName( customConfig.getModelName() );
        customConfig1.setFdFlag( customConfig.getFdFlag() );
        customConfig1.setFdFieldName( customConfig.getFdFieldName() );
        customConfig1.setFdFieldType( customConfig.getFdFieldType() );
        customConfig1.setFdValue( customConfig.getFdValue() );
        customConfig1.setDeleteMethodFlag( customConfig.getDeleteMethodFlag() );
        customConfig1.setJoinFlag( customConfig.getJoinFlag() );
        customConfig1.setSwaggerFlag( customConfig.getSwaggerFlag() );
        customConfig1.setSubTableFlag( customConfig.getSubTableFlag() );

        return customConfig1;
    }

    
    public CommonGlobalConfig commonGlobalConfig2CommonGlobalConfig(CommonGlobalConfig commonGlobalConfig) {
        if ( commonGlobalConfig == null ) {
            return null;
        }

        CommonGlobalConfig commonGlobalConfig1 = new CommonGlobalConfig();

        commonGlobalConfig1.setAuthor( commonGlobalConfig.getAuthor() );
        commonGlobalConfig1.setProjectName( commonGlobalConfig.getProjectName() );
        commonGlobalConfig1.setParent( commonGlobalConfig.getParent() );
        commonGlobalConfig1.setModuleName( commonGlobalConfig.getModuleName() );
        commonGlobalConfig1.setOpen( commonGlobalConfig.getOpen() );
        commonGlobalConfig1.setTemplatePath( commonGlobalConfig.getTemplatePath() );

        return commonGlobalConfig1;
    }
}
