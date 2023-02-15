package com.ajd.meow.controller.chat;

import com.ajd.meow.entity.Chat;
import com.ajd.meow.repository.chat.ChatRepository;
import com.ajd.meow.service.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @Autowired
    ChatRepository repository;

    @Autowired
    ChatService chatService;

    // get contact
    @PostMapping("/contact")
    public Chat getOne(@RequestBody Chat param) {	//JSON으로 받은 param parsing

        Chat data = repository.findTop1ByUserNoOrderByChatDateDesc(param.getUserNo());

        if(data == null) {
            chatService.insert(param);
            return null;
        } else {
            return data;
        }
    }

    // insert DB
    @PostMapping("/contact/insert")
    public void save(@RequestBody Chat param) {
        chatService.insert(param);
    }
}