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
 * @date 2019/5/23.
 * @time 21:27.
 */

@Slf4j
@Component
@ServerEndpoint(value = "/group_room/{groupname}",configurator = HttpSessionConfigurator.class)
public class GroupChatServer {
    private static Map<String, Session> chatMember = new ConcurrentHashMap<>();

    private HttpSession httpSession;

    private String username;

    @OnOpen
    public void onopen(Session session,EndpointConfig endpointConfig){
        this.httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
        this.username = (String)httpSession.getAttribute("username");
        chatMember.put(this.username,session);
        sendMessage(MessageEntity.toJson("Enter",this.username,"hello world",chatMember.size()));
    }

    @OnMessage
    public void onmessage(Session session,String messageJson){
        log.info(messageJson);
        MessageEntity messageEntity = MessageEntity.fromJson(messageJson);
        sendMessage(MessageEntity.toJson(messageEntity));
    }

    @OnClose
    public void onclose(Session session){
        chatMember.remove(this.username);
        sendMessage(MessageEntity.toJson("Leave",this.username,"I leave",chatMember.size()));
    }

    @OnError
    public void onerror(Session session,Throwable e){
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
