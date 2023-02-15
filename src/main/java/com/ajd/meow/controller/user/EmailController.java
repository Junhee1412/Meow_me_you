package com.ajd.meow.controller.user;

import com.ajd.meow.service.user.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {
    @Autowired
    private final EmailService emailService;


    @Autowired
    public EmailController(EmailService emailService){this.emailService=emailService;}

    @GetMapping("emailConfirm/{useremail}")
    @ResponseBody
    public String emailConfirm(@PathVariable String useremail) throws Exception {
        String confirm = emailService.sendSimpleMessage(useremail);
        return confirm;
    }
}
