package com.ajd.meow.controller.user;

import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.user.UserRepository;
import com.ajd.meow.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        if(userRepository.existsByUserId(user.getUserId())){
            return "index";
            // 아마 이 화면 온거면 login안되잇을테니까 일단 걍 session 안받앗음.
            // 새로고침 시 유저가 중복 추가되는거 방지용으로 넣었음 - 걍 홈으로 이동함니다.
        }else{
            userService.insertMember(user);
            return "sign_success";
        }
    }

    @PostMapping("findid.meow")
    public  String findId(Model model, @RequestParam("userName")String name, @RequestParam("phoneType")String type, @RequestParam("phoneNumber")String number){
        if(!userRepository.existsByUserName(name)){
            System.out.println("유저없음");
            model.addAttribute("errorMsg","해당 이름의 유저가 존재하지않습니다.");
            model.addAttribute("name",name);
            model.addAttribute("type",type);
            model.addAttribute("number",number);
            return "join";
        }else {
            Optional<UserMaster>user=userRepository.findByUserName(name);
            if(user.get().getPhoneNumber().equals(number)&&user.get().getPhoneType().equals(type)){
                model.addAttribute("finduserId",user.get().getUserId());
                System.out.println(user.get().getUserId());
                return "join";
            } else{
                System.out.println("정보일치x");
                model.addAttribute("errorMsg","정보가 일치하지않습니다.");
                return "join";
            }
        }
    }

    @PostMapping("resettingpw.meow")
    public String resettingPW(Model model, @RequestParam("userId")String userid, @RequestParam("userName")String name, @RequestParam("phoneType")String type, @RequestParam("phoneNumber")String number){
        if(!userRepository.existsByUserId(userid)){
            System.out.println("아이디가 존재하지않는다.");
            model.addAttribute("errorMsg","아이디가 존재하지않습니다.");
            return "join";
        }else{
            Optional<UserMaster> user=userRepository.findByUserId(userid);
            if(user.get().getUserName().equals(name)&&user.get().getPhoneType().equals(type)&&user.get().getPhoneNumber().equals(number)){
                model.addAttribute("userid",userid);
                return "pwd_reset";
            }else{
                model.addAttribute("errorMsg","유저 정보가 일치하지않습니다.");
                return "join";
            }
        }
    }

    @PostMapping("changepw.meow")
    public String changepw(UserMaster user, Model model){
        userService.updateMemberPassword(user);
        model.addAttribute("userid",user.getUserId());
        return "pwd_success";
    }
}
