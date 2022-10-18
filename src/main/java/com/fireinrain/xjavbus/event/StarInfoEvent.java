package com.fireinrain.xjavbus.event;

import com.fireinrain.xjavbus.entity.StarInfoEntity;

/**
 * @author : fireinrain
 * @description: 演员信息事件
 * @site : https://github.com/fireinrain
 * @file : StarInfoEntityEvent
 * @software: IntelliJ IDEA
 * @time : 2022/10/18 11:45 PM
 */

public class StarInfoEvent {
    private StarInfoEntity starInfoEntity;

    public StarInfoEntity getStarInfoEntity() {
        return starInfoEntity;
    }

    public void setStarInfoEntity(StarInfoEntity starInfoEntity) {
        this.starInfoEntity = starInfoEntity;
    }

    public StarInfoEvent(StarInfoEntity starInfoEntity) {
        this.starInfoEntity = starInfoEntity;
    }

    @Override
    public String toString() {
        return "StarInfoEvent{" +
                "starInfoEntity=" + starInfoEntity +
                '}';
    }
}
