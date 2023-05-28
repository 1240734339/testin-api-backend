package cn.testin.service;

import cn.testin.commons.constants.TestCaseConstants;
import cn.testin.commons.exception.TIException;
import cn.testin.mapper.ext.ExtTestInterfaceModuleDoMapper;
import cn.testin.pojo.dto.TestInterfaceModuleDoDTO;
import cn.testin.mapper.TestInterfaceModuleDoMapper;
import cn.testin.pojo.domain.TestInterfaceModuleDo;
import cn.testin.pojo.domain.TestInterfaceModuleDoExample;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class InterfaceModuleService extends NodeTreeService<TestInterfaceModuleDoDTO>{

    @Resource
    private TestInterfaceModuleDoMapper testInterfaceModuleDoMapper;

    @Resource
    private ExtTestInterfaceModuleDoMapper extTestInterfaceModuleDoMapper;

    public InterfaceModuleService() {
        super(TestInterfaceModuleDoDTO.class);
    }

    public String addNode(TestInterfaceModuleDo node) {
        validateNode(node);
        return addNodeWithoutValidate(node);
    }

    private void validateNode(TestInterfaceModuleDo node) {
        if (node.getLevel() > TestCaseConstants.MAX_NODE_DEPTH) {
            TIException.throwException("模块树最大深度为"
                    + TestCaseConstants.MAX_NODE_DEPTH + "层");
        }
        checkInterfaceModuleExist(node);
    }

    private void checkInterfaceModuleExist(TestInterfaceModuleDo node) {
        if (node.getName() != null) {
            TestInterfaceModuleDoExample example = new TestInterfaceModuleDoExample();
            TestInterfaceModuleDoExample.Criteria criteria = example.createCriteria();
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
            if (testInterfaceModuleDoMapper.selectByExample(example).size() > 0) {
                TIException.throwException("同层级下已存在该模块名称:" + node.getName());
            }
        }
    }

    public String addNodeWithoutValidate(TestInterfaceModuleDo node) {
        node.setCreateTime(System.currentTimeMillis());
        node.setUpdateTime(System.currentTimeMillis());
        node.setId(UUID.randomUUID().toString());
        testInterfaceModuleDoMapper.insertSelective(node);
        return node.getId();
    }

    public int editNode(TestInterfaceModuleDo request) {
        request.setUpdateTime(System.currentTimeMillis());
        checkInterfaceModuleExist(request);
        return testInterfaceModuleDoMapper.updateByPrimaryKeySelective(request);
    }

    public int deleteNode(List<String> nodeIds) {
        if (CollectionUtils.isNotEmpty(nodeIds)) {
            TestInterfaceModuleDoExample apiDefinitionNodeExample = new TestInterfaceModuleDoExample();
            apiDefinitionNodeExample.createCriteria().andIdIn(nodeIds);
            return testInterfaceModuleDoMapper.deleteByExample(apiDefinitionNodeExample);
        }
        return 0;
    }

    public List<TestInterfaceModuleDoDTO> getNodeTreeByProjectId(String projectId, String protocol) {
        // 判断当前项目下是否有默认模块，没有添加默认模块
        this.getDefaultNode(projectId, protocol);
        List<TestInterfaceModuleDoDTO> interfaceModules = getByProjectAndProtocol(projectId, protocol);
        /*ApiDefinitionRequest request = new ApiDefinitionRequest();
        request.setProjectId(projectId);
        request.setProtocol(protocol);
        List<String> list = new ArrayList<>();
        list.add("Prepare");
        list.add("Underway");
        list.add("Completed");
        Map<String, List<String>> filters = new LinkedHashMap<>();
        filters.put("status", list);
        request.setFilters(filters);

        //优化： 所有统计SQL一次查询出来
        List<String> allModuleIdList = new ArrayList<>();
        for (ApiModuleDTO node : apiModules) {
            List<String> moduleIds = new ArrayList<>();
            moduleIds = this.nodeList(apiModules, node.getId(), moduleIds);
            moduleIds.add(node.getId());
            for (String moduleId : moduleIds) {
                if (!allModuleIdList.contains(moduleId)) {
                    allModuleIdList.add(moduleId);
                }
            }
        }
        request.setModuleIds(allModuleIdList);
        List<Map<String, Object>> moduleCountList = extApiDefinitionMapper.moduleCountByCollection(request);
        Map<String, Integer> moduleCountMap = this.parseModuleCountList(moduleCountList);
        apiModules.forEach(node -> {
            List<String> moduleIds = new ArrayList<>();
            moduleIds = this.nodeList(apiModules, node.getId(), moduleIds);
            moduleIds.add(node.getId());
            int countNum = 0;
            for (String moduleId : moduleIds) {
                if (moduleCountMap.containsKey(moduleId)) {
                    countNum += moduleCountMap.get(moduleId).intValue();
                }
            }
            node.setCaseNum(countNum);
        });*/
        return getNodeTrees(interfaceModules);
    }

    public TestInterfaceModuleDo getDefaultNode(String projectId, String protocol) {
        TestInterfaceModuleDoExample example = new TestInterfaceModuleDoExample();
        example.createCriteria().andProjectIdEqualTo(projectId).andProtocolEqualTo(protocol).andNameEqualTo("未规划接口").andParentIdIsNull();
        List<TestInterfaceModuleDo> list = testInterfaceModuleDoMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            TestInterfaceModuleDo record = new TestInterfaceModuleDo();
            record.setId(UUID.randomUUID().toString());
            record.setName("未规划接口");
            record.setProtocol(protocol);
            record.setLevel(1);
            record.setCreateTime(System.currentTimeMillis());
            record.setUpdateTime(System.currentTimeMillis());
            record.setProjectId(projectId);
            testInterfaceModuleDoMapper.insert(record);
            return record;
        } else {
            return list.get(0);
        }
    }

    public List<TestInterfaceModuleDoDTO> getByProjectAndProtocol(String projectId, String protocol) {
        return extTestInterfaceModuleDoMapper.getByProjectAndProtocol(projectId,protocol);
    }
}
