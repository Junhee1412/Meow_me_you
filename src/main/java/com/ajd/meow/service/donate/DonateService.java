package com.ajd.meow.service.donate;

import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.repository.donate.DonateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonateService {

    @Autowired
    private DonateRepository donateRepository;

    //후원하기(Create)
    public void createDonate(DonateMaster donateMaster){
        donateRepository.save(donateMaster);
    }
}
