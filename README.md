# generator-alicem
使用mybatis plus自动生成bean,mapper,service,serviceImpl,controller,mapper.xml

20201102 v1.2更新
1.添加连表别名列配置
1.1设置joinFlag为true则开启

2.添加假删除选择配置
2.1设置fdFlag为true开启
2.2同时还支持开启假删除的同时保留删除操作
2.3需设置fdFieldName 删除的数据库属性名 默认 is_delete
2.4需设置fdFieldType 删除的数据库属性类型 默认 int
2.5需设置fdValue 在数据库中删除状态的值 默认 1
2.6DeleteMethodFlag 是否保存删除操作,为true保留(如不开启假删除则需设置为true)
