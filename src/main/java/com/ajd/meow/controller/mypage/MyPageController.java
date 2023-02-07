package com.ajd.meow.controller.mypage;

import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.entity.SecondHandTrade;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.CommunityImageRepository;
import com.ajd.meow.repository.community.CommunityMasterRepository;
import com.ajd.meow.repository.community.SecondHandTradeRepository;
import com.ajd.meow.repository.donate.DonateRepository;
import com.ajd.meow.repository.user.UserRepository;
import com.ajd.meow.service.community.CommunityMasterService;
import com.ajd.meow.service.community.ReplyService;
import com.ajd.meow.service.donate.DonateService;
import com.ajd.meow.service.donate.DonateServiceImpl;
import com.ajd.meow.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private CommunityMasterRepository communityMasterRepository;
    @Autowired
    private CommunityImageRepository communityImageRepository;
    @Autowired
    private CommunityMasterService communityMasterService;
    @Autowired
    private DonateService donateService;
    @Autowired
    private DonateRepository donateRepository;
    @Autowired
    private SecondHandTradeRepository secondHandTradeRepository;
    @Autowired
    private ReplyService replyService;

    @GetMapping("my.meow") // 마이페이지로 이동
    public String my(HttpSession session, Model model){
        //user.setUserId(session.getId());
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);
        return "my_page";
    }

    @GetMapping("myPost.meow") // 내글 모두보기 / 페이징이 아직 확실하지않음.
    public String myPost(HttpSession session, Model model, @PageableDefault Pageable pageable){
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            UserMaster loginUser=(UserMaster)session.getAttribute("user");
            //List<CommunityMaster> com=communityMasterRepository.findAllById(Collections.singleton(loginUser.getUserNo()));
            Page<CommunityMaster> boardListFindByUserNO= communityMasterService.boardListByUserNO(loginUser.getUserNo(), pageable);
            model.addAttribute("userNickName",loginUser.getNickName());
            model.addAttribute("postList",boardListFindByUserNO);
            return "user_post_list";
        }
        //return "post_list";
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
        return "redirect:/my.meow";
    }

    @GetMapping("deleteUser.meow") // 회우너탈퇴 폼
    public String deleteUserForm(HttpSession session, Model model){
        //UserMaster loginUser=(UserMaster)session.getAttribute("user");
        UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
        model.addAttribute("user", loginUser);
        return "delete_user"; // ^ㅁ^
    }

    @PostMapping("deleteUser.meow") // 탈퇴완료
    public  String deleteUser(HttpSession session, @RequestParam("userPassword") String password, Model model){
        /*UserMaster loginUser=(UserMaster)session.getAttribute("user");
        if(loginUser.getUserPassword().equals(password)){
        userService.deleteMember(loginUser); return "index";
        }else{
            model.addAttribute("dontpatchpassword","비밀번호가 틀립니다.");
            return "delete_user";
        }*/
        // 탈퇴되었습니다 경고창 띄우기 해야함.
        if(session.getAttribute("user")==null){
            return "index";
        }else{
            //UserMaster deleteUser=(UserMaster) session.getAttribute("user");
            UserMaster deleteUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            if(deleteUser.getUserPassword().equals(password)){
                if(donateRepository.findByUserNo(deleteUser.getUserNo())!=null){ // 후원한게 있으면!
                // if문 : donateRepository.findAllById(Collections.singleton(deleteUser.getUserNo()))!=null
                    List<DonateMaster> donateMasters=donateRepository.findByUserNo(deleteUser.getUserNo());
                    // 유저번호로 도네이트마스터 다 찾아서 도네이트 코드로 다 지워줍니다.
                    for(DonateMaster donateMaster:donateMasters){
                        donateService.deleteDonate(donateMaster.getDonateCode());
                    }
                }
                if(communityMasterRepository.findAllByUserNo(deleteUser.getUserNo())!=null){ // 커뮤니티 게시글이 있으면!
                    // 유저번호로 커뮤니티 관련 다 지워줍니다.
                    List<CommunityMaster> communityMasters=communityMasterRepository.findAllByUserNo(deleteUser.getUserNo());
                    for(CommunityMaster communityMaster:communityMasters){
                        // 좋아요 삭제

                        // 이미지삭제
                        if(communityMaster.getCommunityImageList()!=null){
                            communityImageRepository.deleteAllById(Collections.singleton(communityMaster.getPostNo()));
                        }
                        // 중고거래 삭제
                        if(communityMaster.getCommunityId().equals("USD_TRN")){ // 커뮤니티 종류가 중고거래일 경우
                            secondHandTradeRepository.deleteById(communityMaster.getPostNo());
                        }
                        // 댓글삭제
                        if(communityMaster.getReplyList()!=null){
                            replyService.deleteAllReplyByPostID(communityMaster.getPostNo());
                        }
                    } // for문 종료
                }
                userService.deleteMember(deleteUser);
                return "index"; // 탈퇴되었습니다. 알림창 띄우기
            }else{
                model.addAttribute("dontpatchpassword","비밀번호가 틀립니다.");
                return "delete_user";
            }
        } // end else문
    }
}
