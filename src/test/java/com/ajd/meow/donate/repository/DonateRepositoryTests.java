package com.ajd.meow.donate.repository;


import com.ajd.meow.MeowApplicationTests;
import com.ajd.meow.entity.AccountTransfer;
import com.ajd.meow.entity.BankTransfer;
import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.repository.donate.AccountRepository;
import com.ajd.meow.repository.donate.BankTransferRepository;
import com.ajd.meow.repository.donate.CreditcardRepository;
import com.ajd.meow.repository.donate.DonateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class DonateRepositoryTests extends MeowApplicationTests {
    @Autowired
    private DonateRepository donateRepository;

    @Autowired
    private BankTransferRepository bankTransferRepository;

    @Autowired
    private CreditcardRepository creditcardRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void create(){
        DonateMaster donatemaster = new DonateMaster();

        donatemaster.setDonateBusinessCode("CURE_001");
        donatemaster.setUserNo(1L);
        donatemaster.setDonateName("미유미유");
        donatemaster.setDonateAmount(4000);
        donatemaster.setDonateType("정기후원");
        donatemaster.setPhoneType("SKT");
        donatemaster.setPhoneNumber("010-0202-0303");
        donatemaster.setBirthDate(Date.valueOf("2014-02-02"));
        donatemaster.setGender("남자");
        donatemaster.setDonateWayCode("ACNT");
        donatemaster.setDonateReceiptState("N");
        donatemaster.setDonateStateCode("BANK_WAIT");
        donatemaster.setDonateDate(Date.valueOf("2014-03-01"));

        DonateMaster TestDonate = donateRepository.save(donatemaster);

        BankTransfer bankTransfer = new BankTransfer();
    }

    @Test
    public void Bankcreate(){
        BankTransfer bankTransfer = new BankTransfer();

        bankTransfer.setDonateCode(5L);
        bankTransfer.setUserNo(1L);
        bankTransfer.setBank("국민은행");

        BankTransfer newbankTransfer= bankTransferRepository.save(bankTransfer);
    }

    @Test
    public void Accountcreate(){
        DonateMaster donateMaster = new DonateMaster();
        AccountTransfer accountTransfer = new AccountTransfer();

        donateMaster.setDonateBusinessCode("CURE_001");
        donateMaster.setUserNo(1L);
        donateMaster.setDonateName("미유미유");
        donateMaster.setDonateAmount(6000);
        donateMaster.setDonateType("정기후원");
        donateMaster.setPhoneType("SKT");
        donateMaster.setPhoneNumber("010-0202-0303");
        donateMaster.setBirthDate(Date.valueOf("2014-02-02"));
        donateMaster.setGender("남자");
        donateMaster.setDonateWayCode("ACNT");
        donateMaster.setDonateReceiptState("N");
        donateMaster.setDonateStateCode("BANK_WAIT");
        donateMaster.setDonateDate(Date.valueOf("2014-03-01"));

        DonateMaster TestDonate = donateRepository.save(donateMaster);

        accountTransfer.setDonateCode(donateMaster.getDonateCode());
        accountTransfer.setAccountPassword(String.valueOf(44));
        accountTransfer.setUserNo(donateMaster.getUserNo());
        accountTransfer.setAccountBank("신한");

        AccountTransfer accountTransfer1=accountRepository.save(accountTransfer);
    }

    @Test
    public void read(){
        Optional<DonateMaster> donateMaster = donateRepository.findById(2L);

        donateMaster.ifPresent(selectDonate ->{
            System.out.println("CodeName은 " + selectDonate.getDonateName() + "입니다.");
            System.out.println("donateCode는 " + selectDonate.getDonateCode() + "입니다.");
            System.out.println("후원금액은 " + selectDonate.getDonateAmount()+ "입니다.");
        });
    }

    @Test
    public void findbyUserNoReadfromDonate(){
        List<DonateMaster> donateMaster = donateRepository.findByUserNo(1L);
        System.out.println(donateMaster.get(1));
    }

    @Test
    public void update(){
        Optional<DonateMaster> donateMaster = donateRepository.findById(3L);

        donateMaster.ifPresent(selectDonate->{
            selectDonate.setDonateAmount(15000);
            selectDonate.setDonateName("김현연");
            selectDonate.setDonateStateCode("DONATE_CNFRM");
            donateRepository.save(selectDonate);
        });
    }

    @Test
    public void delete(){
        Optional<DonateMaster> donateMaster = donateRepository.findById(4L);

        donateMaster.ifPresent(deleteDonate->{
            donateRepository.delete(deleteDonate);
        });
    }
}
