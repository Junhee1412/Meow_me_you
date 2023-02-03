package com.ajd.meow.repository.chat;

import com.ajd.meow.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    // findBy컬럼이름(컬럼 타입); => 정의가능
    Chat findTop1ByUserNoOrderByChatDateDesc(Long userNo);
}