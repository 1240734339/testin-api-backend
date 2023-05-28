package cn.testin.extranal.io.jmeter;


import cn.testin.commons.utils.LogUtil;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

public class CustomerDynamicClassLoader extends URLClassLoader {
    public CustomerDynamicClassLoader() {
        super(new URL[]{}, findParentClassLoader());
    }

    /**
     * 将指定的文件url添加到类加载器的classpath中去，并缓存jar connection，方便以后卸载jar
     * 一个可想类加载器的classpath中添加的文件url
     *
     * @param
     */
    public void addURLFile(URL file) {
        try {
            // 打开并缓存文件url连接
            URLConnection uc = file.openConnection();
            if (uc instanceof JarURLConnection) {
                uc.setUseCaches(true);
                ((JarURLConnection) uc).getManifest();
            }
        } catch (Exception e) {
            LogUtil.error("Failed to cache plugin JAR file: " + file.toExternalForm());
        }
        addURL(file);
    }


    /**
     * 定位基于当前上下文的父类加载器
     *
     * @return 返回可用的父类加载器.
     */
    private static ClassLoader findParentClassLoader() {
        ClassLoader parent = ClassLoader.getSystemClassLoader();
        if (parent == null) {
            parent = CustomerDynamicClassLoader.class.getClassLoader();
        }
        if (parent == null) {
            parent = ClassLoader.getSystemClassLoader();
        }
        return parent;
    }
}
