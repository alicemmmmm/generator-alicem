package ${package.Mapper};

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import ${package.Entity}.${entity};
<#if cfg.subTableFlag><#-- 为子表 -->
import org.apache.ibatis.annotations.Select;
</#if> 
<#--import org.springframework.stereotype.Repository;-->

/**
 * <p><#if table.comment??>${table.comment}<#else>${entity}</#if> Mapper 接口
 * @author ${author}
 * @date ${date}
 * @Description
 */
public interface ${table.mapperName} {
<#list table.fields as field>
 <#if field.keyFlag>
 <#assign primaryKey=field/>
 </#if>
</#list>

<#if primaryKey??>
    /**
     * <p>根据主键删除单条数据
     * @param ${primaryKey.propertyName} 主键
     * @return
     */
    Integer deleteByPrimaryKey(Integer ${primaryKey.propertyName});
</#if>

    /**
     * <p>插入单条数据
     * @param ${entity?uncap_first}
     * @return
     */
    Integer insert(${entity} ${entity?uncap_first});

    /**
     * <p>插入单条数据,忽略空值
     * @param ${entity?uncap_first}
     * @return
     */
    Integer insertSelective(${entity} ${entity?uncap_first});

<#if primaryKey??>
    /**
     * <p>根据主键查询单条数据
     * @param ${primaryKey.propertyName} 主键
     * @return
     */
    ${entity} selectByPrimaryKey(Integer ${primaryKey.propertyName});
</#if>

<#if primaryKey??>
    /**
     * <p>根据主键更新单条数据,忽略空值
     * @param ${entity?uncap_first}
     * @return
     */
    Integer updateByPrimaryKeySelective(${entity} ${entity?uncap_first});
</#if>

<#if primaryKey??>
    /**
     * <p>根据主键更新单条数据
     * @param ${entity?uncap_first}
     * @return
     */
    Integer updateByPrimaryKey(${entity} ${entity?uncap_first});
</#if>

<#if !cfg.subTableFlag><#-- 不为子表 -->
    /**
     * <p>根据搜索条件列表查询
     * @param params 搜索参数
     * @return
     */
    List<${entity}> selectListByQuerys(Map<String, Object> params);

    /**
     * <p>根据条件查询总记录数
     * @param params 搜索参数
     * @return
     */
    Long selectCountByQuerys(Map<String, Object> params);
</#if>

<#if cfg.fdFlag>
    /**
     * <p>根据主键数组移除多条记录  修改删除状态,数据实际存在
     * @param primaryKeys 主键数组
     * @return
     */
    Integer updateIsDeleteByPrimaryKeys(@Param("primaryKeys") int[] primaryKeys);
</#if>

<#if cfg.deleteMethodFlag>
    /**
     * <p>根据主键数组删除多条记录
     * @param primaryKeys 主键数组
     * @return
     */
    Integer deleteByPrimaryKeys(@Param("primaryKeys") int[] primaryKeys);
</#if>  

<#if cfg.subTableFlag><#-- 为子表 -->
    /**
     * <p>根据主表id删除
     * @param mainIds
     * @return
     */
    Integer deleteByMainIds(@Param("mainIds") int[] mainIds);
    
	/**
	 * <p>根据主表id查找
	 * @param mainId
	 * @return
	 */
    @Select("select * from ${table.name} where ${cfg.mainTableIdName} = ${r'#{mainId}'}")
    List<${entity}> selectByMainId(Integer mainId);  
</#if> 
}
