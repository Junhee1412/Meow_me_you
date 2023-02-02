package com.ajd.meow.controller.mypage;

import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.user.UserRepository;
import com.ajd.meow.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class MyPageController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("my.meow")
    public String my(HttpSession session, Model model){
        //user.setUserId(session.getId());
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);
        return "my_page";
    }

    @GetMapping("myPost.meow")
    public String myPost(){
        return "post_list";
    }

    @GetMapping("modifyUser.meow")
    public String modifyUserForm(HttpSession session, Model model){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);
        return "mypage_modify";
    }

    @PostMapping("modifyUser.meow")
    public String modifyUser(UserMaster loginUser, Model model){
        //UserMaster userModift=userRepository.save(loginUser);
        userService.updateMember(loginUser);
        model.addAttribute("user", loginUser);
        return "my_page";
    }

    @GetMapping("deleteUser.meow")
    public String deleteUserForm(HttpSession session, Model model){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user", loginUser);
        return "delete_user"; // ^ㅁ^
    }

    @PostMapping("deleteUser.meow")
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
