package com.ajd.meow.service.donate;

import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.repository.donate.DonateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonateService {

    @Autowired
    private DonateRepository donateRepository;

    //후원하기
    public void createDonate(DonateMaster donateMaster){
        donateRepository.save(donateMaster);
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
    public List<DonateMaster> donateMyView(Long userNo){
        return donateRepository.findByUserNo(userNo);
    }

    //관리자가 후원내역 보기
    public List<DonateMaster> donateListView(){
        return donateRepository.findAll();
    }
}
