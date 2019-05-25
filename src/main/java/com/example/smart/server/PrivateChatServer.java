package com.example.smart.server;

import com.example.smart.config.HttpSessionConfigurator;
import com.example.smart.entity.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brandon.
 * @date 2019/5/22.
 * @time 19:38.
 */

@Slf4j
@Component
@ServerEndpoint(value = "/private_chat",configurator = HttpSessionConfigurator.class)
public class PrivateChatServer {
    private static Map<String,Session> chatMember = new ConcurrentHashMap<>();

    private String username;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.username = (String)httpSession.getAttribute("username");
        chatMember.put(this.username,session);
        sendMessage(MessageEntity.toJson("Enter",this.username,"hello world",chatMember.size()));
    }

    @OnMessage
    public void onMessage(Session session,String messageJson){
        log.info(messageJson);
        MessageEntity messageEntity = MessageEntity.fromJson(messageJson);
        sendMessage(MessageEntity.toJson(messageEntity));
    }

    @OnClose
    public void onClose(Session session){
        chatMember.remove(this.username);
        sendMessage(MessageEntity.toJson("Leave",this.username,"I leave",chatMember.size()));
    }

    @OnError
    public void onError(Session session,Throwable e){
        log.error("there happens some error that " + e.getMessage());
    }

    public void sendMessage(String message){
        chatMember.forEach((username,session)->{
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
