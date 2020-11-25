package ${package.Controller};

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.combest.main.unit.JsonResult;
import com.combest.annotate.submit.ForbidRepeatCommit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

<#if cfg.swaggerFlag>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
</#if>

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};

/**
 * <p><#if table.comment??>${table.comment}<#else>${entity}</#if> 前端控制器
 * @author ${author}
 * @date ${date}
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("<#if cfg.modelName??>/${cfg.modelName}</#if>/${entity?uncap_first}")
<#if cfg.swaggerFlag>
@Api(tags = "")
</#if>
public class ${table.controllerName} {
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign primaryKey=field/>
    </#if>
</#list>

    @Autowired
    private ${entity}Service ${entity?uncap_first}Service;

<#if primaryKey??>
    @ForbidRepeatCommit(createRepeatToken = true)
    @GetMapping("/getBy${primaryKey.propertyName?cap_first}")
    <#if cfg.swaggerFlag>
    @ApiOperation("根据主键${primaryKey.propertyName}查询单条记录")
    @ApiImplicitParam(name = "${primaryKey.propertyName}",value = "主键${primaryKey.propertyName}",required = true,dataType = "integer",paramType = "query")
    </#if><#-- @RequestParam(required = true, value = "${primaryKey.propertyName}") Integer ${primaryKey.propertyName} -->
    public Map<String, Object> get${entity}By${primaryKey.propertyName?cap_first}(@RequestParam(required = true, value = "id") Integer id){
    	return JsonResult.failed(0, "", ${entity?uncap_first}Service.getBy${primaryKey.propertyName?cap_first}(id));
    }
</#if>

<#if primaryKey??>
    /**
    * <p>插入或更新 带有主键${primaryKey.propertyName}即更新  否则即插入
    * @param ${entity?uncap_first}
    * @return 插入成功返回插入后的主键id,更新成功返回更新记录的条数
    */
    @ForbidRepeatCommit(checkRepeat = true)
    @PostMapping("/save")
    <#if cfg.swaggerFlag>
    @ApiOperation("插入或更新 带有主键${primaryKey.propertyName}即更新  否则即插入")
    </#if>
    public Map<String, Object> save${entity}(@RequestBody ${entity} ${entity?uncap_first}){
        return JsonResult.failed(0, StringUtils.isEmpty(${entity?uncap_first}.get${primaryKey.propertyName?cap_first}()) ? "insert" : "update", ${entity?uncap_first}Service.save(${entity?uncap_first}));
    }
</#if>

<#if !cfg.subTableFlag><#-- 是否为子表 -->
<#if primaryKey??>
<#if cfg.fdFlag>
    /**
    * <p>根据主键字符串移除多条记录  修改删除状态,数据实际存在
    * @param ids 主键数组
    * @return
    */
    @PostMapping("/remove")
    <#if cfg.swaggerFlag>
    @ApiOperation("根据主键数组移除多条记录,修改删除状态,数据实际存在")
    @ApiImplicitParam(name = "ids",value = "主键字符串,用逗号分隔",required = true,dataType = "string",paramType = "query")
    </#if>
    public Map<String, Object> remove${entity}By${primaryKey.propertyName?cap_first}s(@RequestParam(required = true) String ids){
        String[] idStrArr = ids.split(",");
        int[] idsArr = Arrays.stream(idStrArr).mapToInt(Integer::parseInt).toArray();
        return JsonResult.failed(0, "", ${entity?uncap_first}Service.removeBy${primaryKey.propertyName?cap_first}s(idsArr));
    }
</#if>

<#if cfg.deleteMethodFlag>
    /**
    * <p>根据主键字符串删除多条记录
    * @param ids 主键数组
    * @return
    */
    @PostMapping("/delete")
    <#if cfg.swaggerFlag>
    @ApiOperation("根据主键字符串删除多条记录")
    @ApiImplicitParam(name = "ids",value = "主键字符串,用逗号分隔",required = true,dataType = "string",paramType = "query")
    </#if>
    public Map<String, Object> delete${entity}By${primaryKey.propertyName?cap_first}s(@RequestParam(required = true) String ids){
        String[] idStrArr = ids.split(",");
        int[] idsArr = Arrays.stream(idStrArr).mapToInt(Integer::parseInt).toArray();
        return JsonResult.failed(0, "", ${entity?uncap_first}Service.deleteBy${primaryKey.propertyName?cap_first}s(idsArr));
    }
</#if>
</#if>


    /**
    * @limit  每页大小
    * @page   当前页码
    * @return 根据搜索条件查询分页信息
    */
    @ForbidRepeatCommit(createRepeatToken = true)
    @PostMapping("/list")
    <#if cfg.swaggerFlag>
    @ApiOperation("根据搜索条件查询分页信息")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "limit",value = "每页大小",required = true,dataType = "integer",paramType = "query",defaultValue = "20"),
    	@ApiImplicitParam(name = "page",value = "当前页码",required = true,dataType = "integer",paramType = "query",defaultValue = "1")
    })
    </#if>
    public Map<String, Object> get${entity}List(
    @RequestParam(required = true, value = "limit", defaultValue = "20") Integer limit,
    @RequestParam(required = true, value = "page", defaultValue = "1") Integer page) {

        // 搜索条件
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", page);
        params.put("pageSize", limit);

        Map<String, Object> resultMap = JsonResult.failed(0, "", ${entity?uncap_first}Service.listByQuerys(params));//查记录
        resultMap.put("count", ${entity?uncap_first}Service.countByQuerys(params));//总记录数
        return resultMap;
    }
</#if><#-- 是否为子表 -->
}
