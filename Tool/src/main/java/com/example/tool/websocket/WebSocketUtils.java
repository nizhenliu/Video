package com.example.tool.websocket;

import android.os.Handler;
import android.text.TextUtils;

import com.example.tool.BuildConfig;

import com.example.tool.utils.LogUtils;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/8/19 7:53
 * @description
 * @updateUser hahajing
 * @updateDate 2022/8/19 7:53
 * @updateRemark
 */
public class WebSocketUtils {
    public ZWebSocketClient client;
    private String websocket_url= BuildConfig.WS_HOST;
    private ReceiveMsgListener listener;

    private WebSocketUtils(String _websocket_url,ReceiveMsgListener _listener) {
//        if(TextUtils.isEmpty(_websocket_url)) {
//            throw new IllegalArgumentException("Invalid websocket URL: " + _websocket_url);
//        }
        if(!TextUtils.isEmpty(_websocket_url)) {
            websocket_url = _websocket_url.trim();
        }
        listener = _listener;
        initWS();
    }

    /**
     * 设置接收到消息监听
     * @param listener
     */
    public void setListener(ReceiveMsgListener listener) {
        this.listener = listener;
    }

    /**
     * 初始化WebSocket
     */
    private void initWS() {
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
        if (client == null) {
            initWebSocket();
        } else if (!client.isOpen()) {
            reconnectWs();//进入页面发现断开开启重连
        }
    }

    /**
     * 关闭连接是否资源
     */
    public void destroy() {
        closeConnect();
    }

    /**
     * 初始化websocket
     */
    private void initWebSocket() {
        LogUtils.INSTANCE.e("123", "websocket的地址是：ws://" + websocket_url);
        URI uri = URI.create("ws://" + websocket_url);
        //TODO 创建websocket
        client = new ZWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                if (!message.equals("Heartbeat")){
                    LogUtils.INSTANCE.i("123", "websocket收到消息：" + message);
//                    sendNotification(message);
                    if(listener != null){
                        listener.onReceive(message);
                    }
                }
            }

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);
                LogUtils.INSTANCE.i("123", "websocket连接成功");
                if(listener != null){
                    listener.onOpen(handshakedata);
                }
            }

            @Override
            public void onError(Exception ex) {
                super.onError(ex);
                LogUtils.INSTANCE.i("123", "websocket连接错误：" + ex);
                if(listener != null){
                    listener.onError(ex);
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                super.onClose(code, reason, remote);
                if (code!=1000) {
                    reconnectWs();//意外断开马上重连
                }
                if(listener != null){
                    listener.onClose(code, reason, remote);
                }
                LogUtils.INSTANCE.i("123", "websocket断开连接：·code:" + code + "·reason:" + reason + "·remote:" + remote);
            }
        };
        //TODO 设置超时时间
        client.setConnectionLostTimeout(110 * 1000);
        //TODO 连接websocket
        new Thread() {
            @Override
            public void run() {
                try {
                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
                    client.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 发送消息
     *
     * @param msg
     */
    public void sendMsg(String msg) {
        if (null != client) {
            LogUtils.INSTANCE.e("123", String.format("Websocket发送的消息:%s",msg));
            if (client.isOpen()) {
                client.send(msg);
            }
        }
    }

    /**
     * 开启重连
     */
    private void reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    LogUtils.INSTANCE.w("123", "开启重连");
                    client.reconnectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 断开连接
     */
    private void closeConnect() {
        try {
            //关闭websocket
            if (null != client) {
                client.close();
            }
            //停止心跳
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
    }

    /**
     * WebSocket 心跳逻辑
     * 每隔10秒进行一次对长连接的心跳检测
     */
    private static final long HEART_BEAT_RATE = 10 * 1000;
    private Handler mHandler = new Handler();
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (client != null) {
                if (client.isClosed()) {
                    LogUtils.INSTANCE.w("123", "心跳包检测websocket连接状态1"+client.isOpen() + "/" + websocket_url);
                    reconnectWs();//心跳机制发现断开开启重连
                } else {
                    LogUtils.INSTANCE.w("123", "心跳包检测websocket连接状态2"+client.isOpen() + "/" + websocket_url);
                    sendMsg("Heartbeat");
                }
            } else {
                LogUtils.INSTANCE.w("123", "心跳包检测websocket连接状态重新连接");
                //如果client已为空，重新初始化连接
                client = null;
                initWebSocket();
            }
            //每隔一定的时间，对长连接进行一次心跳检测
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    /**
     * 建造者
     */
    public static class Builder{
        private String websocket_url;
        private ReceiveMsgListener listener;

        public Builder setWebsocket_url(String websocket_url) {
            this.websocket_url = websocket_url;
            return this;
        }

        public Builder setListener(ReceiveMsgListener listener) {
            this.listener = listener;
            return this;
        }

        public WebSocketUtils build() {
            return new WebSocketUtils(websocket_url,listener);
        }
    }

    /**
     * 接收到消息监听
     */
    public interface ReceiveMsgListener{
        void onReceive(String message);
        void onOpen(ServerHandshake handshakedata);
        void onError(Exception ex);
        void onClose(int code, String reason, boolean remote);
    }
}
