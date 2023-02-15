package com.ajd.meow.controller.donate;

import com.ajd.meow.entity.*;
import com.ajd.meow.repository.donate.AccountRepository;
import com.ajd.meow.repository.donate.BankTransferRepository;
import com.ajd.meow.repository.donate.CreditcardRepository;
import com.ajd.meow.repository.donate.DonateRepository;
import com.ajd.meow.service.donate.DonateService;
import com.ajd.meow.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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

    @Autowired
    private UserService userService;

    @GetMapping("/donatehome")
    public String donatehome(){
        return "donate/donate_home";
    }

    @GetMapping("/donatesuccess")
    public String donateSuccess(HttpSession session, Model model){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);

        return "donate/donate_success";
    }

    @GetMapping("/donatecreate")
    public String donateCreateForm(HttpSession session, DonateMaster donateMaster, Model model){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);
        return "donate/donate_insert";
    }

    @PostMapping("/donatecreatedo")
    public String donate(HttpServletRequest request, HttpSession session, DonateMaster donateMaster,
                         BankTransfer bankTransfer, CreditcardPayment creditcardPayment, AccountTransfer accountTransfer,
                         RedirectAttributes redirectAttributes, Model model){

        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);

        String birth = request.getParameter("birthdate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;

        try {
            date = (Date) dateFormat.parse(birth);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Date utilDate = date;

        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        donateMaster.setBirthDate(sqlDate);
        donateMaster.setDonateDate(java.sql.Date.valueOf(LocalDate.now()));

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
                donateservice.createDonate(donateMaster);
                creditcardPayment.setDonateCode(donateMaster.getDonateCode());
                creditcardPayment.setUserNo(donateMaster.getUserNo());
                donateservice.creditcardDonate(creditcardPayment);
                break;

            case "ACNT":
                donateservice.updateDonateAccountStateCode(donateMaster);
                donateservice.createDonate(donateMaster);
                accountTransfer.setDonateCode(donateMaster.getDonateCode());
                accountTransfer.setUserNo(donateMaster.getUserNo());
                donateservice.accountDonate(accountTransfer);
                break;
            }
            redirectAttributes.addAttribute("donatename", donateMaster.getDonateName());
            return "redirect:donatesuccess";
    }

    @GetMapping("/donatelist")
    public String donatelistForm(@PageableDefault(page = 0,size = 10, sort = "donateCode", direction = Sort.Direction.DESC)
                                     Pageable pageable, HttpSession session, Model model){

        UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
        model.addAttribute("user",loginUser);
        model.addAttribute("list", donateservice.donateList());

        Page<DonateMaster> lists = donateservice.donateList(pageable);
        int nowPage = lists.getPageable().getPageNumber()+1 ;
        int startPage = Math.max(0 , 1);

        int endPage = Math.min(nowPage + 10 , lists.getTotalPages());

        model.addAttribute("list", lists);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("maxPage",10);

        return "donate/my_donate_list";
    }

    @GetMapping("/donateconfirm")
    public String donateConfirm( HttpSession session, Model model, Long donateCode) {
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);

        if(loginUser.getUserType().equals("ADMIN")){
        donateservice.confirmDonate(donateCode);
        return "redirect:adminDonatemanage";
        } else{
        donateservice.confirmDonate(donateCode);
            return "redirect:donatelist";
        }
    }

    @GetMapping("/donatedelete")
    public String donateDelete(HttpSession session, Model model, Long donateCode){

        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);

        if(loginUser.getUserType().equals("ADMIN")){
            donateservice.deleteDonate(donateCode);
            return "redirect:adminDonatemanage";
        } else{
            donateservice.deleteDonate(donateCode);
            return "redirect:donatelist";
        }
    }

    @GetMapping("/donatereceipt/{id}")
    public String donateReceipt(@PathVariable("id") Long donateCode, HttpSession session, Model model) {
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);

        model.addAttribute("donateReceipt", donateservice.donateReceipt(donateCode));

        return "donatereceipt";
    }

    @GetMapping("/donatereceipt")
    public String donateReceipt(HttpSession session, Model model, Long id){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);

        model.addAttribute("donateReceipt", donateservice.donateReceipt(id));
        return "donate/donate_receipt";
    }

}