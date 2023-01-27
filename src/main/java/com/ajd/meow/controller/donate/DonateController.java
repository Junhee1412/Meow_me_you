package com.ajd.meow.controller.donate;

import com.ajd.meow.entity.BankTransfer;
import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.repository.donate.AccountRepository;
import com.ajd.meow.repository.donate.BankTransferRepository;
import com.ajd.meow.repository.donate.CreditcardRepository;
import com.ajd.meow.repository.donate.DonateRepository;
import com.ajd.meow.service.donate.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DonateController {
    @Autowired
    private DonateRepository donateRepository;

    @Autowired
    private BankTransferRepository bankTransferRepository;

    @Autowired
    private CreditcardRepository creditcardRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DonateService donateservice;


    @GetMapping("/donate/create.meow")
    public String donateCreateForm(){
        return "sponsor";
    }

    @PostMapping("/donate/createdo.meow")
    public String donate(DonateMaster donateMaster, BankTransfer bankTransfer, Model model){

        donateservice.createDonate(donateMaster, bankTransfer);

        model.addAttribute("searchUrl", "/donate/list.meow");

        return "spon_success";

    }

    @GetMapping("/donate/list.meow")
    public String donatelistForm(Model model){
        model.addAttribute("list", donateservice.donateList());
        return "spon_list";
    }

}
