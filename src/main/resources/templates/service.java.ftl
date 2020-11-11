package ${package.Service};

import java.util.List;
import java.util.Map;
import ${package.Entity}.${entity};

/**
 * <p>
 * <#if table.comment??>${table.comment}<#else>${entity}</#if> 服务类
 * </p>
 * @author ${author}
 * @date ${date}
 * @Description
 */
public interface ${table.serviceName}{
<#list table.fields as field>
 <#if field.keyFlag>
  <#assign primaryKey=field/>
 </#if>
</#list>

<#if primaryKey??>
    ${entity} getBy${primaryKey.propertyName?cap_first}(Integer ${primaryKey.propertyName});
</#if>

<#if primaryKey??>
    /**
    * 插入或更新 带有主键id即更新  否则即插入
    * @param ${entity?uncap_first}
    * @return 插入成功返回插入后的主键id,更新成功返回更新记录的条数
    */
    Integer save(${entity} ${entity?uncap_first});
</#if>

<#if cfg.subTableFlag><#-- 是否为子表 -->
    /**
    * 根据条件查询总记录数
    * @param params 搜索参数
    * @return
    */
    Long countByQuerys(Map<String, Object> params);

    /**
    * 根据搜索条件列表查询
    * @param params 搜索参数
    * @return
    */
    List<${entity}> listByQuerys(Map<String, Object> params);
</#if>

<#if primaryKey??>
<#if cfg.fdFlag>
    /**
    * 根据主键数组移除多条记录  修改删除状态,数据实际存在
    * @param ${primaryKey.propertyName}s 主键数组
    * @return
    */
    Integer removeBy${primaryKey.propertyName?cap_first}s(int[] ${primaryKey.propertyName}s);
</#if>

<#if cfg.deleteMethodFlag>
    /**
    * 根据主键数组删除多条记录
    * @param ${primaryKey.propertyName}s 主键数组
    * @return
    */
    Integer deleteBy${primaryKey.propertyName?cap_first}s(int[] ${primaryKey.propertyName}s);
</#if>
</#if>


}
