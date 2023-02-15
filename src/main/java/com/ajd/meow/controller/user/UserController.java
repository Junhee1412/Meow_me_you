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

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("goToTerms")
    public String goToTerms(){
        return "user/terms";
    }

    @GetMapping("sign_user")
    public String memberSign(){
        return "user/sign_page";
    }

    @GetMapping("checkDuplication/{userIdemail}") // 이메일 중복 체크
    @ResponseBody
    public String checkDuplication(@PathVariable String userIdemail){
        if(userRepository.existsByUserId(userIdemail)){
            return "unavailable";
        }else{return "available";}
    }

    @GetMapping("checkDuplicationNickName/{userNickName}") // 닉네임 중복체크
    @ResponseBody
    public String checkDuplicationNickName(@PathVariable String userNickName){
        if(userRepository.existsByNickName(userNickName)){
            return "unavailable";
        }else{return "available";}
    }

    @PostMapping("sign_success") //@ModelAttribute
    public String memberSignSuccess(UserMaster user, MultipartFile file) throws Exception{
        if(userRepository.existsByUserId(user.getUserId())){
            return "redirect:/";
            // 아마 이 화면 온거면 login안되잇을테니까 일단 걍 session 안받앗음.
            // 새로고침 시 유저가 중복 추가되는거 방지용으로 넣었음 - 걍 홈으로 이동함니다.
        }else{
            userService.insertMember(user, file);
            return "user/sign_success";
        }
    }

    @PostMapping("findid")
    @ResponseBody
    public  String findId(Model model, @RequestParam("userName")String name, @RequestParam("phoneType")String type, @RequestParam("phoneNumber")String number){
        if(!userRepository.existsByUserName(name)){
            System.out.println("유저없음");
            return "none";
        }else {
            List<UserMaster> userInfo=userRepository.findAllByUserName(name);
            String userRealId="";
            for(UserMaster userIn:userInfo){
                if(userIn.getPhoneType().equals(type)&&userIn.getPhoneNumber().equals(number)){
                    userRealId=userIn.getUserId();
                }
            }
            if(userRealId.isEmpty()){
                return "mismatch";
            }else{return userRealId;}
        }
    }

    @PostMapping("resettingpw")
    @ResponseBody
    public String resettingPW(Model model, @RequestParam("userId")String userid, @RequestParam("userName")String name, @RequestParam("phoneType")String type, @RequestParam("phoneNumber")String number){
        if(!userRepository.existsByUserId(userid)){
            System.out.println("아이디가 존재하지않는다.");
            return "none";
        }else{
            Optional<UserMaster> user=userRepository.findByUserId(userid);
            if(user.get().getUserName().equals(name)&&user.get().getPhoneType().equals(type)&&user.get().getPhoneNumber().equals(number)){
                model.addAttribute("userid",userid);
                return "available";//"user/pwd_reset";
            }else{
                return "mismatch";
            }
        }
    }
    @PostMapping("resetting_pw") // resetting_pw/${resetid}
    public String resetPW(@RequestParam("userId")String userId, Model model){
        model.addAttribute("userid",userId);
        return "user/pwd_reset";
    }
    @PostMapping("finalResetPW")
    public String finalResetPWForm(@RequestParam("userId")String userId, Model model){
        model.addAttribute("userid",userId);
        return "user/pwd_reset";
    }

    @PostMapping("changepw")
    public String changepw(@RequestParam("userId")String userId, @RequestParam("userPassword")String password, Model model){
        //UserMaster user=userRepository.findByUserId(userId).get();
        //userService.updateMemberPassword(user);
        userRepository.findByUserId(userId).get().setUserPassword(password);
        userRepository.save(userRepository.findByUserId(userId).get());
        //model.addAttribute("userid",user.getUserId());
        model.addAttribute("userid",userRepository.findByUserId(userId));
        return "user/pwd_success";
    }
}
