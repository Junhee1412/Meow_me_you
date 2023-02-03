package com.ajd.meow.controller.user;

import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

//@RequiredArgsConstructor
@Controller
@SessionAttributes("user")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login.meow")
    public String login(){
        return "join";
    }
    @PostMapping("/login.meow")
    public String login(UserMaster user, Model model){
        UserMaster findUser=userService.getUserMaster(user);
        if(findUser!=null && findUser.getUserPassword().equals(user.getUserPassword())){
            model.addAttribute("user",findUser);
            if(findUser.getUserType().equals("ADMIN")) {
                 return "index_admin"; // 어드민 페이지 따로 만드나유
            } else {
                return "index_login"; // 로그인 후 index 페이지
            }
        }else {
            model.addAttribute("errMsg", "로그인 실패! 다시 입력해주세요.");
            return "join";}
    }

    @GetMapping("/logout.meow")
    public String logout(SessionStatus status){
        status.setComplete();
        return "index"; // 로그아웃 후 인덱스
    }
}
