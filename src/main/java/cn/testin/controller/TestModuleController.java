package cn.testin.controller;

import cn.testin.pojo.domain.TestModuleDo;
import cn.testin.pojo.dto.TestModuleDoDTO;
import cn.testin.service.TestModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/module")
@Controller
@ResponseBody
@Api(tags = "模块相关接口")
public class TestModuleController {
    @Resource
    private TestModuleService testModuleService;

    @ApiOperation("根据项目以及协议查询")
    @PostMapping("/list/{projectId}/{protocol}/{type}")
    public List<TestModuleDoDTO> listNode(@PathVariable String projectId, @PathVariable String protocol,@PathVariable String type) {
        return testModuleService.getByProjectIdAndProtocolAndType(projectId, protocol,type);
    }

    @PostMapping("/add")
    @ApiOperation("新增模块")
    public String addNode(@RequestBody TestModuleDo node) {
        return testModuleService.addNode(node);
    }

    @PostMapping("/edit")
    @ApiOperation("修改模块")
    public int editNode(@RequestBody TestModuleDo node) {
        return testModuleService.editNode(node);
    }

    @PostMapping("/delete")
    @ApiOperation("删除模块")
    public int deleteNode(@RequestBody List<String> nodeIds) {
        //nodeIds 包含删除节点ID及其所有子节点ID
        return testModuleService.deleteNode(nodeIds);
    }

    @ApiOperation("列出当前目录树下的子目录")
    @PostMapping("/listChildren/{parentId}")
    public List<TestModuleDoDTO> listNode(@PathVariable String parentId) {
        return testModuleService.listByParentId(parentId);
    }


}
