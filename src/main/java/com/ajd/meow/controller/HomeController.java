package com.ajd.meow.controller;

import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/")
    public String home(HttpSession session){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            Optional<UserMaster> loginUser=userRepository.findByUserId(((UserMaster)session.getAttribute("user")).getUserId());
            if(loginUser.get().getUserType().equals("ADMIN")){
                return "index_admin";// 어드민 페이지로 이동
            }else{return "index_login";}

            /*String userType=userRepository.findByUserId(((UserMaster)session.getAttribute("user")).getUserId()).get().getUserType();
            if(userType.equals("ADMIN")){
                return "index_admin";
            }else{return "index_login";}*/
        }
    }
}
