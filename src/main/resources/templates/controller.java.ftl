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

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};

/**
 * <p>
 * <#if table.comment??>${table.comment}<#else>${entity}</#if> 前端控制器
 * </p>
 * @author ${author}
 * @date ${date}
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("<#if cfg.modelName??>/${cfg.modelName}</#if>/${entity?uncap_first}")
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
    public Map<String, Object> get${entity}By${primaryKey.propertyName?cap_first}(@RequestParam(required = true, value = "${primaryKey.propertyName}") Integer ${primaryKey.propertyName}){
    	return JsonResult.failed(0, "", ${entity?uncap_first}Service.getBy${primaryKey.propertyName?cap_first}(${primaryKey.propertyName}));
    }
</#if>

<#if primaryKey??>
    /**
    * 插入或更新 带有主键id即更新  否则即插入
    * @param ${entity?uncap_first}
    * @return 插入成功返回插入后的主键id,更新成功返回更新记录的条数
    */
    @ForbidRepeatCommit(checkRepeat = true)
    @PostMapping("/save")
    public Map<String, Object> save${entity}(@RequestBody ${entity} ${entity?uncap_first}){
        return JsonResult.failed(0, StringUtils.isEmpty(${entity?uncap_first}.get${primaryKey.propertyName?cap_first}()) ? "插入" : "更新", ${entity?uncap_first}Service.save(${entity?uncap_first}));
    }
</#if>

<#if primaryKey??>
<#if cfg.fdFlag>
    /**
    * 根据主键数组移除多条记录  修改删除状态,数据实际存在
    * @param ${primaryKey.propertyName}s 主键数组
    * @return
    */
    @PostMapping("/remove")
    public Map<String, Object> remove${entity}By${primaryKey.propertyName?cap_first}s(@RequestParam(required = true) String ${primaryKey.propertyName}s){
        String[] idStrArr = ${primaryKey.propertyName}s.split(",");
        int[] idsArr = Arrays.stream(idStrArr).mapToInt(Integer::parseInt).toArray();
        return JsonResult.failed(0, "", ${entity?uncap_first}Service.removeBy${primaryKey.propertyName?cap_first}s(idsArr));
    }
</#if>

<#if cfg.DeleteMethodFlag>
    /**
    * 根据主键数组删除多条记录
    * @param ${primaryKey.propertyName}s 主键数组
    * @return
    */
    @PostMapping("/delete")
    public Map<String, Object> delete${entity}By${primaryKey.propertyName?cap_first}s(@RequestParam(required = true) String ${primaryKey.propertyName}s){
        String[] idStrArr = ${primaryKey.propertyName}s.split(",");
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
}
