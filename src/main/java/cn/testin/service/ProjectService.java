package cn.testin.service;

import cn.testin.mapper.TestProjectDoMapper;
import cn.testin.pojo.domain.TestProjectDo;
import cn.testin.pojo.domain.TestProjectDoExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectService {

    @Resource
    private TestProjectDoMapper testProjectDoMapper;

    public List<TestProjectDo> getProjectByIds(List<String> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            TestProjectDoExample example = new TestProjectDoExample();
            example.createCriteria().andIdIn(ids);
            return testProjectDoMapper.selectByExample(example);
        }
        return new ArrayList<>();
    }
}
