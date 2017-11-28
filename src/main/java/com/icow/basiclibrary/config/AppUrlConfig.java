package com.icow.basiclibrary.config;

import com.icow.basiclibrary.BasicApp;
import com.icow.basiclibrary.R;

/**
 * 服务器地址配置信息
 *
 * @author Xun.Zhang
 * @version 1.0
 */
public class AppUrlConfig {

    /**
     * 是否开启调试信息（正式环境设置为false，其余情况都设置为true）
     */
    public static final boolean DEBUG_LOG_ENABLE = false;

    /**
     * 是否开启Unity3D店铺（如果不开启走H5店铺 ）
     */
    public static final boolean UNITY_3D_SHOP_ENABLE = true;

    /**
     * Java服务器地址（IP和端口）
     */
    public static final String IP_PORT = BasicApp.getInstance().getString(R.string.IP_PORT); // 开发环境

    /**
     * 推送服务HTTP请求地址（IP和端口）
     */
    public static final String PUSH_IP_PORT = BasicApp.getInstance().getString(R.string.PUSH_IP_PORT); // 开发环境

    /**
     * 推送服务TCP请求地址（只保留IP地址，端口后在NIO库里面默认配置）
     */
    public static final String PUSH_IP = BasicApp.getInstance().getString(R.string.PUSH_IP);// 开发环境

    /**
     * Unity请求资源接口
     */
    public static final String ASSET_IP = BasicApp.getInstance().getString(R.string.ASSET_IP); // 开发环境
}
