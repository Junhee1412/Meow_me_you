package com.ajd.meow.controller.user;

import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.user.UserRepository;
import com.ajd.meow.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("sign.meow")
    public String memberSign(){
        return "sign";
    }

    @PostMapping("sign_success.meow")
    public String memberSignSuccess(/*@ModelAttribute */UserMaster user){
        userService.insertMember(user);
        return "sign_success";
    }

    /*
    @GetMapping("findid.meow") // 아이디, 비번찾기
    public String findId(){
        return "find_id";
    }

    @PostMapping("findid.meow")
    public String findID(@RequestParam String name, String email){
        return "id_find";
    }

    @PostMapping("findpw.meow")
    public String findPW(){
        return "리다이랙트:";
    }
 */
    @PostMapping("resettingpw.meow")
    public String resettingPW(UserMaster user, @RequestParam("confirmCode")String code, Model model){
        Optional<UserMaster> finduser=userRepository.findByUserName(user.getUserName());
        if(user.getUserId()!=null&&user.getUserName().equals(finduser.get().getUserName())){
            return "pwd_reset";
        }else if(user.getUserId()!=null&&!user.getUserName().equals(finduser.get().getUserName())){
            model.addAttribute("mismatch","아이디와 이름이 매치되지않습니다.");
            return ""; // 리다이렉트?
        }else if(user.getUserId().isEmpty()){
            model.addAttribute("nouser","해당 이메일의 유저가 존재하지않습니다.");
            return ""; // 리다이렉트?
        }
        return ""; // 뭐 적어야하지...
    }
}
