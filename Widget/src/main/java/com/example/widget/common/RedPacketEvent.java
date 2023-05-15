package com.example.widget.common;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/4/29 9:10
 * @description
 * @updateUser hahajing
 * @updateDate 2022/4/29 9:10
 * @updateRemark
 */
public class RedPacketEvent {
    private RedPacketEnum state;

    public RedPacketEvent(){}

    public RedPacketEvent(RedPacketEnum state) {
        this.state = state;
    }

    public RedPacketEnum getState() {
        return state;
    }

    public void setState(RedPacketEnum state) {
        this.state = state;
    }
}
