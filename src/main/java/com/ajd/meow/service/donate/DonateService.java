package com.ajd.meow.service.donate;

import com.ajd.meow.entity.*;
import com.ajd.meow.repository.donate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void updateDonateBankStateCode(DonateMaster donateMaster){
        donateMaster.setDonateStateCode("BANK_WAIT");
    }

    public void updateDonateCreditStateCode(DonateMaster donateMaster){
        donateMaster.setDonateStateCode("DONATE_CPL");
    }

    public void updateDonateAccountStateCode(DonateMaster donateMaster){
        donateMaster.setDonateStateCode("DONATE_CPL");
    }


    //후원확정
    public void confirmDonate(Long donateCode){
        Optional<DonateMaster> donateMaster = donateRepository.findById(donateCode);
        donateMaster.get().setDonateStateCode("DONATE_CNFRM");
        donateRepository.save(donateMaster.get());
    }

    //후원취소
    public void deleteDonate(Long donateCode){

        String donateWayCode = donateRepository.findById(donateCode).get().getDonateWayCode();

        switch(donateWayCode) {
            case "BANK":
                bankTransferRepository.deleteById(donateCode);
                break;
            case "CRCRD":
                creditcardRepository.deleteById(donateCode);
                break;
            case "ACNT":
                accountRepository.deleteById(donateCode);
                break;
        }
        donateRepository.deleteById(donateCode);
    }
    // 게시글 리스트
    public Page<DonateMaster> donateList(Pageable pageable){
        return donateRepository.findAll(pageable);
    }
    //MyPage 후원내역 보기
    public List<DonateMaster> donateMyView(Long UserNo){ return donateRepository.findByUserNo(UserNo); }

    //관리자가 후원내역 보기
    public List<DonateMaster> donateList(){
        return donateRepository.findAll();
    }
}
