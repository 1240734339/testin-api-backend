package cn.testin.controller;

import cn.testin.commons.utils.PageUtils;
import cn.testin.commons.utils.Pager;
import cn.testin.pojo.domain.TestInterfaceDoWithBLOBs;
import cn.testin.pojo.dto.request.SaveInterfaceRequest;
import cn.testin.pojo.dto.request.TestInterfaceRequest;
import cn.testin.pojo.vo.result.TestInterfaceResult;
import cn.testin.service.InterfaceService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/interface")
@Controller
@ResponseBody
@Api(tags = "接口相关接口")
public class InterfaceController {
    @Resource
    private InterfaceService interfaceService;

    @ApiOperation("分页查询")
    @PostMapping("/list/{goPage}/{pageSize}")
    public Pager<List<TestInterfaceResult>> list(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody TestInterfaceRequest request) {
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, interfaceService.list(request));
    }

    @ApiOperation("新增接口")
    @PostMapping(value = "/add")
    public TestInterfaceDoWithBLOBs interfaceAdd (@RequestParam SaveInterfaceRequest request){
        return interfaceService.add(request);
    }

    @ApiOperation("删除单个接口")
    @PostMapping("/delete/{id}")
    public void interfaceDelete(@PathVariable String id) {
        interfaceService.delete(id);
    }

    @ApiOperation("修改接口")
    @PostMapping(value = "/update")
    public TestInterfaceDoWithBLOBs interfaceUpdate (@RequestParam SaveInterfaceRequest request){
        return interfaceService.update(request);
    }

    @ApiOperation("查看单个接口")
    @PostMapping("/view/{id}")
    public TestInterfaceResult getInterface(@PathVariable String id){
        return interfaceService.getById(id);
    }

    @ApiOperation("列出当前目录树下的接口")
    @PostMapping("/list/{moduleId}")
    public List<TestInterfaceResult> listByModule(@PathVariable String moduleId){
        return interfaceService.listByModule(moduleId);
    }

    @ApiOperation("列出当前目录及子目录下所有接口")
    @PostMapping("/listAll/{moduleId}")
    public List<TestInterfaceResult> listAllByModule(@PathVariable String moduleId){
        return interfaceService.listAllByModule(moduleId);
    }

}
