package cn.testin.commons.utils;

import java.util.UUID;

/**
 * @author WangG
 * @title: UUidUitl
 * @description:
 */
public class UUidUitl {
    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
