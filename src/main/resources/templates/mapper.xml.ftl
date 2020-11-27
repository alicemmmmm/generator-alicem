<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">
<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>
    <#assign primaryKey=null/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" jdbcType="<#if field.type?upper_case == 'INT'>INTEGER<#elseif field.type?upper_case == 'DATETIME'>TIMESTAMP<#else>${field.type?upper_case}</#if>"/>
        <#assign primaryKey=field/>
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
    <result column="${field.name}" property="${field.propertyName}" jdbcType="<#if field.type?upper_case == 'INT'>INTEGER<#elseif field.type?upper_case == 'DATETIME'>TIMESTAMP<#else>${field.type?upper_case}</#if>"/>
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" jdbcType="<#if field.type?upper_case == 'INT'>INTEGER<#elseif field.type?upper_case == 'DATETIME'>TIMESTAMP<#else>${field.type?upper_case}</#if>"/>
</#if>
</#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
<#list table.fields as field>
	<#if table.fields?size != (field_index + 1)>
		${field.name},  <#-- <!-- <#if field.comment??>${field.comment}</#if> --><#-- -->
	<#else>
		${field.name}  <#-- <!-- <#if field.comment??>${field.comment}</#if> --><#-- -->
	</#if>
</#list>
       <#--${table.fieldNames} -->
    </sql>
    
<#if cfg.joinFlag>    
    <!-- 连表查询结果列 -->
    <sql id="joint_table_list">
<#list table.fields as field>
	<#if table.fields?size != (field_index + 1)>
		${cfg.joinName}.${field.name} AS ${field.name},  
	<#else>
		${cfg.joinName}.${field.name} AS ${field.name} 
	</#if>
</#list>
    </sql>
</#if>

</#if>
<#if !cfg.subTableFlag><#-- 不为子表 -->
    <!-- 排序条件 -->
    <sql id="orderSql">
        order by
        <choose>
            <!--
            <when test="orderName == 'alicem'">alicem</when>
            -->
            <#if primaryKey??>
            <otherwise>${primaryKey.name}</otherwise>
            </#if>
        </choose>
        <choose>
            <when test="order == 'asc'">asc</when>
            <when test="order == 'desc'">desc</when>
            <otherwise>desc</otherwise>
        </choose>
    </sql>

    <!-- 比较条件 -->
    <sql id="whereSql">
        <where>
            <trim prefixOverrides="and">
                <!--
                <if test="alicemA != null and alicemA != '' ">
                    and alicem_a like concat('%',${r'#{alicemA}'},'%')
                </if>
                -->
                <#if cfg.fdFlag>and ${cfg.fdFieldName} != <#if cfg.fdFieldType == 'int'>${cfg.fdValue}<#else>'${cfg.fdValue}'</#if> <!-- 删除状态  --></#if> 
            </trim>
        </where>
    </sql>
</#if>
    
<#if cfg.fdFlag>   
    <!-- 删除状态条件 -->
	<sql id="where_delete_status">
		${cfg.fdFieldName} != <#if cfg.fdFieldType == 'int'>${cfg.fdValue}<#else>'${cfg.fdValue}'</#if>	
  	</sql>
</#if>

<#if !cfg.subTableFlag><#-- 不为子表 -->
    <!-- 分页 -->
    <sql id="pageFoot">
        offset (${r'#{pageNo}'}-1)*${r'#{pageSize}'} row fetch next ${r'#{pageSize}'}
        row only
    </sql>

    <!-- 根据条件分页查询 -->
    <select id="listByQuerys" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List" />
        FROM ${table.name}
        <include refid="whereSql" />
        <include refid="orderSql" />
        <include refid="pageFoot" />
    </select>

    <!-- 根据条件查询总记录数 -->
    <select id="countByQuerys" resultType="java.lang.Long">
        SELECT COUNT(<#if primaryKey??>${primaryKey.name}<#else>*</#if>) FROM ${table.name} with(nolock)
        <include refid="whereSql" />
    </select>
</#if>

<#if cfg.subTableFlag><#-- 是否为子表 -->
	<!-- 根据主表id删除 -->
	<delete id="deleteByMainIds">
        delete from ${table.name} where ${cfg.mainTableIdName} in
        <foreach collection="mainIds" item="mainId" index="index" open="(" separator="," close=")">
        ${r'#{mainId}'}
        </foreach>
    </delete>
</#if>

<#if primaryKey??>
<#if cfg.fdFlag>
    <!-- 根据主键数组移除多条记录 记录任然存在 -->
    <update id="updateIsDeleteByPrimaryKeys">
        update ${table.name} set ${cfg.fdFieldName} = <#if cfg.fdFieldType == 'int'>${cfg.fdValue}<#else>'${cfg.fdValue}'</#if>
        where ${primaryKey.name} in
        <foreach collection="primaryKeys" item="primaryKey" index="index" open="(" separator="," close=")">
        ${r'#{'}primaryKey${r'}'}
        </foreach>
    </update>
</#if>

<#if cfg.deleteMethodFlag>
    <!-- 根据主键数组删除多条记录 -->
    <delete id="deleteByPrimaryKeys">
        delete from ${table.name} where ${primaryKey.name} in
        <foreach collection="primaryKeys" item="primaryKey" index="index" open="(" separator="," close=")">
        ${r'#{'}primaryKey${r'}'}
        </foreach>
    </delete>
</#if>
</#if>

<#if primaryKey??>
    <!-- 根据主键查询单条数据 -->
    <select id="selectByPrimaryKey" parameterType="java.lang.Integer" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from ${table.name}
        where ${primaryKey.name} = ${r'#{'}${primaryKey.propertyName}${r',jdbcType='}<#if primaryKey.type?upper_case == 'INT'>INTEGER<#else>${primaryKey.type?upper_case}</#if>${r'}'}
    	<#if cfg.fdFlag>
    	and <include refid="where_delete_status" /> 
    	</#if>
    </select>
</#if>

<#if primaryKey??>
    <!-- 根据主键删除单条数据 -->
    <delete id="deleteByPrimaryKey" parameterType="java.lang.Integer">
        delete from ${table.name}
        where ${primaryKey.name} = ${r'#{'}${primaryKey.propertyName}${r',jdbcType='}<#if primaryKey.type?upper_case == 'INT'>INTEGER<#else>${primaryKey.type?upper_case}</#if>${r'}'}
    </delete>
</#if>

    <!-- 插入单条数据 -->
    <insert id="insert" parameterType="${package.Entity}.${entity}" <#if primaryKey??>useGeneratedKeys="true" keyProperty="${primaryKey.propertyName}"</#if>>
        insert into ${table.name} (
        <include refid="Base_Column_List" />
        )values (
<#list table.fields as field>
        ${r'#{'}${field.propertyName}${r',jdbcType='}<#if field.type?upper_case == 'INT'>INTEGER<#elseif field.type?upper_case == 'DATETIME'>TIMESTAMP<#else>${field.type?upper_case}</#if>${r'}'}<#if field_has_next>,</#if>
</#list>
    )
    </insert>

    <!-- 插入单条数据,忽略空值 -->
    <insert id="insertSelective" parameterType="${package.Entity}.${entity}" <#if primaryKey??>useGeneratedKeys="true" keyProperty="${primaryKey.propertyName}"</#if>>
        insert into ${table.name}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list table.fields as field>
         <if test="${field.propertyName} != null">
             ${field.name},
         </if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#list table.fields as field>
        <if test="${field.propertyName} != null">
        ${r'#{'}${field.propertyName}${r',jdbcType='}<#if field.type?upper_case == 'INT'>INTEGER<#elseif field.type?upper_case == 'DATETIME'>TIMESTAMP<#else>${field.type?upper_case}</#if>${r'}'},
        </if>
        </#list>
        </trim>
    </insert>
<#if primaryKey??>
    <!-- 根据主键更新单条数据,忽略空值 -->
    <update id="updateByPrimaryKeySelective" parameterType="${package.Entity}.${entity}">
        update ${table.name}
        <set>
        <#list table.fields as field>
            <#if !field.keyFlag>
            <if test="${field.propertyName} != null">
            ${field.name} = ${r'#{'}${field.propertyName}${r',jdbcType='}<#if field.type?upper_case == 'INT'>INTEGER<#elseif field.type?upper_case == 'DATETIME'>TIMESTAMP<#else>${field.type?upper_case}</#if>${r'}'},
            </if>
            </#if>
        </#list>
        </set>
        where ${primaryKey.name} = ${r'#{'}${primaryKey.propertyName}${r',jdbcType='}<#if primaryKey.type?upper_case == 'INT'>INTEGER<#else>${primaryKey.type?upper_case}</#if>${r'}'}
    </update>
</#if>

<#if primaryKey??>
    <!-- 根据主键更新单条数据 -->
    <update id="updateByPrimaryKey" parameterType="${package.Entity}.${entity}">
        update ${table.name} set
        <#list table.fields as field>
            <#if !field.keyFlag>
        ${field.name} = ${r'#{'}${field.propertyName}${r',jdbcType='}<#if field.type?upper_case == 'INT'>INTEGER<#elseif field.type?upper_case == 'DATETIME'>TIMESTAMP<#else>${field.type?upper_case}</#if>${r'}'}<#if field_has_next>,</#if>
            </#if>
        </#list>
        where ${primaryKey.name} = ${r'#{'}${primaryKey.propertyName}${r',jdbcType='}<#if primaryKey.type?upper_case == 'INT'>INTEGER<#else>${primaryKey.type?upper_case}</#if>${r'}'}
    </update>
</#if>
</mapper>
