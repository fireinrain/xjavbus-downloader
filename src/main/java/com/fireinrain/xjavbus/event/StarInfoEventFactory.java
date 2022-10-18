package com.fireinrain.xjavbus.event;

import com.fireinrain.xjavbus.entity.StarInfoEntity;
import com.lmax.disruptor.EventFactory;

/**
 * @author : fireinrain
 * @description: StarInfoEventFactory
 * @site : https://github.com/fireinrain
 * @file : StarInfoEntityEventFactory
 * @software: IntelliJ IDEA
 * @time : 2022/10/18 11:59 PM
 */

public class StarInfoEventFactory implements EventFactory<StarInfoEvent> {
    @Override
    public StarInfoEvent newInstance() {
        return new StarInfoEvent(new StarInfoEntity());
    }
}
