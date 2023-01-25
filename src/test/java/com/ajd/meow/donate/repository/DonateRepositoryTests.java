package com.ajd.meow.donate.repository;


import com.ajd.meow.MeowApplicationTests;
import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.repository.donate.DonateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.UUID;

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
        DonateMaster donateMaster = new DonateMaster();

        donateMaster.getUserNo();
    }
}
