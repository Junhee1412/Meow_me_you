package com.ajd.meow.controller.user;

import com.ajd.meow.service.user.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private final EmailService emailService;
    String confirm="";String abc="";

    @Autowired
    public EmailController(EmailService emailService){this.emailService=emailService;}

    @PostMapping("/emailConfirm")
    public void emailConfirm(@RequestParam String email) throws Exception {

        confirm = emailService.sendSimpleMessage(email);
        //System.out.println(confirm);
        //return "redirect:/";
    }
    @PostMapping("/checkCode")
    public void checkCode(@RequestParam String code){
        //return "check_mail";
        //System.out.println("code : "+code);System.out.println("confirm : "+confirm);
        if(confirm.equals(code)){
            abc="코드 인증 성공!";
        }else {abc="인증 실패";}
        System.out.println(abc);
    }
}
