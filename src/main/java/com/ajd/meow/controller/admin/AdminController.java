package com.ajd.meow.controller.admin;


import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.CommunityMasterRepository;
import com.ajd.meow.repository.donate.DonateRepository;
import com.ajd.meow.repository.user.UserRepository;
import com.ajd.meow.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
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
    private CommunityMasterRepository communityMasterRepository;
    @Autowired
    private DonateRepository donateRepository;

    @GetMapping("admin.meow") // 어드민의 마이페이지로 이동
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
    @GetMapping("modifyadmin.meow") // 어드민 수정 폼
    public String modidyadminform(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster user=(UserMaster)session.getAttribute("user");
            model.addAttribute("user",user);
            if(user.getUserType().equals("ADMIN")){
                return "admin_mypage_modify"; // 어드민 수정 페이지
            }else{
                return "my_page"; // 유저 마이페이지로 이동
            }
        }
    }
    @PostMapping("modifyAdmin.meow") // 어드민 수정
    public String modidyadmin(UserMaster userMaster, HttpSession session, Model model, MultipartFile file) throws Exception{
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            userService.updateMember(userMaster, file);
            Optional<UserMaster> userMM=userRepository.findByUserName(userMaster.getUserName());
            model.addAttribute("user",userMM.get());
            if(userMM.get().getUserType().equals("ADMIN")){
                return "admin_my_page";
            }else{
                return "my_page"; // 유저 마이페이지로 이동
            }
        }
    }
    @PostMapping("createadminuser.meow") // 관리자 생성
    public String createadminuserform(HttpSession session, Model model, UserMaster users){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster userMaster=(UserMaster)session.getAttribute("user");
            if(userMaster.getUserType().equals("ADMIN")){
                users.setUserType("ADMIN");
                users.setUserJoinDate(LocalDateTime.now());
                userRepository.save(users);
                return "redirect:/admin.meow";
            }else{return "my_page";}
        }
    }

    @GetMapping("userlist.meow") // 유저리스트 보기
    public String userlist(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster user=(UserMaster)session.getAttribute("user");
            //model.addAttribute("user",user);
            if(user.getUserType().equals("ADMIN")){
                List<UserMaster> everyUser=userRepository.findAll();
                model.addAttribute("userList",everyUser);
                return "admin_user_list"; // 어드민 마이페이지로 이동
            }else{
                return "index_login"; // 유저 마이페이지로 이동?
            }
        }
    }

    @GetMapping("userMaster/{userNo}") // 해당 유저의 디테일 보기
    public String userdetail(HttpSession session, Model model, @PathVariable Long userNo){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster user=(UserMaster) session.getAttribute("user");
            if(user.getUserType().equals("ADMIN")){
                if(userRepository.findById(userNo).isEmpty()){
                    //model.addAttribute("err","해당유저가 존재하지않습니다.");
                    return "redirect:/userlist.meow";
                }else{
                    UserMaster certainuser=userRepository.findById(userNo).get();
                    model.addAttribute("user",certainuser);
                    return "admin_user_detail";
                }
            }else{
                return "index_login";
            }
        }
    }

    @GetMapping("{userNo}/postlist") // 해당 유저의 글 리스트
    public String userpostlist(HttpSession session, @PathVariable Long userNo, Model model){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster user=(UserMaster) session.getAttribute("user");
            if(user.getUserType().equals("ADMIN")){
                if(userRepository.findById(userNo).isEmpty()){
                    model.addAttribute("err","해당유저가 존재하지않습니다.");
                    return "redirect:/";
                }else{
                    List<CommunityMaster> com=communityMasterRepository.findAllById(Collections.singleton(userNo));
                    model.addAttribute("postList",com);
                    return "post_list";
                }
            }else{
                return "index_login";
            }
        }//return "post_list";
    }

    @GetMapping("postmanage.meow") // 모든 게시글 보기
    public String postmanage(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster user=(UserMaster)session.getAttribute("user");
            //model.addAttribute("user",user);
            if(user.getUserType().equals("ADMIN")){
                List<Optional<UserMaster>> userINFO = null;
                List<CommunityMaster> everyPost=communityMasterRepository.findAll();
                for(CommunityMaster com:everyPost){
                    userINFO= Collections.singletonList(userRepository.findById(com.getUserNo()));
                }
                model.addAttribute("postList",everyPost);
                model.addAttribute("postUser",userINFO);
                return "admin_post_list"; // 어드민 마이페이지로 이동
            }else{
                return "index_login"; // 유저 마이페이지로 이동?
            }
        }
    }

    @GetMapping("donatemanage.meow") // 모든 도네이트 보기
    public String donatemanage(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster user=(UserMaster)session.getAttribute("user");
            if(user.getUserType().equals("ADMIN")){
                // donateRepository.findAll(); // 모든 도네이트
                return "admin_donate_list";
            }else{
                return "index_login";
            }
        }
    }

    //후원사업 개시
    @GetMapping("createdonateBS.meow")
    public String createdonateBSform(HttpSession session, Model model){
        return "시간이 있으면 합시다!"; // ^ㅁ^
    }
    //후원사업 삭제
    @GetMapping("deletedonateBS.meow")
    public String deletedonateBSform(HttpSession session, Model model){
        return "시간이 있으면 합시다!"; // ^ㅁ^
    }
    @GetMapping("insertnotice.meow")//공지적는 폼으로 이동
    public String insertnoticeform(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster userMaster=(UserMaster)session.getAttribute("user");
            //model.addAttribute("user",userMaster);
            model.addAttribute("adminNo",userMaster.getUserNo());
            return "admin_post_insert";
        }
    }
    @PostMapping("insertnotice.meow")//공지등록
    public String insertnotice(CommunityMaster com, HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster userMaster=(UserMaster)session.getAttribute("user");
            model.addAttribute("user",userMaster);
            com.setCreatePostDate(LocalDateTime.now());
            communityMasterRepository.save(com);
            return "redirect:/admin.meow"; // 새로고침해도 인서트되지않게끔 함니다.
        }
    }
}
