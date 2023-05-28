package cn.testin.service;

import cn.testin.commons.constants.TestCaseConstants;
import cn.testin.commons.exception.TIException;
import cn.testin.mapper.TestModuleDoMapper;
import cn.testin.mapper.ext.ExtTestModuleDoMapper;
import cn.testin.pojo.domain.TestModuleDo;
import cn.testin.pojo.domain.TestModuleDoExample;
import cn.testin.pojo.dto.TestModuleDoDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class TestModuleService extends NodeTreeService<TestModuleDoDTO>{

    @Resource
    private TestModuleDoMapper testModuleDoMapper;

    @Resource
    private ExtTestModuleDoMapper extTestModuleDoMapper;

    public TestModuleService() {
        super(TestModuleDoDTO.class);
    }

    public String addNode(TestModuleDo node) {
        validateNode(node);
        return addNodeWithoutValidate(node);
    }

    private void validateNode(TestModuleDo node) {
        if (node.getLevel() > TestCaseConstants.MAX_NODE_DEPTH) {
            TIException.throwException("模块树最大深度为"
                    + TestCaseConstants.MAX_NODE_DEPTH + "层");
        }
        checkModuleExist(node);
    }

    private void checkModuleExist(TestModuleDo node) {
        if (node.getName() != null) {
            TestModuleDoExample example = new TestModuleDoExample();
            TestModuleDoExample.Criteria criteria = example.createCriteria();
            criteria.andNameEqualTo(node.getName())
                    .andProjectIdEqualTo(node.getProjectId());

            if (StringUtils.isNotBlank(node.getParentId())) {
                criteria.andParentIdEqualTo(node.getParentId());
            } else {
                criteria.andLevelEqualTo(node.getLevel());
            }

            if (StringUtils.isNotBlank(node.getProtocol())) {
                criteria.andProtocolEqualTo(node.getProtocol());
            }

            if (StringUtils.isNotBlank(node.getId())) {
                criteria.andIdNotEqualTo(node.getId());
            }
            if (StringUtils.isNotBlank(node.getType())) {
                criteria.andTypeNotEqualTo(node.getType());
            }
            if (testModuleDoMapper.selectByExample(example).size() > 0) {
                TIException.throwException("同层级下已存在该模块名称:" + node.getName());
            }
        }
    }

    public String addNodeWithoutValidate(TestModuleDo node) {
        node.setCreateTime(System.currentTimeMillis());
        node.setUpdateTime(System.currentTimeMillis());
        node.setId(UUID.randomUUID().toString());
        testModuleDoMapper.insertSelective(node);
        return node.getId();
    }

    public int editNode(TestModuleDo request) {
        request.setUpdateTime(System.currentTimeMillis());
        checkModuleExist(request);
        return testModuleDoMapper.updateByPrimaryKeySelective(request);
    }

    public int deleteNode(List<String> nodeIds) {
        if (CollectionUtils.isNotEmpty(nodeIds)) {
            TestModuleDoExample apiDefinitionNodeExample = new TestModuleDoExample();
            apiDefinitionNodeExample.createCriteria().andIdIn(nodeIds);
            return testModuleDoMapper.deleteByExample(apiDefinitionNodeExample);
        }
        return 0;
    }

    /**
     * 根据项目ID，协议以及类型拿到第一层模块
     * @param projectId
     * @param protocol
     * @param type
     * @return
     */
    public List<TestModuleDoDTO> getByProjectIdAndProtocolAndType(String projectId, String protocol,String type) {
        // 判断当前项目下是否有默认模块，没有添加默认模块
        this.getDefaultNode(projectId, protocol,type);
        List<TestModuleDoDTO> Modules = extTestModuleDoMapper.getByProjectAndProtocolAndType(projectId,protocol,type);
        return Modules;
    }

    /**
     * 添加默认模块
     * @param projectId
     * @param protocol
     * @param type
     * @return
     */
    public TestModuleDo getDefaultNode(String projectId, String protocol,String type) {
        TestModuleDoExample example = new TestModuleDoExample();
        example.createCriteria().andProjectIdEqualTo(projectId).andProtocolEqualTo(protocol).andTypeEqualTo(type).andNameEqualTo("未分类接口").andParentIdIsNull();
        List<TestModuleDo> list = testModuleDoMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            TestModuleDo record = new TestModuleDo();
            record.setId(UUID.randomUUID().toString());
            record.setName("未分类接口");
            record.setProtocol(protocol);
            record.setLevel(1);
            record.setCreateTime(System.currentTimeMillis());
            record.setUpdateTime(System.currentTimeMillis());
            record.setProjectId(projectId);
            record.setType(type);
            testModuleDoMapper.insert(record);
            return record;
        } else {
            return list.get(0);
        }
    }

    /**
     * 根据模块ID获取下一级目录
     * @param parentId
     * @return
     */
    public List<TestModuleDoDTO> listByParentId(String parentId){
        return extTestModuleDoMapper.listByParentId(parentId);
    }

    /**
     * 根据模块ID递归获取所有子目录
     * @param result
     * @param moduleId
     * @return
     */
    public List<TestModuleDoDTO> listTree(List<TestModuleDoDTO> result,String moduleId){
       List<TestModuleDoDTO> list = listByParentId(moduleId);
       result.addAll(list);
       for (TestModuleDoDTO module:list){
            if (listByParentId(module.getId())!=null){
                listTree(result,module.getId());
            }
       }
       return result;
    }

    /**
     * 根据模块ID获取自身以及所有子目录ID
     * @param moduleId
     * @return
     */
    public List<String> listModuleIds(String moduleId){
        List<String> result = new ArrayList<>();
        result.add(moduleId);
        List<TestModuleDoDTO> list = listTree(new ArrayList<>(),moduleId);
        for (TestModuleDoDTO module:list){
            result.add(module.getId());
        }
        return result;
    }

}

