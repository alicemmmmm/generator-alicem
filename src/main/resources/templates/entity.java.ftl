package ${package.Entity};

<#list table.importPackages as pkg>
    <#if pkg?string?contains("baomidou")>
    <#else>
import ${pkg};
    </#if>
</#list>
<#list table.fields as field>
    <#if field.propertyType == 'Date'>
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
        <#break>
    </#if>
</#list>
<#list table.fields as field>
    <#if field.type?upper_case == 'DECIMAL'>
import java.math.BigDecimal;
        <#break>
    </#if>
</#list>
<#if cfg.swaggerFlag>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
</#if>

/**
 * <p><#if table.comment??>${table.comment}<#else>${entity}</#if> 实体类
 * @author ${author}
 * @date ${date}
 * @Description 数据表:${table.name}
 */
<#if entityLombokModel>
@Data
</#if>
<#if cfg.swaggerFlag>
@ApiModel(value = "${entity}", description = "<#if table.comment??>${table.comment}<#else>${entity}</#if>")
</#if>
public class ${entity} implements Serializable {

    private static final long serialVersionUID = 1L;

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
<#------------  swaggerFlag模式判断  ---------->
<#if cfg.swaggerFlag>
	<#if field.propertyType == 'Date'>
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("<#if field.comment??>${field.comment}</#if>")
    private ${field.propertyType} ${field.propertyName};
    <#elseif field.type?upper_case == 'DECIMAL'>
    @ApiModelProperty("<#if field.comment??>${field.comment}</#if>")
    private BigDecimal ${field.propertyName};
    <#else>
    @ApiModelProperty("<#if field.comment??>${field.comment}</#if>")
    private ${field.propertyType} ${field.propertyName};
    </#if>
<#else>
	<#if field.propertyType == 'Date'>
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ${field.propertyType} ${field.propertyName};<#if field.comment??> //${field.comment}</#if>
    <#elseif field.type?upper_case == 'DECIMAL'>
    private BigDecimal ${field.propertyName};<#if field.comment??> //${field.comment}</#if>
    <#else>
    private ${field.propertyType} ${field.propertyName};<#if field.comment??> //${field.comment}</#if>
    </#if>
</#if>
</#list>
<#------------  END 字段循环遍历  ---------->
}
