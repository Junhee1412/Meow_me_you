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
public class DonateServiceImpl implements DonateService {

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
    //후원등록 시 후원상태
    //신용카드 한도초과, 계좌이체 잔액부족일 경우는 결제 API를 구현하지 않을 예정이기 때문에 주석으로 코드만 작성
    public void updateDonateBankStateCode(DonateMaster donateMaster){
        donateMaster.setDonateStateCode("BANK_WAIT");
    }
    public void updateDonateCreditStateCode(DonateMaster donateMaster){
        //if(한도초과일 시){
        //donateMaster.setDonateStateCode("LIMIT_EXCDD");
        donateMaster.setDonateStateCode("DONATE_CPL");
    }
    public void updateDonateAccountStateCode(DonateMaster donateMaster){
        //if(잔액부족일 시){
        //donateMaster.setDonateStateCode("LCK_BLC");
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

    // 후원 페이징
    public Page<DonateMaster> donateList(Pageable pageable){
        return donateRepository.findAll(pageable);
    }

    //후원페이징
    public Page<DonateMaster> donateMyList(Pageable pageable, Long userNo){
        return donateRepository.findAllByUserNo(pageable, userNo);
    }

    //전체 후원목록 보기
    public List<DonateMaster> donateList(){
        return donateRepository.findAll();
    }

    // 특정 후원 기부금영수증(상세보기)
    public DonateMaster donateReceipt(Long donateCode){
        return donateRepository.findById(donateCode).get();
    }
}
