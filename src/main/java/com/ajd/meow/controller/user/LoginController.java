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

    @GetMapping("/loginpage")
    public String login(){
        return "user/login_page";
    }

    @PostMapping("/loginpage")
    public String login(UserMaster user, Model model){
        UserMaster findUser=userService.getUserMaster(user);
        if(findUser!=null && findUser.getUserPassword().equals(user.getUserPassword())){
            model.addAttribute("user",findUser);
            if(findUser.getUserType().equals("ADMIN")) {
                 return "redirect:/"; // 어드민 홈으로 이동
            } else {
                return "redirect:/"; // 일반유저 홈으로 이동
            }
        }else {
            model.addAttribute("errMsg", "로그인 실패! 다시 입력해주세요.");
            return "user/login_page";}
    }

    @GetMapping("/logout")
    public String logout(SessionStatus status){
        status.setComplete();
        return "redirect:/"; // 로그아웃 후 인덱스
    }
}
