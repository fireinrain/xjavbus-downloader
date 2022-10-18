package com.fireinrain.xjavbus.handler;

import com.fireinrain.xjavbus.event.StarInfoEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @author : fireinrain
 * @description:
 * @site : https://github.com/fireinrain
 * @file : StarInfoEntityHandler
 * @software: IntelliJ IDEA
 * @time : 2022/10/18 11:42 PM
 */

public class StarInfoEntityHandler implements EventHandler<StarInfoEvent>, WorkHandler<StarInfoEvent> {
    @Override
    public void onEvent(StarInfoEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("starInfoEntityEvent=" + event + "  sequence=" + sequence + "  endOfBatch=" + endOfBatch);

    }


    @Override
    public void onEvent(StarInfoEvent event) throws Exception {
        System.out.println(event);
    }
}
