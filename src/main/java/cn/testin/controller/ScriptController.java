package cn.testin.controller;

import cn.testin.commons.utils.PageUtils;
import cn.testin.commons.utils.Pager;
import cn.testin.pojo.domain.TestScriptDo;
import cn.testin.pojo.dto.TestScriptDTO;
import cn.testin.pojo.dto.request.SaveScriptRequest;
import cn.testin.pojo.dto.request.TestScriptRequest;
import cn.testin.service.ScriptService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/script")
@ResponseBody
@Api(tags = "脚本相关接口")
public class ScriptController {

    @Resource
    private ScriptService scriptService;

    @PostMapping("/list/{goPage}/{pageSize}")
    public Pager<List<TestScriptDTO>> listSimple(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody TestScriptRequest request) {
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, scriptService.listSimple(request));
    }

    @PostMapping("/add")
    @ApiOperation("新增脚本")
    public TestScriptDo scriptAdd(@RequestBody SaveScriptRequest request){
        return scriptService.add(request);
    }

    @PostMapping(value = "/update")
    public TestScriptDo update(@RequestBody SaveScriptRequest request) {
        return scriptService.update(request);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        scriptService.delete(id);
    }
}
