package ${package.ServiceImpl};

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.util.StringUtils;

/**
 * <p><#if table.comment??>${table.comment}<#else>${entity}</#if> 服务实现类
 * @author ${author}
 * @date ${date}
 * @Description
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class ${table.serviceImplName} implements ${table.serviceName} {
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign primaryKey=field/>
    </#if>
</#list>

    @Autowired
    private ${entity}Mapper ${entity?uncap_first}Mapper;

<#if primaryKey??>
    @Override
    public ${entity} getBy${primaryKey.propertyName?cap_first}(Integer ${primaryKey.propertyName}) {
        return ${entity?uncap_first}Mapper.selectByPrimaryKey(${primaryKey.propertyName});
    }
</#if>

<#if primaryKey??>
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT, readOnly = false)
    public Integer save(${entity} ${entity?uncap_first}){
        Integer result = null;
        //判断是插入还是更新
        if (StringUtils.isEmpty(${entity?uncap_first}.get${primaryKey.propertyName?cap_first}())) {
	        //插入
	        ${entity?uncap_first}Mapper.insertSelective(${entity?uncap_first});
	        //已设置插入成功主键回显
	        result = ${entity?uncap_first}.get${primaryKey.propertyName?cap_first}();
        }else {
	        //更新
	        result = ${entity?uncap_first}Mapper.updateByPrimaryKeySelective(${entity?uncap_first});
        }
        return result;
    }
</#if>

<#if !cfg.subTableFlag><#-- 不为子表 -->
    @Override
    public Long countByQuerys(Map<String, Object> params) {
        return ${entity?uncap_first}Mapper.selectCountByQuerys(params);
    }

    @Override
    public List<${entity}> listByQuerys(Map<String, Object> params) {
        return ${entity?uncap_first}Mapper.selectListByQuerys(params);
    }
</#if>

<#if primaryKey??>
<#if cfg.fdFlag>
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT, readOnly = false)   
    public Integer removeBy${primaryKey.propertyName?cap_first}s(int[] ${primaryKey.propertyName}s){
    	return ${entity?uncap_first}Mapper.updateIsDeleteByPrimaryKeys(${primaryKey.propertyName}s);
    }
</#if>

<#if cfg.deleteMethodFlag>
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT, readOnly = false)
    public Integer deleteBy${primaryKey.propertyName?cap_first}s(int[] ${primaryKey.propertyName}s) {
        return ${entity?uncap_first}Mapper.deleteByPrimaryKeys(${primaryKey.propertyName}s);
    }
</#if>
</#if>
}
