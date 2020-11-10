package com.combest.generator.configBean;

import lombok.Data;

/**
* @author lhb
* @date 创建时间：2020年11月7日 下午3:45:10
* @Description 数据库配置
*/
@Data
public class MDataSourceConfig {
	
    private String url;

    private String driverName;

    private String username;

    private String password;
}
