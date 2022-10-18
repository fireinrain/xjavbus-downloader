package com.fireinrain.xjavbus.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @Description: 获取随机的user-agent
 * @Author : fireinrain
 * @Site : https://github.com/fireinrain
 * @File : UserAgent
 * @Software: IntelliJ IDEA
 * @Time : 2022/9/14 12:10 AM
 */

public class UserAgentConfig {

    private static List<String> userAgentList = null;

    static {
        InputStream resource = UserAgentConfig.class.getClassLoader().getResourceAsStream("user-agent.txt");
        assert resource != null;
        InputStreamReader fileReader = new InputStreamReader(resource);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> collect = bufferedReader.lines()
                .collect(Collectors.toList());
        userAgentList = collect;

    }

    public static String getRandom() {
        int size = userAgentList.size();
        int nextInt = ThreadLocalRandom.current().nextInt(size);

        return userAgentList.get(nextInt);
    }


    public static void main(String[] args) {
        System.out.println(getRandom());
    }
}
