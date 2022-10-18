package com.fireinrain.xjavbus.handler;

import com.fireinrain.xjavbus.event.StarInfoEvent;
import com.lmax.disruptor.EventHandler;

/**
 * @author : fireinrain
 * @description:
 * @site : https://github.com/fireinrain
 * @file : StarInfoEntityHandler
 * @software: IntelliJ IDEA
 * @time : 2022/10/18 11:42 PM
 */

public class StarInfoEntityHandler implements EventHandler<StarInfoEvent> {
    @Override
    public void onEvent(StarInfoEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("starInfoEntityEvent=" + event + "  sequence=" + sequence + "  endOfBatch=" + endOfBatch);

    }
}
