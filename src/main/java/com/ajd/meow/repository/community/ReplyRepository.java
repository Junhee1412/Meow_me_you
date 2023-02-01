package com.ajd.meow.repository.community;

import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply,Long> {
}
