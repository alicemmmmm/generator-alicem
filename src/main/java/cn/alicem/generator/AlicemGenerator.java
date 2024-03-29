package cn.alicem.generator;

import cn.alicem.generator.config.GeneratorConfig;

/**
 * @author lhb
 * @date 创建时间：2020年11月10日 下午3:41:58
 * @Description MybatisPlusGenerator 代码自动生成器 2.0
 * 1. 先配置数据库连接属性 resources/dataSourceConfig.yml
 * 2. 打开 resources/generatorConfig.yml 配置全局属性 与生成表配置
 * 3. 运行 AlicemGenerator类
 * 修改模板注入参数所在类:com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine#batchOutput
*/
public class AlicemGenerator {
	public static void main(String[] args) {
		GeneratorConfig.execute();
	}
}
