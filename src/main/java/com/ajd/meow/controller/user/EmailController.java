package com.ajd.meow.controller.user;

import com.ajd.meow.service.user.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {
    @Autowired
    private final EmailService emailService;

    String confirm="";
    String email="";

    @Autowired
    public EmailController(EmailService emailService){this.emailService=emailService;}

    @GetMapping("emailConfirm/{useremail}")
    @ResponseBody
    public String emailConfirm(@PathVariable String useremail) throws Exception {
        confirm = emailService.sendSimpleMessage(useremail);
        email=useremail;
        return confirm;
    }
    @PostMapping("checkCode")
    public void checkCode(@RequestParam String checkCode, Model model){
        if(confirm.equals(checkCode)){
            model.addAttribute("successemail", email);
        }else {
            model.addAttribute("nomatchcode","코드가 일치하지않습니다.");
        }
    }
}
