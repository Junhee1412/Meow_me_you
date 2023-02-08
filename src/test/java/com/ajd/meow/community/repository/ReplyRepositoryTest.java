package com.ajd.meow.community.repository;

import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.repository.community.CommunityMasterRepository;
import com.ajd.meow.repository.community.ReplyRepository;
import com.ajd.meow.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    CommunityMasterRepository communityMasterRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void read() {
        Optional<CommunityMaster> communityMaster = communityMasterRepository.findById(1l);

        communityMaster.ifPresent(selectCommunity -> {

            System.out.println("Community : " + selectCommunity.getUserMaster().getNickName() + "입니다");
        });
    }


    }


