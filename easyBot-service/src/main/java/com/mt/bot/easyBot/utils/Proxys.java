package com.mt.bot.easyBot.utils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import static com.mt.bot.easyBot.common.Constants.*;

public class Proxys {
    /**
     * 获取代理
     * @return
     */
    public static Proxy getProxy(String proxyHost,int port) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, port));
        return proxy;
    }
}
