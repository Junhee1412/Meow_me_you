package com.ajd.meow.repository.community;

import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.Reply;
import com.ajd.meow.entity.UserMaster;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    CommunityMasterRepository communityMasterRepository;

    @Autowired
    UserMasterRepository userMasterRepository;

    @Test
    public void read() {
        Optional<CommunityMaster> communityMaster = communityMasterRepository.findById(1l);

        communityMaster.ifPresent(selectCommunity -> {

            System.out.println("Community : " + selectCommunity.getUserMaster().getNickName() + "입니다");
        });
    }


    }


