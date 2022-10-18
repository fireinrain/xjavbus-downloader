package com.fireinrain.xjavbus.sqlite;

import com.fireinrain.xjavbus.config.XjavbusConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @Description:
 * @Author : fireinrain
 * @Site : https://github.com/fireinrain
 * @File : DbManager
 * @Software: IntelliJ IDEA
 * @Time : 2022/9/14 7:38 PM
 */

public class DbManager {
    private static final Logger logger = LoggerFactory.getLogger(DbManager.class);

    private static final String itemDbFilePath = XjavbusConfig.itemDbFilePath;

    private static final String resourceDbFilePath = XjavbusConfig.resourceDbFilePath;

    private static final String itemDbUrl = "jdbc:sqlite:" + itemDbFilePath;

    private static final String resourceDbUrl = "jdbc:sqlite:" + resourceDbFilePath;


    private static Connection itemDbconnection;

    private static Connection resourceDbConnection;


    static {
        checkIfHasDbFile();
        try {
            itemDbconnection = DriverManager.getConnection(itemDbUrl);
            resourceDbConnection = DriverManager.getConnection(resourceDbUrl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkIfHasDbFile() {
        Path path = Paths.get(itemDbFilePath);
        boolean exists = Files.exists(path);
        if (!exists) {
            logger.warn("javbus.db数据库文件: " + itemDbFilePath + "不存在!!!");
            logger.info("自动创建......");
            createDatabase(itemDbFilePath);
        } else {
            logger.info(itemDbFilePath + "数据库文件已存在......");
        }
        Path path2 = Paths.get(resourceDbFilePath);
        boolean exists2 = Files.exists(path2);
        if (!exists2) {
            logger.warn("javbus-resource.db数据库文件: " + resourceDbFilePath + "不存在!!!");
            logger.info("自动创建......");
            createDatabase(resourceDbFilePath);
        } else {
            logger.info(resourceDbFilePath + "数据库文件已存在......");
        }

    }

    public static Connection getItemDbConnection() {
        return itemDbconnection;
    }

    public static Connection getResourceDbConnection() {
        return resourceDbConnection;
    }


    public static void createDatabase(String fileName) {
        String url = "jdbc:sqlite:" + fileName;

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                logger.info("the driver name is " + meta.getDriverName());
                logger.info("a new database has been created.");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                Objects.requireNonNull(conn).close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 不使用代码创建表
    // 用户定义好表 然后创建


    public static void main(String[] args) {
        // createDatabase("v2ph.db");

    }

}
