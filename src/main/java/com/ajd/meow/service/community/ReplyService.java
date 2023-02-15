package com.ajd.meow.service.community;

import com.ajd.meow.entity.Reply;
import com.ajd.meow.entity.UserMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyService {

    //댓글 작성
    void replySave(Long postNo, Reply reply, UserMaster userMaster);
    //댓글 삭제
    void replyDelete(Long replyNo);

    Page<Reply> getAllReplyByUserNo(Long userNo, Pageable pageable);

    void deleteAllReplyByUserNo(Long userNo);

    void deleteAllReplyByPostNo(Long postNo);

    Reply findReply(Long replyNo);

    Page<Reply> replySearchKeyword(String searchKeyword, Pageable pageable);
}
