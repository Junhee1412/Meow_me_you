package com.ajd.meow.service.community;


import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.CommunityMasterRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunityMasterService {

    @Autowired
    private CommunityMasterRepository communityMasterRepository;


    //글 작성
    public void write(CommunityMaster communityMaster){

        communityMaster.setCommunityId("ADP_ACT");
        communityMaster.setPostId("FOOD_SELL");
        communityMaster.setCreatePostDate(LocalDateTime.now());

        communityMasterRepository.save(communityMaster);
    }

    // 게시글 리스트
    public Page<CommunityMaster> boardList(Pageable pageable){
        return communityMasterRepository.findAll(pageable);
    }

    //특정 게시글 불러오기
    public CommunityMaster boardView(Long postNo){return communityMasterRepository.findById(postNo).get();
    }
    //게시글 삭제 하기
    public void boardDelete(Long postNo) {
        communityMasterRepository.deleteById(postNo);
    }

    //게시글 수정
    public void boardUpdate(CommunityMaster communityMaster){

        communityMaster.setCommunityId("ADP_ACT");
        communityMaster.setPostId("FOOD_SELL");
        communityMasterRepository.save(communityMaster);
    }
}
