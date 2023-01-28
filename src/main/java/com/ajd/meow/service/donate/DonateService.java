package com.ajd.meow.service.donate;

import com.ajd.meow.entity.*;
import com.ajd.meow.repository.donate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonateService implements DonateServiceImpl {

    @Autowired
    private DonateRepository donateRepository;
    @Autowired
    private DonateWayRepository donateWayRepository;
    @Autowired
    private BankTransferRepository bankTransferRepository;
    @Autowired
    private CreditcardRepository creditcardRepository;
    @Autowired
    private AccountRepository accountRepository;


    //후원하기
    public void createDonate(DonateMaster donateMaster){
        donateRepository.save(donateMaster);
    }

    public void bankTransferDonate(BankTransfer bankTransfer){
        bankTransferRepository.save(bankTransfer);
    }

    public void creditcardDonate(CreditcardPayment creditcardPayment){
        creditcardRepository.save(creditcardPayment);
    }

    public void accountDonate(AccountTransfer accountTransfer){
        accountRepository.save(accountTransfer);
    }

    //후원확정
    public DonateMaster confirmDonate(Long donateCode){
        return donateRepository.findById(donateCode).get();
    }

    //후원취소
    public DonateMaster cancelDonate(Long donateCode){
        return donateRepository.findById(donateCode).get();
    }

    //MyPage 후원내역 보기
    public List<DonateMaster> donateMyView(Long UserNo){ return donateRepository.findByUserNo(UserNo); }

    //관리자가 후원내역 보기
    public List<DonateMaster> donateList(){
        return donateRepository.findAll();
    }

}
