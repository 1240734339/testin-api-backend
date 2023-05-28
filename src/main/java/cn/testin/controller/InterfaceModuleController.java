package cn.testin.controller;

import cn.testin.pojo.domain.TestInterfaceModuleDo;
import cn.testin.pojo.dto.TestInterfaceModuleDoDTO;
import cn.testin.service.InterfaceModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/interface/module")
@ResponseBody
@Api(tags = "接口模块相关接口")
public class InterfaceModuleController {

    @Resource
    private InterfaceModuleService interfaceModuleService;

    @ApiOperation("根据项目以及协议查询")
    @PostMapping("/list/{projectId}/{protocol}")
    public List<TestInterfaceModuleDoDTO> listNodeByProjectId(@PathVariable String projectId, @PathVariable String protocol) {
        return interfaceModuleService.getNodeTreeByProjectId(projectId, protocol);
    }

    @PostMapping("/add")
    @ApiOperation("新增接口模块")
    public String addNode(@RequestBody TestInterfaceModuleDo node) {
        return interfaceModuleService.addNode(node);
    }

    @PostMapping("/edit")
    @ApiOperation("修改接口模块")
    public int editNode(@RequestBody TestInterfaceModuleDo node) {
        return interfaceModuleService.editNode(node);
    }

    @PostMapping("/delete")
    @ApiOperation("删除接口模块")
    public int deleteNode(@RequestBody List<String> nodeIds) {
        //nodeIds 包含删除节点ID及其所有子节点ID
        return interfaceModuleService.deleteNode(nodeIds);
    }
}
