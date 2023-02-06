package com.ajd.meow.controller.donate;

import com.ajd.meow.entity.*;
import com.ajd.meow.repository.donate.AccountRepository;
import com.ajd.meow.repository.donate.BankTransferRepository;
import com.ajd.meow.repository.donate.CreditcardRepository;
import com.ajd.meow.repository.donate.DonateRepository;
import com.ajd.meow.service.donate.DonateService;
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

    @GetMapping("/donate.meow")
    public String donatehome(){
        return "sponsor_main";
    }

    @GetMapping("/donatesuccess.meow")
    public String donateSuccess(HttpSession session, Model model){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);

        return "spon_success";
    }

    @GetMapping("/donatecreate.meow")
    public String donateCreateForm(HttpSession session, DonateMaster donateMaster, Model model){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);
        return "sponsor";
    }

    @PostMapping("/donatecreatedo.meow")
    public String donate(HttpServletRequest request, HttpSession session, DonateMaster donateMaster,
                         BankTransfer bankTransfer, CreditcardPayment creditcardPayment, AccountTransfer accountTransfer,
                         RedirectAttributes redirectAttributes, Model model){

        //신용카드 한도초과, 계좌이체 잔액부족일 경우는 결제 API를 구현하지 않을 예정이기 때문에 주석으로 코드만 작성
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
            redirectAttributes.addAttribute("donatename", donateMaster.getDonateName());
            return "redirect:donatesuccess.meow";
    }

    @GetMapping("/donatelist.meow")
    public String donatelistForm(@PageableDefault(page = 0,size = 10, sort = "donateCode", direction = Sort.Direction.DESC) Pageable pageable,
                                 HttpSession session, Model model){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
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

        return "spon_list";
    }

    @GetMapping("/donatedelete.meow")
    public String donateDelete(Long donateCode){
        donateservice.deleteDonate(donateCode);
        return "redirect:/donatelist.meow";
    }

    @GetMapping("/donateconfirm.meow")
    public String donateConfirm(Long donateCode) {
        donateservice.confirmDonate(donateCode);
        return "redirect:/donatelist.meow";
    }


    @GetMapping("/donateReceipt/{id}.meow")
    public String donateReceipt(@PathVariable("id") Long donateCode, HttpSession session, Model model) {
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);


        model.addAttribute("donateReceipt", donateservice.donateReceipt(donateCode));

        return "donateReceipt";
    }

    @GetMapping("/donateReceipt.meow")
    public String donateReceipt(HttpSession session, Model model, Long id){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);

        model.addAttribute("donateReceipt", donateservice.donateReceipt(id));
        return "spon_receipt";
    }

}