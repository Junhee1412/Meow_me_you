package com.ajd.meow.controller.community;

import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.Reply;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.service.community.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class ReplyApiController {

    @Autowired
    private final ReplyService replyService;
    @PostMapping("/api/board/{postNo}/reply")
    public void save(@PathVariable Long postNo,
                       @RequestBody Reply reply, HttpSession session) {
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        reply.setPostNo(postNo);
        reply.setUserNo(loginUser.getUserNo());
        System.out.println(reply);
        replyService.replySave(postNo, reply,loginUser);

    }

    @DeleteMapping("/api/v1/board/{postNo}/reply/{replyNo}")
    public void delete(@PathVariable Long replyNo ,HttpSession session) {
        System.out.println(replyNo);

//        Long replyUserNo = replyRepository.findById(replyNo).get().getUserNo();



        replyService.replyDelete(replyNo);

    }
}