package com.example.smart.server;

import com.example.smart.config.HttpSessionConfigurator;
import com.example.smart.entity.MessageEntity;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brandon.
 * @date 2019/5/20.
 * @time 15:12.
 */

@Slf4j
@Component
@ServerEndpoint(value = "/chat", configurator = HttpSessionConfigurator.class)
public class WebSocketServer {
    private static Map<String,Session> onlineCount = new ConcurrentHashMap<>();

    //存储在线人数名字
    private static List<String> list = new ArrayList<>();

    private String username;

    @OnOpen
    public void onOpen(Session session,EndpointConfig config){
        //onlineCount.put(session.getId(),session);
         HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.username = (String)httpSession.getAttribute("username");
        log.info("用户名：" + this.username);
        onlineCount.put(this.username,session);
        list.add(this.username);
        sendAllMessage(MessageEntity.toJson("Enter","","someone enter the chat room",onlineCount.size(),list));
    }


    @OnMessage
    public void onMessage(Session session, String messageJson){
        MessageEntity messageEntity = MessageEntity.fromJson(messageJson);
        if("Single".equals(messageEntity.getType())){
            log.info("私聊的两个人：" + messageEntity.getList().get(0) + " " + messageEntity.getList().get(1));
            sendToUserMessage(MessageEntity.toJson(messageEntity),messageEntity.getList());
        }else if("Picture".equals(messageEntity.getType())){
            messageEntity.setIsPicture(1);
            sendAllMessage(MessageEntity.toJson(messageEntity));
        }else{
            sendAllMessage(MessageEntity.toJson(messageEntity));
        }
    }

    @OnClose
    public void onClose(Session session){
        onlineCount.remove(this.username);
        list.remove(this.username);
        sendAllMessage(MessageEntity.toJson("Out","","someone out",onlineCount.size(),list));
    }

    @OnError
    public void onError(Session session,Throwable e){
        log.error("find some error: " + e.getMessage());
    }
    private void sendAllMessage(String message){
        onlineCount.forEach((username, session)->{
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.info("send the " + message + " happened some errors");
            }
        });
    }

    private void sendToUserMessage(String message,List<String> list){
        for (String name : list) {
            Session session = onlineCount.get(name);
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("Sending to user message finds a error that " + e.getMessage());
            }
        }
    }
}
