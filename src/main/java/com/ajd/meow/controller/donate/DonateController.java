package com.ajd.meow.controller.donate;

import com.ajd.meow.entity.AccountTransfer;
import com.ajd.meow.entity.BankTransfer;
import com.ajd.meow.entity.CreditcardPayment;
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

    @GetMapping("/donate.meow")
    public String donatehome(){
        return "sponsor_main";
    }

    @GetMapping("/donatecreate.meow")
    public String donateCreateForm(DonateMaster donateMaster){
        return "sponsor";
    }

//    @RequestMapping("/donate/success/{donateName}.meow")
//    public String donateSuccessPage(@PathVariable String donateName, DonateMaster donateMaster, Model model){
//
//        model.addAttribute("msg", "donateName : " + donateName);
//
//        return "spon_success";
//    }

    @GetMapping("/donatesuccess.meow")
    public String donateSuccess(){
        return "spon_success";
    }

    @PostMapping("/donatecreatedo.meow")
    public String donate(DonateMaster donateMaster, BankTransfer bankTransfer, CreditcardPayment creditcardPayment, AccountTransfer accountTransfer, Model model){
        //신용카드 한도초과, 계좌이체 잔액부족일 경우는 결제 API를 구현하지 않을 예정이기 때문에 주석으로 코드만 작성
        donateMaster.setDonateDate(Date.valueOf(LocalDate.now()));
        donateMaster.setDonateReceiptState("N");

        donateservice.createDonate(donateMaster);
        switch(donateMaster.getDonateWayCode()) {
            case "BANK":
                donateservice.updateDonateBankStateCode(donateMaster);
                donateservice.createDonate(donateMaster);

                bankTransfer.setDonateCode(donateMaster.getDonateCode());
                bankTransfer.setUserNo(donateMaster.getUserNo());
                donateservice.bankTransferDonate(bankTransfer);
                break;

            case "CRCRD":
                donateservice.updateDonateCreditStateCode(donateMaster);
//                if(한도초과일 시){
//                donateMaster.setDonateStateCode("LIMIT_EXCDD");
//                donateMaster.setDonateStateCode("DONATE_CPL");
                donateservice.createDonate(donateMaster);

                creditcardPayment.setDonateCode(donateMaster.getDonateCode());
                creditcardPayment.setUserNo(donateMaster.getUserNo());
                donateservice.creditcardDonate(creditcardPayment);

                break;

            case "ACNT":
                donateservice.updateDonateAccountStateCode(donateMaster);
//                if(잔액부족일 시){
//                donateMaster.setDonateStateCode("LCK_BLC");
//                donateMaster.setDonateStateCode("DONATE_CPL");
                donateservice.createDonate(donateMaster);

                accountTransfer.setDonateCode(donateMaster.getDonateCode());
                accountTransfer.setUserNo(donateMaster.getUserNo());
                donateservice.accountDonate(accountTransfer);

                break;
            }

            model.addAttribute("donate", donateMaster);
            return "spon_success";
    }

    @GetMapping("/donatelist.meow")
    public String donatelistForm(Model model){
        model.addAttribute("list", donateservice.donateList());
        return "spon_list";
    }

    @GetMapping("/donatecure_001.meow")
    public String donatecureForm(DonateMaster donateMaster){
        donateMaster.setDonateBusinessCode("CURE_001");
        donateMaster.setUserNo(1L);
        return "sponsor";
    }
}