package com.fireinrain.xjavbus.spider;

import com.fireinrain.xjavbus.config.XjavbusConfig;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author : fireinrain
 * @description : 抽象爬虫类
 * @site : https://github.com/fireinrain
 * @file : AbstractHttpSpider
 * @software: IntelliJ IDEA
 * @time : 2022/10/18 3:24 PM
 */

public class AbstractHttpSpider {
    public static final Logger logger = LoggerFactory.getLogger(AbstractHttpSpider.class);

    public static OkHttpClient client = null;

    static {
        java.util.logging.Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new RetryInterceptor(2)).readTimeout(60 * 8, TimeUnit.SECONDS).retryOnConnectionFailure(true).connectTimeout(60 * 8, TimeUnit.SECONDS).connectionPool(new ConnectionPool(16, 16, TimeUnit.SECONDS)).build();
        // 先判断是否能访问外网 否则使用代理
        String checkUrl = "https://www.javbus.com";
        Request url = new Request.Builder().url(checkUrl).get().build();
        try (Response response = okHttpClient.newCall(url).execute();) {

            if (response.code() == 200) {
                logger.info("can access site: " + checkUrl + ", dont need proxy.");
                client = okHttpClient;
            }
            if (response.code() == 400 || response.code() == 403) {
                throw new SocketTimeoutException();
            }
        } catch (IOException e) {
            // throw new RuntimeException(e);
            StringBuilder info = new StringBuilder();
            info.append("can't access site: ").append(checkUrl).append(" use proxy at ").append(XjavbusConfig.getConfig("ProxyHost")).append(":").append(XjavbusConfig.getConfig("ProxyPort"));
            logger.info(info.toString());
            OkHttpClient httpClient = new OkHttpClient.Builder().readTimeout(60 * 8, TimeUnit.SECONDS).retryOnConnectionFailure(false).connectTimeout(60 * 8, TimeUnit.SECONDS).connectionPool(new ConnectionPool(10, 10, TimeUnit.SECONDS)).proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(XjavbusConfig.getConfig("ProxyHost"), Integer.parseInt(XjavbusConfig.getConfig("ProxyPort"))))).build();
            try (Response response = httpClient.newCall(url).execute();) {

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            client = httpClient;
        }
    }
}
