package com.ajd.meow.controller.chat;

import com.ajd.meow.entity.UserMaster;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Log4j2
public class ChatController {

    @GetMapping("/chat")
    public void getChat(HttpServletRequest request, @RequestParam String id) {

        HttpSession session = request.getSession();
        System.out.println("chat param id 값 출력 : "  + id);

        if (id.equals("guest")) { // 여기쪽 게스트가 채팅방 내 게스트 아이디를 보여주는 곳
            String name = "guest"; // 게스트 값을 db아이디 값을 받게 만들어 줌
            session.setAttribute("sessionId", name);
        } else if(id.equals("master")) {
            String name = "master";
            session.setAttribute("sessionId", name);
        }

        log.info("@ChatController, getChat()");
    }

    @GetMapping("admin/chat/master")
    public String enterChatAsMaster(HttpServletRequest request) {

        log.info("@ChatController, getChat()");
        return "/chat";
    }

    @GetMapping("/chat_btn")
    public String chatBtn(HttpSession session, Model model) {
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);
        return "chat_button";
    }
}
