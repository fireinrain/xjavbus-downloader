package com.fireinrain.xjavbus;

import com.fireinrain.xjavbus.event.StarInfoEvent;
import com.fireinrain.xjavbus.event.StarInfoEventFactory;
import com.fireinrain.xjavbus.handler.StarInfoEntityHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @Description: 采集javbus数据
 * @Author : fireinrain
 * @Site : https://github.com/fireinrain
 * @File : ${NAME}
 * @Software: ${PRODUCT_NAME}
 * @Time : ${DATE} ${TIME}
 */

public class MainApp {
    public static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        logger.info("----------------------xjavbus-downloader starting----------------------");
        // 多生产者 单一消费者(也可以指定多个)
        Disruptor<StarInfoEvent> disruptor = new Disruptor<>(new StarInfoEventFactory(), 1024 * 1024, Executors.defaultThreadFactory(),
                // 这里的枚举修改为多生产者
                ProducerType.MULTI, new YieldingWaitStrategy());


        // 数据消费者生产者
        StarInfoEntityHandler starInfoEntityHandler = new StarInfoEntityHandler();
        disruptor.handleEventsWith(starInfoEntityHandler);

        RingBuffer<StarInfoEvent> ringBuffer = disruptor.getRingBuffer();
        // 开启所有消费者监听数据
        disruptor.start();

        IntStream.of(10).mapToObj(e -> new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                final int index = i;
                ringBuffer.publishEvent((event, sequence) -> {
                    event.getStarInfoEntity().setStarName(UUID.randomUUID().toString());
                    event.getStarInfoEntity().setAge(e + "--" + index);
                });
            }
        })).forEach(Thread::start);


    }
}