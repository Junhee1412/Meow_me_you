package com.ajd.meow.controller.home;

import com.ajd.meow.entity.UserMaster;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
                return "admin/admin_index";// 어드민 페이지로 이동
            }else{
                return "user/index_login";
            }
        }
    }

    @GetMapping("/business")
    public String business(){
        return "business/business_info";
    }
}
