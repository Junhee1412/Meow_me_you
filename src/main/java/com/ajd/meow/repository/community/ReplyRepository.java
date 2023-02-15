package com.ajd.meow.repository.community;

import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply,Long> {

    List<Reply> findAllByPostNo(Long postNo);
    List<Reply> findAllByUserNo(Long userNo);
    void deleteAllByUserNo(Long userNo);
    void deleteAllByPostNo(Long postNo);

    Page<Reply> findAllByUserNo(Long userNo, Pageable pageable);
    Page<Reply> findByReplyContentContaining(String searchKeyword, Pageable pageable);
    // 230212 주희 추가
    Reply findByReplyNo(Long replyNo);
}
