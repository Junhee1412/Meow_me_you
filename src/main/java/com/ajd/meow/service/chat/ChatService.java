package com.ajd.meow.service.chat;

import com.ajd.meow.entity.Chat;
import com.ajd.meow.repository.chat.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Service
public class ChatService {

    @Autowired
    ChatRepository repository;

    public void insert(Chat param) {
        // Date now = Date.valueOf(LocalDate.now());	// 현재시간 (Date type)
        // Timestamp now = Timestamp.valueOf(LocalDateTime.now());	// 현재시간 (Timestamp type add h,m,s)
        param.setChatDate(LocalDateTime.now());	// entity에 현재간 저장

        repository.save(param);	//db저장
    }
}