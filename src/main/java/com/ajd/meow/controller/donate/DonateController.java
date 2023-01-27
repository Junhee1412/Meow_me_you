package com.ajd.meow.controller.donate;

import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.repository.donate.DonateRepository;
import com.ajd.meow.service.donate.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class DonateController {

    @Autowired
    private DonateRepository donateRepository;

    @GetMapping("/donate/create.meow")
    public String donateCreateForm(){
        return "sponsor";
    }

    @GetMapping("/donate/read.meow")
    public String donateReadForm(){
        return "spon_list";
    }

    @PostMapping("/donate/createdo.meow")
    public String donate(@RequestBody DonateMaster donateMaster){

        DonateMaster newdonate = donateRepository.save(donateMaster);

        return "spon_success";
    }
}
