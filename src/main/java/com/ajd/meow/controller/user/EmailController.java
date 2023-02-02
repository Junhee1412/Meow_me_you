package com.ajd.meow.controller.user;

import com.ajd.meow.service.user.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private final EmailService emailService;

    String confirm="";
    String email="";

    @Autowired
    public EmailController(EmailService emailService){this.emailService=emailService;}

    @PostMapping("user/emailConfirm")
    public void emailConfirm(@RequestParam String userId) throws Exception {
        confirm = emailService.sendSimpleMessage(userId);
        email=userId;
        //System.out.println(confirm);
        //return "redirect:/";
    }
    @PostMapping("user/checkCode")
    public void checkCode(@RequestParam String checkCode, Model model){
        if(confirm.equals(checkCode)){
            model.addAttribute("successemail", email);
        }else {
            model.addAttribute("nomatchcode","코드가 일치하지않습니다.");
        }
    }
}
