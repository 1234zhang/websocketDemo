package com.example.smart.entity;

import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.http.converter.json.GsonFactoryBean;

import java.util.List;
import java.util.Objects;

/**
 * @author Brandon.
 * @date 2019/5/20.
 * @time 15:30.
 */

@Data
@Slf4j
public class MessageEntity {

    static Gson gson = new Gson();

    private String type;

    private String username;

    private String msg;

    private int onlineNum;

    private List<String> list;

    private int isPicture = 0;

    public MessageEntity(String type,String username,String msg, int onlineNum,List<String> list){
        this.type = type;
        this.username = username;
        this.msg = msg;
        this.onlineNum = onlineNum;
        this.list = list;
    }
    public MessageEntity(String type,String username, String msg, int onlineNum){
        this.type = type;
        this.username = username;
        this.msg = msg;
        this.onlineNum = onlineNum;
    }

    public static String toJson(String type,String username, String msg,int onlineNum, List<String> list){
        return gson.toJson(new MessageEntity(type,username,msg,onlineNum,list));
    }

    public static String toJson(String type,String username, String msg,int onlineNum){
        return gson.toJson(new MessageEntity(type,username,msg,onlineNum));
    }

    public static String toJson(MessageEntity messageEntity){
        return gson.toJson(messageEntity);
    }


    public static MessageEntity fromJson(String messgeJson){
        MessageEntity message = gson.fromJson(messgeJson, MessageEntity.class);
        return message;
    }
}
