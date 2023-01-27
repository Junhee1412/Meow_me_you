package com.ajd.meow.donate.repository;


import com.ajd.meow.MeowApplicationTests;
import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.repository.donate.DonateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class DonateRepositoryTests extends MeowApplicationTests {
    @Autowired
    private DonateRepository donateRepository;

    @Test
    public void create(){
        DonateMaster donatemaster = new DonateMaster();

        donatemaster.setDonateBusinessCode("CURE_001");
        donatemaster.setUserNo(1L);
        donatemaster.setDonateName("미유미유");
        donatemaster.setDonateAmount(30000);
        donatemaster.setDonateType("정기후원");
        donatemaster.setDonateWayCode("ACNT");
        donatemaster.setDonateReceiptState("N");
        donatemaster.setDonateStateCode("BANK_WAIT");
        donatemaster.setDonateDate(Date.valueOf("2014-03-01"));

        DonateMaster TestDonate = donateRepository.save(donatemaster);
    }

    @Test
    public void read(){
        Optional<DonateMaster> donateMaster = donateRepository.findById(2L);

        donateMaster.ifPresent(selectDonate ->{
            System.out.println("CodeName은 " + selectDonate.getDonateName() + "입니다.");
            System.out.println("DonateCode는 " + selectDonate.getDonateCode() + "입니다.");
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
