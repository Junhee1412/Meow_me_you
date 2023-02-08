package com.ajd.meow.service.community;

import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.Reply;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.CommunityMasterRepository;
import com.ajd.meow.repository.community.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ReplyService {

    @Autowired
    private final ReplyRepository replyRepository;
    @Autowired
    private final CommunityMasterRepository communityMasterRepository;

    @Transactional
    public void replySave(Long postNo, Reply reply,UserMaster userMaster) {
        CommunityMaster communityMaster = communityMasterRepository.findById(postNo).orElseThrow(() -> new IllegalArgumentException("해당 boardId가 없습니다. id="));

        reply.setUserMaster(userMaster);
        reply.setCommunityMaster(communityMaster);

        replyRepository.save(reply);
    }

    @Transactional
    public void replyDelete(Long replyNo) {
        replyRepository.deleteById(replyNo);
    }

    public Page<Reply> getAllReplyByUserNo(Long userNo, Pageable pageable){
        return replyRepository.findAllByUserNo(userNo, pageable);
    }
}