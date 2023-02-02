package com.ajd.meow.controller;

import com.ajd.meow.entity.UserMaster;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(HttpSession session){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster loginUser= (UserMaster)session.getAttribute("user");
            if(loginUser.getUserType().equals("ADMIN")){
                return "index_admin";// 어드민 페이지로 이동
            }else{
                return "index_login";
            }
        }
    }
}
