package com.ajd.meow.controller.user;

import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.user.UserRepository;
import com.ajd.meow.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("checkDuplication.meow/{userIdemail}") // 이메일 중복 체크
    @ResponseBody
    public String checkDuplication(@PathVariable String userIdemail){
        if(userRepository.existsByUserId(userIdemail)){
            return "unavailable";
        }else{return "available";}
    }

    @GetMapping("checkDuplicationNickName.meow/{userNickName}") // 닉네임 중복체크
    @ResponseBody
    public String checkDuplicationNickName(@PathVariable String userNickName){
        if(userRepository.existsByNickName(userNickName)){
            return "unavailable";
        }else{return "available";}
    }

    @PostMapping("sign_success.meow") //@ModelAttribute
    public String memberSignSuccess(UserMaster user, MultipartFile file) throws Exception{
        if(userRepository.existsByUserId(user.getUserId())){
            return "index";
            // 아마 이 화면 온거면 login안되잇을테니까 일단 걍 session 안받앗음.
            // 새로고침 시 유저가 중복 추가되는거 방지용으로 넣었음 - 걍 홈으로 이동함니다.
        }else{
            userService.insertMember(user, file);
            return "sign_success";
        }
    }

    @PostMapping("findid.meow")
    @ResponseBody
    public  String findId(Model model, @RequestParam("userName")String name, @RequestParam("phoneType")String type, @RequestParam("phoneNumber")String number){
        if(!userRepository.existsByUserName(name)){
            System.out.println("유저없음");
            return "none";
        }else {
            Optional<UserMaster>user=userRepository.findByUserName(name);
            if(user.get().getPhoneNumber().equals(number)&&user.get().getPhoneType().equals(type)){
                model.addAttribute("finduserId",user.get().getUserId());
                System.out.println(user.get().getUserId());
                return user.get().getUserId();
            } else{
                System.out.println("정보일치x");
                model.addAttribute("errorMsg","정보가 일치하지않습니다.");
                return "mismatch";
            }
        }
    }

    @PostMapping("resettingpw.meow")
    @ResponseBody
    public String resettingPW(Model model, @RequestParam("userId")String userid, @RequestParam("userName")String name, @RequestParam("phoneType")String type, @RequestParam("phoneNumber")String number){
        if(!userRepository.existsByUserId(userid)){
            System.out.println("아이디가 존재하지않는다.");
            return "none";
        }else{
            Optional<UserMaster> user=userRepository.findByUserId(userid);
            if(user.get().getUserName().equals(name)&&user.get().getPhoneType().equals(type)&&user.get().getPhoneNumber().equals(number)){
                model.addAttribute("userid",userid);
                return "available";//"pwd_reset";
            }else{
                return "mismatch";
            }
        }
    }
    @GetMapping("resetting_pw.meow/{resetid}") // resetting_pw.meow/${resetid}
    public String resetPW(@PathVariable("resetid")String resetid, Model model){
        model.addAttribute("userid",resetid);
        return "pwd_reset";
    }

    @PostMapping("changepw.meow")
    public String changepw(@RequestParam("userId")String userId, @RequestParam("userPassword")String password, Model model){
        UserMaster user=userRepository.findByUserId(userId).get();
        userService.updateMemberPassword(user);
        model.addAttribute("userid",user.getUserId());
        return "pwd_success";
    }
}
