package com.fireinrain.xjavbus.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author : fireinrain
 * @description: 爬取所有的演员信息
 * @site : https://github.com/fireinrain
 * @file : StarInfoSpider
 * @software: IntelliJ IDEA
 * @time : 2022/10/18 11:14 PM
 */

public class StarInfoSpider extends AbstractHttpSpider implements Runnable {
    public static final Logger logger = LoggerFactory.getLogger(StarInfoSpider.class);

    private static final List<String> starInfoUrls = Arrays.asList("");


    @Override
    public void run() {

    }
}
