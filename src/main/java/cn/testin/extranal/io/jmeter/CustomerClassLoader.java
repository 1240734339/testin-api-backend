package cn.testin.extranal.io.jmeter;

import cn.testin.commons.utils.LogUtil;
import com.alibaba.fastjson.JSON;
import groovy.lang.GroovyClassLoader;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.List;

public class CustomerClassLoader {
    public static CustomerDynamicClassLoader loadJar(List<String> jarPaths) {
        if (CollectionUtils.isEmpty(jarPaths)) {
            return null;
        }
        LogUtil.info("开始初始化JAR[ " + JSON.toJSONString(jarPaths) + " ]");
        CustomerDynamicClassLoader urlClassLoader = new CustomerDynamicClassLoader();
        jarPaths.forEach(path -> {
            try {
                if (StringUtils.isNotEmpty(path)) {
                    File jarFile = new File(path);
                    if (jarFile.exists()) {
                        URL jarUrl = jarFile.toURI().toURL();
                        urlClassLoader.addURLFile(jarUrl);
                    }
                }
                LogUtil.info("完成初始化JAR[ " + path + " ]");
            } catch (Exception ex) {
                LogUtil.error("加载JAR包失败：", ex);
            }
        });
        return urlClassLoader;
    }

    public static GroovyClassLoader getDynamic(List<String> jarPaths) {
        if (CollectionUtils.isNotEmpty(jarPaths)) {
            GroovyClassLoader dynamicClassLoader = new GroovyClassLoader();
            jarPaths.forEach(path -> {
                try {
                    if (StringUtils.isNotEmpty(path)) {
                        File jarFile = new File(path);
                        if (jarFile.exists()) {
                            dynamicClassLoader.addURL(jarFile.toURI().toURL());
                            LogUtil.info("初始化JAR[ " + path + " ] 成功");
                        } else {
                            LogUtil.info("JAR文件：【" + path + "】已经丢失，加载失败");
                        }
                    }
                } catch (Exception ex) {
                    LogUtil.error("加载JAR包失败：", ex);
                }
            });
            return dynamicClassLoader;
        }
        return null;
    }

    private CustomerClassLoader() {
    }
}
