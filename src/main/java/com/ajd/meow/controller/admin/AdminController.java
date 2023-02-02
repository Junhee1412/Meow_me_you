package com.ajd.meow.controller.admin;


import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.CommunityRepository;
import com.ajd.meow.repository.user.UserRepository;
import com.ajd.meow.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.Id;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommunityRepository communityRepository;

    @GetMapping("admin.meow")
    public String adminmypage(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "index"; // 로그인안된 상태면 걍 index로 이동
        }else{
            UserMaster user=(UserMaster)session.getAttribute("user");
            model.addAttribute("user",user);
            if(user.getUserType().equals("ADMIN")){
                return "admin_my_page"; // 어드민 마이페이지로 이동
            }else{
                return "my_page"; // 유저 마이페이지로 이동?
            }
        }
    }

    @GetMapping("userlist.meow")
    public String userlist(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster user=(UserMaster)session.getAttribute("user");
            //model.addAttribute("user",user);
            if(user.getUserType().equals("ADMIN")){
                List<UserMaster> everyUser=userRepository.findAll();
                model.addAttribute("userList",everyUser);
                return "user_list"; // 어드민 마이페이지로 이동
            }else{
                return "index_login"; // 유저 마이페이지로 이동?
            }
        }
    }
    @GetMapping("postmanage.meow")
    public String postmanage(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster user=(UserMaster)session.getAttribute("user");
            //model.addAttribute("user",user);
            if(user.getUserType().equals("ADMIN")){
                List<Optional<UserMaster>> userINFO = null;
                List<CommunityMaster> everyPost=communityRepository.findAll();
                for(CommunityMaster com:everyPost){
                    userINFO= Collections.singletonList(userRepository.findById(com.getUserNo()));
                }
                model.addAttribute("postList",everyPost);
                model.addAttribute("postUser",userINFO);
                return "post_list"; // 어드민 마이페이지로 이동
            }else{
                return "index_login"; // 유저 마이페이지로 이동?
            }
        }
    }
}
