package com.ajd.meow.service.community;

import com.ajd.meow.entity.Reply;
import com.ajd.meow.entity.UserMaster;

public interface ReplyService {

    //댓글 작성
    void replySave(Long postNo, Reply reply, UserMaster userMaster);
    //댓글 삭제
    void replyDelete(Long replyNo);
}
