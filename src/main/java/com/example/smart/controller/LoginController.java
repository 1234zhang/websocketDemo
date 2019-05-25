package com.example.smart.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brandon.
 * @date 2019/5/10.
 * @time 22:17.
 */
@Slf4j
@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login(){
       log.info("用户登录界面");
       return "login";
    }


    @RequestMapping("/index")
    public String index(String username, HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("username",username);
        HttpSession session = request.getSession();
        session.setAttribute("username",username);
        return "chat";
    }


    @RequestMapping("/private")
    public String privateChat(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if(session != null){
            request.setAttribute("username",(String)session.getAttribute("username"));
        }
        return "private_chat";
    }
    @RequestMapping("/group")
    public String group( HttpServletRequest request, HttpServletResponse response){
        String username = (String)request.getSession().getAttribute("username");
        request.setAttribute("groupName",request.getParameter("groupname"));
        request.setAttribute("username",username);
        log.info("用户 " + username + " 创建聊天室");
        return "/group_room";
    }
}
