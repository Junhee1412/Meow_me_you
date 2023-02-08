package com.ajd.meow.controller.mypage;

import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.Reply;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.service.community.CommunityMasterService;
import com.ajd.meow.service.community.ReplyService;
import com.ajd.meow.service.donate.DonateService;
import com.ajd.meow.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class MyPageController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommunityMasterService communityMasterService;
    @Autowired
    private DonateService donateService;
    @Autowired
    private ReplyService replyService;

    @GetMapping("my.meow") // 마이페이지로 이동
    public String my(HttpSession session, Model model){
        //user.setUserId(session.getId());
        UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
        model.addAttribute("user",loginUser);
        return "my_page";
    }

    @GetMapping("myPost.meow") // 내글 모두보기
    public String myPost(HttpSession session, Model model, @PageableDefault(page = 0,size = 10, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable){
        if(session.getAttribute("user")==null){
            return "redirect:/"; // 홈으로
        }else{
            UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            //List<CommunityMaster> com=communityMasterRepository.findAllById(Collections.singleton(loginUser.getUserNo()));
            Page<CommunityMaster> boardListFindByUserNO= communityMasterService.boardListByUserNO(loginUser.getUserNo(), pageable);

            int nowPage = boardListFindByUserNO.getPageable().getPageNumber()+1 ;
            int startPage = Math.max(0 , 1);
            int endPage = Math.min(nowPage + 10 , boardListFindByUserNO.getTotalPages());

            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("maxPage",10);

            model.addAttribute("userNickName",loginUser.getNickName());
            model.addAttribute("postList",boardListFindByUserNO);
            return "user_post_list";
        }
    }
    @GetMapping("myReply.meow") // 내 덧글 모아보기
    public String myReply(HttpSession session, Model model, @PageableDefault(page = 0,size = 10, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            Page<Reply> replies=replyService.getAllReplyByUserNo(loginUser.getUserNo(), pageable);

            int nowPage = replies.getPageable().getPageNumber()+1 ;
            int startPage = Math.max(0 , 1);
            int endPage = Math.min(nowPage + 10 , replies.getTotalPages());

            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("maxPage",10);

            model.addAttribute("userNickName",loginUser.getNickName());
            model.addAttribute("replies",replies);

            return "user_reply_list";
        }
    }
    @GetMapping("myHeart.meow") // 좋아요 모아보기 / 일단은 미룸
    public String myheart(HttpSession session, Model model, @PageableDefault(page = 0,size = 10, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            return "user_reply_list";
        }
    }
    @GetMapping() // 내 후원 모아보기
    public String sdfsdf(HttpSession session, Model model, @PageableDefault(page = 0,size = 10, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            //dfdfsdfd
        }
        return "spon_list";
    }

    @GetMapping("modifyUser.meow") // 유저 수정 폼
    public String modifyUserForm(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            model.addAttribute("user",loginUser);
            return "mypage_modify";
        }
    }

    @PostMapping("modifyUser.meow") // 유저 수정
    public String modifyUser(UserMaster loginUser, Model model, MultipartFile file) throws  Exception{
        //UserMaster userModift=userRepository.save(loginUser);
        userService.updateMember(loginUser, file);
        model.addAttribute("user", loginUser);
        return "redirect:/my.meow";
    }

    @GetMapping("deleteUser.meow") // 회원탈퇴 폼
    public String deleteUserForm(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            model.addAttribute("user", loginUser);
            return "delete_user";
        }
    }

    @PostMapping("deleteUser.meow") // 탈퇴완료
    public  String deleteUser(HttpSession session, @RequestParam("userPassword") String password, Model model){
        // ☆ 메모장에 복사해둠!
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster deleteUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            if(deleteUser.getUserPassword().equals(password)){
                userService.deleteMember(deleteUser);
                // ☆ 주석처리 내용 메모장 복사
                return "redirect:/";
            }else{
                model.addAttribute("dontpatchpassword","비밀번호가 틀립니다.");
                return "delete_user";
            }
        }
    }
}