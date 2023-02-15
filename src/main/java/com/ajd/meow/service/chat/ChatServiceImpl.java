package com.ajd.meow.service.chat;

import com.ajd.meow.entity.Chat;
import com.ajd.meow.repository.chat.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    ChatRepository chatRepository;

    public void insert(Chat param) {
        param.setChatDate(LocalDateTime.now());
        chatRepository.save(param);	//db저장
    }
}