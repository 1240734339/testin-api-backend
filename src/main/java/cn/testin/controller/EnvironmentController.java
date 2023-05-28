package cn.testin.controller;

import cn.testin.pojo.domain.TestEnvironmentDo;
import cn.testin.pojo.dto.environment.EnvConfigResult;
import cn.testin.pojo.dto.request.EnvironmentRequest;
import cn.testin.service.EnvironmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RequestMapping("/environment")
@Controller
@ResponseBody
@Api(tags = "环境相关接口")
public class EnvironmentController {

    @Resource
    private EnvironmentService environmentService;
    //该注释用于创建Api文档
    @ApiOperation("根据项目查询环境列表")
    @PostMapping("/list/{projectId}")
    public List<TestEnvironmentDo> listEnvByProjectId(@PathVariable String projectId,@RequestBody EnvironmentRequest request) {
        return environmentService.list(projectId,request);
    }

    @ApiOperation("新增环境")
    @PostMapping("/add")
    public String addEnv(@RequestBody TestEnvironmentDo testEnvironmentDo) {
        return environmentService.add(testEnvironmentDo);
    }

    @ApiOperation("更新环境")
    @PostMapping(value = "/update")
    public void updateEnv(@RequestBody TestEnvironmentDo apiTestEnvironment) {
        environmentService.update(apiTestEnvironment);
    }

    @ApiOperation("删除单个环境")
    @PostMapping("/delete/{id}")
    public void deleteEnv(@PathVariable String id) {
        environmentService.delete(id);
    }

    @PostMapping("/view/{id}")
    @ApiOperation("查看单个环境")
    public TestEnvironmentDo getEnv(@PathVariable String id){
        return environmentService.getById(id);
    }

    @PostMapping("/listProtocol/{projectId}")
    @ApiOperation("显示当前项目下排重的环境协议配置名称")
    public List<EnvConfigResult> listProtocolName(@PathVariable String projectId){
        return environmentService.listProtocolName(projectId);
    }

    @PostMapping("/listDatabase/{projectId}")
    @ApiOperation("显示当前项目下排重的数据库配置名称")
    public List<EnvConfigResult> listDatabaseName(@PathVariable String projectId){
        return environmentService.listDatabaseName(projectId);
    }

}
