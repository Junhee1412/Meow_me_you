package com.ajd.meow.controller.mypage;

import com.ajd.meow.entity.CommunityLike;
import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.Reply;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.CommunityLikeRepository;
import com.ajd.meow.service.community.CommunityService;
import com.ajd.meow.service.community.ReplyService;
import com.ajd.meow.service.donate.DonateService;
import com.ajd.meow.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class MyPageController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private DonateService donateService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private CommunityLikeRepository communityLikeRepository;// 이거 좋아요 누른 게시글 모아보기, 게시글 삭제하기에서 사용



    @GetMapping("my_page") // 마이페이지로 이동
    public String my(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            model.addAttribute("user",loginUser);
            return "user/mypage";
        }
    }



    // 닉네임 중복체크는 회원가입 시 쓰는 닉네임 중복체크꺼 가져다가 쓴다 - UserController 에 있음
    @PostMapping("changeNickName")
    public String changeNickName(HttpSession session, @RequestParam("nickName")String nickName){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            userService.updateNickName(loginUser.getUserNo(),nickName);
            return "redirect:/my_page";
        }
    }


    @GetMapping("myPost") // 내글 모두보기
    public String myPost(HttpSession session, Model model, @PageableDefault(page = 0,size = 10, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword){
        if(session.getAttribute("user")==null){
            return "redirect:/"; // 홈으로
        }else{
            UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            //List<CommunityMaster> com=communityMasterRepository.findAllById(Collections.singleton(loginUser.getUserNo()));
//            Page<CommunityMaster> boardListFindByUserNO= communityService.boardListByUserNO(loginUser.getUserNo(), pageable);

            Page<CommunityMaster> boardListFindByUserNO = null;

            if(searchKeyword == null ) {
                boardListFindByUserNO=  communityService.boardListByUserNO(loginUser.getUserNo(), pageable);
            }else {
                boardListFindByUserNO= communityService.communitySearchKeyword(searchKeyword,pageable);
            }

            int nowPage = boardListFindByUserNO.getPageable().getPageNumber()+1 ;
            int startPage = Math.max(0 , 1);
            int endPage = Math.min(nowPage + 10 , boardListFindByUserNO.getTotalPages());

            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("maxPage",10);

            model.addAttribute("userNickName",loginUser.getNickName());
            model.addAttribute("postList",boardListFindByUserNO);
            model.addAttribute("userType",loginUser.getUserType());
            return "user/user_post_list";
        }
    }



    @GetMapping("deletePostInMyPage") // 마이페이지에서 게시글 지우기 - 어드민도 공유
    public String deletePostInMyPage(HttpSession session, Long postNo){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            Long usernumber=communityService.findPost(postNo).getUserNo();
            UserMaster userMaster=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            replyService.deleteAllReplyByPostNo(postNo);// 좋아요 지우기
            communityLikeRepository.deleteLikesByPost(postNo);// 좋아요 지우기
            communityService.communityPostDelete(postNo); // 게시글 지우기
            if(userMaster.getUserType().equals("ADMIN")){
                return "redirect:/adminUserPost?userNo="+usernumber;
            }else{
                return "redirect:/myPost";
            }
        }
    }



    @GetMapping("myReply") // 내 덧글 모아보기
    public String myReply(HttpSession session, Model model, @PageableDefault(page = 0,size = 10, sort = "postNo",
             direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
//            Page<Reply> replies=replyService.getAllReplyByUserNo(loginUser.getUserNo(), pageable);

            Page<Reply> replies = null;

            if(searchKeyword == null ) {
                replies= replyService.getAllReplyByUserNo(loginUser.getUserNo(), pageable);
            }else {
                replies= replyService.replySearchKeyword(searchKeyword,pageable);
            }

            int nowPage = replies.getPageable().getPageNumber()+1 ;
            int startPage = Math.max(0 , 1);
            int endPage = Math.min(nowPage + 10 , replies.getTotalPages());

            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("maxPage",10);

            model.addAttribute("userNickName",loginUser.getNickName());
            model.addAttribute("replies",replies);

            model.addAttribute("userType", loginUser.getUserType());

            return "user/user_reply_list";
            //return "user/user_post_list";
        }
    }



    @GetMapping("deleteReplyInMyPage") // 관리자(어드민), 일반유저가 마이페이지에서 리플(덧글) 삭제
    public String deleteReplyInMyPage(HttpSession session, Long replyNo){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster userMaster=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            Long usernumber=replyService.findReply(replyNo).getUserNo();
            replyService.replyDelete(replyNo);
            if(userMaster.getUserType().equals("ADMIN")){
                return "redirect:/adminUserReply?userNo="+usernumber;
            }else{
                return "redirect:/myReply";
            }
        }
    }



    @GetMapping("myHeart") // 좋아요 모아보기
    public String myheart(HttpSession session, Model model, @PageableDefault(page = 0,size = 10) Pageable pageable){
        // , sort = "postNo", direction = Sort.Direction.DESC  <<  pageabledefault 괄호 안에 적는건데 적으면 오류남 - 쿼리문에 적음
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            Page<CommunityLike> likes=communityLikeRepository.selectLikesByUser(loginUser.getUserNo(),pageable);

            int nowPage = likes.getPageable().getPageNumber()+1 ;
            int startPage = Math.max(0 , 1);
            int endPage = Math.min(nowPage + 10 , likes.getTotalPages());

            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("maxPage",10);

            model.addAttribute("userNickName",loginUser.getNickName());
            model.addAttribute("likes",likes);

            return "user/user_likes_list";
        }
    }


    // 이거 필요 없음
    /*@GetMapping() // 내 후원 모아보기
    public String sdfsdf(HttpSession session, Model model, @PageableDefault(page = 0,size = 10, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            //dfdfsdfd
        }
        return "donate/my_donate_list";
    }*/



    @GetMapping("modifyUser") // 유저 수정 폼
    public String modifyUserForm(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            model.addAttribute("user",loginUser);
            return "user/mypage_modify";
        }
    }



    @PostMapping("modifyUser") // 유저 수정
    public String modifyUser(UserMaster loginUser, Model model, MultipartFile file) throws  Exception{
        //UserMaster userModift=userRepository.save(loginUser);
        userService.updateMember(loginUser, file);
        model.addAttribute("user", loginUser);
        return "redirect:/my_page";
    }



    @GetMapping("deleteUser") // 회원탈퇴 폼
    public String deleteUserForm(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            model.addAttribute("user", loginUser);
            return "user/delete_user";
        }
    }



    @PostMapping("deleteUser") // 탈퇴완료
    public  String deleteUser(HttpSession session, @RequestParam("userPassword") String password, Model model, SessionStatus status){
        // ☆ 메모장에 복사해둠!
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster deleteUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            if(deleteUser.getUserPassword().equals(password)){
                userService.deleteMember(deleteUser);
                status.setComplete();
                // ☆ 주석처리 내용 메모장 복사
                return "redirect:/";
            }else{
                model.addAttribute("dontpatchpassword","비밀번호가 틀립니다.");
                return "user/delete_user";
            }
        }
    }
}