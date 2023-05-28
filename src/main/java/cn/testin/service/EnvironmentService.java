package cn.testin.service;

import cn.testin.commons.exception.TIException;
import cn.testin.mapper.TestEnvironmentDoMapper;
import cn.testin.pojo.domain.TestEnvironmentDo;
import cn.testin.pojo.domain.TestEnvironmentDoExample;
import cn.testin.pojo.dto.environment.DatabaseConfig;
import cn.testin.pojo.dto.environment.EnvConfigResult;
import cn.testin.pojo.dto.environment.EnvironmentConfig;
import cn.testin.pojo.dto.environment.HttpConfig;
import cn.testin.pojo.dto.request.EnvironmentRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)//回滚事务
public class EnvironmentService {

    @Resource
    private TestEnvironmentDoMapper testEnvironmentDoMapper;

    public List<TestEnvironmentDo> list(String projectId) {
        TestEnvironmentDoExample example = new TestEnvironmentDoExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        example.setOrderByClause("update_time desc");
        return testEnvironmentDoMapper.selectByExampleWithBLOBs(example);
    }

    public List<TestEnvironmentDo> list(String projectId, EnvironmentRequest request) {
        TestEnvironmentDoExample example = new TestEnvironmentDoExample();
        TestEnvironmentDoExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        if (StringUtils.isNotBlank(request.getName())){
            criteria.andNameLike(request.getName());
        }
        example.setOrderByClause("create_time desc");
        return testEnvironmentDoMapper.selectByExampleWithBLOBs(example);
    }

    public String add(TestEnvironmentDo request){
        request.setId(UUID.randomUUID().toString());
        checkEnvironmentExist(request);
        request.setCreateTime(System.currentTimeMillis());
        request.setUpdateTime(System.currentTimeMillis());
        testEnvironmentDoMapper.insert(request);
        return request.getId();
    }

    public void delete(String id) {
        testEnvironmentDoMapper.deleteByPrimaryKey(id);
    }

    public void update(TestEnvironmentDo apiTestEnvironment) {
        checkEnvironmentExist(apiTestEnvironment);
        apiTestEnvironment.setUpdateTime(System.currentTimeMillis());
        testEnvironmentDoMapper.updateByPrimaryKeyWithBLOBs(apiTestEnvironment);
    }

    /**
     * @param envId 环境id
     * @return cn.testin.pojo.domain.TestEnvironmentDo
     * @Description 通过环境id查询数据
     **/
    public TestEnvironmentDo findByEnvId(String envId) {
        TestEnvironmentDo testEnvironmentDo = testEnvironmentDoMapper.selectByPrimaryKey(envId);
        return testEnvironmentDo;
    }

    private void checkEnvironmentExist(TestEnvironmentDo environment) {
        if (environment.getName() != null) {
            if (StringUtils.isEmpty(environment.getProjectId())) {
                TIException.throwException("项目ID不能为空");
            }
            TestEnvironmentDoExample example = new TestEnvironmentDoExample();
            TestEnvironmentDoExample.Criteria criteria = example.createCriteria();
            criteria.andNameEqualTo(environment.getName())
                    .andProjectIdEqualTo(environment.getProjectId());
            if (StringUtils.isNotBlank(environment.getId())) {
                criteria.andIdNotEqualTo(environment.getId());
            }
            if (testEnvironmentDoMapper.selectByExample(example).size() > 0) {
                TIException.throwException("该环境名称已存在！");
            }
        }
    }

    public TestEnvironmentDo getById(String envId){
        TestEnvironmentDoExample example = new TestEnvironmentDoExample();
        example.createCriteria().andIdEqualTo(envId);
        return testEnvironmentDoMapper.selectByExample(example).get(0);
    }

    public List<EnvConfigResult> listProtocolName(String projectId){
        List<EnvConfigResult> results = new ArrayList<>();
        List<TestEnvironmentDo> list = list(projectId);
        HashMap map = new HashMap<>();
        for (TestEnvironmentDo env:list){
            JSONObject json = JSONObject.parseObject(env.getConfig());
            JSONArray httpConfigs = json.getJSONArray("httpConfigs");
            if (httpConfigs.size() > 0) {
                for (Object httpConfig : httpConfigs) {
                    JSONObject config = (JSONObject) httpConfig;
                    Object id = config.get("id");
                    Object name = config.get("name");
                    EnvConfigResult result = new EnvConfigResult();
                    result.setId(id.toString());
                    result.setName(name.toString());
                    if (!map.containsValue(name)){
                        map.put(id,name);
                        results.add(result);
                    }
                }
            }
        }
        return results;
    }

    public List<EnvConfigResult> listDatabaseName(String projectId){
        List<EnvConfigResult> results = new ArrayList<>();
        List<TestEnvironmentDo> list = list(projectId);
        HashMap map = new HashMap<>();
        for (TestEnvironmentDo env:list){
            JSONObject json = JSONObject.parseObject(env.getConfig());
            JSONArray databaseConfigs = json.getJSONArray("databaseConfigs");
            if (databaseConfigs.size() > 0) {
                for (Object databaseConfig : databaseConfigs) {
                    JSONObject database = (JSONObject) databaseConfig;
                    Object id = database.get("id");
                    Object name = database.get("name");
                    EnvConfigResult result = new EnvConfigResult();
                    result.setId(id.toString());
                    result.setName(name.toString());
                    if (!map.containsValue(name)){
                        map.put(id,name);
                        results.add(result);
                    }
                }
            }
        }
        return results;
    }

    /**
     * 根据环境ID以及HttpID找到Http配置
     * @param envId
     * @param httpId
     * @return
     */
    public HttpConfig findByEnvIdAndHttpId(String envId,String httpId){
        TestEnvironmentDo environment =  this.findByEnvId(envId);
        HttpConfig httpConfig = null;
        EnvironmentConfig environmentConfig = null;
        if (environment!=null&&environment.getConfig()!=null){
            environmentConfig = JSONObject.parseObject(environment.getConfig(), EnvironmentConfig.class);
            if (CollectionUtils.isNotEmpty(environmentConfig.getHttpConfigs())){
                for (HttpConfig config:environmentConfig.getHttpConfigs()){
                    if (config.getId().equals(httpId)){
                        httpConfig=config;
                    }
                }
            }
        }
        return httpConfig;
    }

    /**
     * 根据环境ID以及DatabaseID找到数据库配置
     * @param envId
     * @param databaseId
     * @return
     */
    public DatabaseConfig findByEnvIdAndDatabaseId(String envId, String databaseId){
        TestEnvironmentDo environment =  this.findByEnvId(envId);
        DatabaseConfig databaseConfig = null;
        EnvironmentConfig environmentConfig = null;
        if (environment!=null&&environment.getConfig()!=null){
            environmentConfig = JSONObject.parseObject(environment.getConfig(), EnvironmentConfig.class);
            if (CollectionUtils.isNotEmpty(environmentConfig.getDatabaseConfigs())){
                for (DatabaseConfig config:environmentConfig.getDatabaseConfigs()){
                    if (config.getId().equals(databaseId)){
                        databaseConfig=config;
                    }

                }
            }
        }
        return databaseConfig;
    }
}
