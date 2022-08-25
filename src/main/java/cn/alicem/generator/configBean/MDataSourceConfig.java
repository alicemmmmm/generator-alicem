package cn.alicem.generator.configBean;

import com.baomidou.mybatisplus.annotation.DbType;
import cn.alicem.generator.utils.StringUtils;
import lombok.Data;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    private String type;

    private DbType dbType;

    public DbType getDbType() {
        if (StringUtils.isEmpty(this.type)){
            throw new RuntimeException("dbType字段为空,请在'dataSourceConfig.yml'文件中指定该字段值!");
        }
        DbType type = DbType.getDbType(this.type);
        if (type == null){
            String allTypeStr = Arrays.stream(DbType.values()).map(d -> d.getDb()).collect(Collectors.toList()).toString();
            throw new RuntimeException("dbType字段设置错误!请指定以下值" + allTypeStr);
        }
        return type;
    }

    public static void main(String[] args) {
        DbType oracle = DbType.getDbType("oracle22");
        System.out.println(oracle.getLike());
        System.out.println();
    }
}
