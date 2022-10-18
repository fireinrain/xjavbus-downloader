package com.fireinrain.xjavbus.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.ForkJoinPool;

/**
 * @author : fireinrain
 * @description:
 * @site : https://github.com/fireinrain
 * @file : XjavbusConfig
 * @software: IntelliJ IDEA
 * @time : 2022/10/18 3:33 PM
 */

public class XjavbusConfig {
    public static final String javbusBaseUrl = "https://www.javbus.com/";

    public static String dataFolder = "download-data";

    public static String itemDbFilePath = "javbus.db";

    public static String resourceDbFilePath = "javbus-resource.db";


    public static Properties properties;

    public static final ForkJoinPool clientForkJoinPool = new ForkJoinPool(4);

    public static final ForkJoinPool imagesForkJoinPool = new ForkJoinPool(4);


    static {
        // https://blog.nowcoder.net/n/eae8df31faf445bb9dc4dca8bab028d7?from=nowcoder_improve
        // System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");

        Properties properties = new Properties();
        // 以下方式也是可以读取的
        // val fileInputStream = FileInputStream("src/main/resources/setting.properties")
        // properties.load(fileInputStream)
        // class.getClassLoader().getResource()
        // 是不会去搜索jar包中的配置文件的
        // 相关参考: https://stackoverflow.com/questions/37270047/java-io-filenotfoundexception-while-running-java-app-from-jar-file
        InputStream resourceAsStream = XjavbusConfig.class.getClassLoader().getResourceAsStream("setting.properties");
        InputStreamReader fileReader = null;
        try {
            assert resourceAsStream != null;
            fileReader = new InputStreamReader(resourceAsStream);
            properties.load(fileReader);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        XjavbusConfig.properties = properties;

        try {
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String dataFolderPath = XjavbusConfig.getConfig("DataFolder");
        if (null != dataFolderPath && dataFolderPath.length() > 0) {
            dataFolder = dataFolderPath;
        }

        String itemDbFile = XjavbusConfig.getConfig("ItemDbFilePath");
        if (null != itemDbFile && itemDbFile.length() > 0) {
            itemDbFilePath = dataFolder + File.separator + itemDbFile;

        }

        String resourceDbFile = XjavbusConfig.getConfig("ResourceDbFilePath");
        if (null != resourceDbFile && resourceDbFile.length() > 0) {
            resourceDbFilePath = dataFolder + File.separator + resourceDbFile;
        }

        // 生成data目录
        Path path = Paths.get(dataFolder);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static String getConfig(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        String proxyHost = XjavbusConfig.getConfig("ProxyHost");
        System.out.println(proxyHost);
        String proxyPort = XjavbusConfig.getConfig("ProxyPort");
        System.out.println(proxyPort);
        System.out.println(dataFolder);
        System.out.println(itemDbFilePath);
        System.out.println(resourceDbFilePath);
    }
}
