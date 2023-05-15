package com.example.tool.websocket;

import com.example.tool.utils.LogUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/8/19 7:57
 * @description
 * @updateUser hahajing
 * @updateDate 2022/8/19 7:57
 * @updateRemark
 */
public class ZWebSocketClient extends WebSocketClient {

    public ZWebSocketClient(URI serverUri) {
        super(serverUri,new Draft_6455());
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        LogUtils.INSTANCE.i("123", "onOpen()");
    }

    @Override
    public void onMessage(String message) {
        LogUtils.INSTANCE.i("123", "onMessage()");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LogUtils.INSTANCE.i("123", String.format("onClose code=%s reason=%s remote=%s", code, reason, remote));
    }

    @Override
    public void onError(Exception ex) {
        LogUtils.INSTANCE.i("123", "onError()"+ex.getMessage());
    }
}
