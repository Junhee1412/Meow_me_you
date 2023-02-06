package com.ajd.meow.controller.mypage;

import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.CommunityRepository;
import com.ajd.meow.repository.user.UserRepository;
import com.ajd.meow.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("user")
public class MyPageController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommunityRepository communityRepository;

    @GetMapping("my.meow") // 마이페이지로 이동
    public String my(HttpSession session, Model model){
        //user.setUserId(session.getId());
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);
        return "my_page";
    }

    @GetMapping("myPost.meow") // 내글 모두보기
    public String myPost(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster loginUser=(UserMaster)session.getAttribute("user");
            List<CommunityMaster> com=communityRepository.findAllById(Collections.singleton(loginUser.getUserNo()));
            model.addAttribute("postList",com);
        }
        return "post_list";
    }
    @GetMapping("myReply.meow") // 내 덧글 모아보기 / 일단은 미룸
    public String myReply(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster loginUser=(UserMaster)session.getAttribute("user");
            return "";
        }
    }
    @GetMapping("myHeart.meow") // 좋아요 모아보기 / 일단은 미룸
    public String myheart(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster loginUser=(UserMaster)session.getAttribute("user");
            return "";
        }
    }

    @GetMapping("modifyUser.meow") // 유저 수정 폼
    public String modifyUserForm(HttpSession session, Model model){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);
        return "mypage_modify";
    }

    @PostMapping("modifyUser.meow") // 유저 수정
    public String modifyUser(UserMaster loginUser, Model model, MultipartFile file) throws  Exception{
        //UserMaster userModift=userRepository.save(loginUser);
        userService.updateMember(loginUser, file);
        model.addAttribute("user", loginUser);
        return "my_page";
    }

    @GetMapping("deleteUser.meow") // 회우너탈퇴 폼
    public String deleteUserForm(HttpSession session, Model model){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user", loginUser);
        return "delete_user"; // ^ㅁ^
    }

    @PostMapping("deleteUser.meow") // 탈퇴완료
    public  String deleteUser(HttpSession session, @RequestParam("userPassword") String password, Model model){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        if(loginUser.getUserPassword().equals(password)){
        userService.deleteMember(loginUser); return "index";
        }else{
            model.addAttribute("dontpatchpassword","비밀번호가 틀립니다.");
            return "delete_user";
        }
        // 탈퇴되었습니다 경고창 띄우기 해야함.
    }
}
