package com.ajd.meow.controller.donate;

import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.service.donate.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DonateController {

    @Autowired
    private DonateService donateService;

    @GetMapping("/donate/create")
    public String donateCreateForm(){
        return "sponsor";
    }

    @PostMapping("/donate/createdo")
    public String donate(DonateMaster donateMaster, Model model){
        donateService.createDonate(donateMaster);

        model.addAttribute("message", "님 후원해주셔서 감사합니다!");
        model.addAttribute("searchUrl", "/donate/list");
        return donateMaster.getDonateName()+ "message";
    }
}
