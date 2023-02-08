package com.ajd.meow.repository.community;

import com.ajd.meow.MeowApplicationTests;
import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.UserMaster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommunityMasterRepositoryTest extends MeowApplicationTests {

    @Autowired
    private CommunityMasterRepository communityMasterRepository;

    @Test
    public void create() {
        CommunityMaster communityMaster = new CommunityMaster();

        communityMaster.setUserNo(1L);
        communityMaster.setCommunityId("ADP_ACT");
        communityMaster.setPostId("FOOD_SELL");
        communityMaster.setSubject("제목입니다");
        communityMaster.setContent("내용입니다");
        communityMaster.setCreatePostDate(LocalDateTime.now());

        CommunityMaster newCommunity = communityMasterRepository.save(communityMaster);
        System.out.println(newCommunity);

    }

    @Test
    public void read() {
        Optional<CommunityMaster> communityMaster = communityMasterRepository.findById(11l);

        communityMaster.ifPresent(selectCommunity -> {

            System.out.println("Community : " + selectCommunity.getUserMaster().getNickName() + "입니다");
        });
    }

    @Test
    public void update() {
        Optional<CommunityMaster> communityMaster = communityMasterRepository.findById(3L);

        communityMaster.ifPresent((selectCommunity -> {
            selectCommunity.setUserNo(1L);
            selectCommunity.setCommunityId("ADP_ACT");
            selectCommunity.setPostId("FOOD_SELL");
            selectCommunity.setSubject("999999");
            selectCommunity.setContent("999999999");
            CommunityMaster newCommunity = communityMasterRepository.save(selectCommunity);
            System.out.println("Community : " + newCommunity);
        }));
    }

//    @Test
//    public  void delete(){
//        Optional<CommunityMaster> communityMaster = communityMasterRepository.findById(3l);
//
//        Assertions.assertTrue(communityMaster.isPresent());
//        communityMaster.ifPresent(selectCommunity ->{
//            communityMasterRepository.delete(selectCommunity);
//        });
//        Optional<CommunityMaster> deleteCommunity = communityMasterRepository.findById(1l);
//        Assertions.assertFalse(deleteCommunity.isPresent());
    }


