package com.ajd.meow.service.donate;

import com.ajd.meow.entity.BankTransfer;
import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.entity.DonateStateClass;
import com.ajd.meow.entity.DonateWayClass;
import com.ajd.meow.repository.donate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonateService {

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
    public void createDonate(DonateMaster donateMaster, BankTransfer bankTransfer){
        donateRepository.save(donateMaster);

        bankTransferRepository.save(bankTransfer);
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
