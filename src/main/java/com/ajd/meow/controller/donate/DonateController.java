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

import java.sql.Date;
import java.time.LocalDate;

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

    @RequestMapping("/donate/success/{donateName}.meow")
    public String donateSuccessPage(@PathVariable String donateName, DonateMaster donateMaster, Model model){

        model.addAttribute("msg", "donateName : " + donateName);

        return "spon_success";
    }

    @PostMapping("/donate/createdo.meow")
    public String donate(DonateMaster donateMaster, BankTransfer bankTransfer, Model model){
        donateMaster.setDonateDate(Date.valueOf(LocalDate.now()));
        donateMaster.setDonateReceiptState("N");
        donateMaster.setDonateStateCode("BANK_WAIT");

        donateservice.createDonate(donateMaster);

        bankTransfer.setDonateCode(donateMaster.getDonateCode());
        bankTransfer.setUserNo(donateMaster.getUserNo());

        donateservice.bankTransferDonate(bankTransfer);

        model.addAttribute("searchUrl", "/donate/list.meow");

        return "redirect:/donate/create.meow";

    }
//    @PostMapping("/donate/createdo.meow")
//    public String donate(DonateMaster donateMaster, BankTransfer bankTransfer, Model model){
//
//        donateMaster.setDonateDate(Date.valueOf("2014-02-02"));
//        donateMaster.setDonateReceiptState("N");
//
//        switch(donateMaster.getDonateWayCode()){
//            case "bank":
//
//                donateMaster.setDonateStateCode("BANK_WAIT");
//                donateservice.createDonate(donateMaster);
//
//                bankTransfer.setDonateCode(donateMaster.getDonateCode());
//                bankTransfer.setUserNo(donateMaster.getUserNo());
//                donateservice.bankTransferDonate(bankTransfer);
//
//                model.addAttribute("searchUrl", "/donate/list.meow");
//                break;
//        }
//        return "sponsor";
//    }

    @GetMapping("/donate/list.meow")
    public String donatelistForm(Model model){
        model.addAttribute("list", donateservice.donateList());
        return "spon_list";
    }


//            case "CRCRD":
////                if(한도초과일 시){
////                donateMaster.setDonateStateCode("LIMIT_EXCDD");
////                donateMaster.setDonateReceiptState("N");
////                break;}
//                    donateMaster.setDonateStateCode("DONATE_CPL");
//                donateservice.createDonate(donateMaster);
//
//                creditcardPayment.setDonateCode(donateMaster.getDonateCode());
//                creditcardPayment.setUserNo(donateMaster.getUserNo());
//                donateservice.creditcardDonate(creditcardPayment);
//
//                model.addAttribute("searchUrl", "/donate/list.meow");
//                break;



}
