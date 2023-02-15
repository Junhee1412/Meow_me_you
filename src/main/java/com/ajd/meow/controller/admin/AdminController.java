package com.ajd.meow.controller.admin;


import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.entity.Reply;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.CommunityMasterRepository;
import com.ajd.meow.repository.donate.DonateRepository;
import com.ajd.meow.repository.user.UserRepository;
import com.ajd.meow.service.community.CommunityService;
import com.ajd.meow.service.community.ReplyService;
import com.ajd.meow.service.donate.DonateService;
import com.ajd.meow.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
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
    @Autowired
    private DonateService donateService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private ReplyService replyService;



    @GetMapping("adminMypage") // 어드민의 마이페이지로 이동
    public String adminMypage(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "redirect:/"; // 로그인안된 상태면 걍 index로 이동
        }else{
            UserMaster user=(UserMaster)session.getAttribute("user");
            model.addAttribute("user",user);
            if(user.getUserType().equals("ADMIN")){
                return "admin/admin_mypage"; // 어드민 마이페이지로 이동
            }else{
                return "redirect:/my_page"; // 유저 마이페이지로 이동?
            }
        }
    }



    @GetMapping("adminModify") // 어드민 수정 폼
    public String modifyAdminForm(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster user=(UserMaster)session.getAttribute("user");
            model.addAttribute("user",user);
            if(user.getUserType().equals("ADMIN")){
                return "admin/admin_mypage_modify"; // 어드민 수정 페이지
            }else{
                return "redirect:/";
            }
        }
    }



    @PostMapping("adminModify") // 어드민 수정
    public String modifyAdmin(UserMaster userMaster, HttpSession session, Model model, MultipartFile file) throws Exception{
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            if(userMaster.getProfileImagePath()!=null) {
                return null;
            }else {
                userMaster.setProfileImagePath(null);
                userMaster.setProfileImageName(null);
                userService.updateMember(userMaster, file);
            }
            Optional<UserMaster> userMM=userRepository.findByUserId(userMaster.getUserId());
            model.addAttribute("user",userMM.get());
            if(userMM.get().getUserType().equals("ADMIN")){
                return "redirect:/adminMypage";//"admin/admin_mypage";
            }else{
                return "redirect:/";
            }
        }
    }



    @PostMapping("adminCreate") // 관리자 생성 - 잘 되는지 확인
    public String createAdminUserForm(HttpSession session, Model model, UserMaster users){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster userMaster=(UserMaster)session.getAttribute("user");
            if(userMaster.getUserType().equals("ADMIN")){
                users.setUserType("ADMIN");
                users.setUserJoinDate(LocalDateTime.now());
                userRepository.save(users);
                return "redirect:adminMypage";
            }else{return "redirect:/my_page";}
        }
    }



    @GetMapping("adminUserList") // 유저리스트 보기
    public String userList(HttpSession session, Model model, @PageableDefault(page = 0,size = 10, sort = "userNo", direction = Sort.Direction.DESC) Pageable pageable){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster user=(UserMaster)session.getAttribute("user");
            //model.addAttribute("user",user);
            if(user.getUserType().equals("ADMIN")){
                Page<UserMaster> everyUser=userRepository.findAll(pageable);//userRepository.findAll();

                int nowPage = everyUser.getPageable().getPageNumber()+1 ;
                int startPage = Math.max(0 , 1);
                int endPage = Math.min(nowPage + 10 , everyUser.getTotalPages());

                model.addAttribute("nowPage", nowPage);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
                model.addAttribute("maxPage",10);

                model.addAttribute("userList",everyUser);
                return "admin/admin_user_list"; // 어드민 마이페이지로 이동
            }else{
                return "redirect:/";
            }
        }
    }

    @GetMapping("adminUserDetail") // 해당 유저의 디테일 보기
    public String userDetail(HttpSession session, Model model, Long userNo){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster user=(UserMaster) session.getAttribute("user");
            if(user.getUserType().equals("ADMIN")){
                if(userRepository.findById(userNo).isEmpty()){
                    return "redirect:/adminUserList"; // 없는 유저번호일 경우 이동
                }else{
                    UserMaster certainuser=userRepository.findById(userNo).get();
                    model.addAttribute("user",certainuser);
                    return "admin/admin_user_detail";
                }
            }else{
                return "redirect:/";
            }
        }
    }



    @GetMapping("adminUserDelete") // 해당 유저 삭제
    public String userDelete(HttpSession session, Long userNo){
        if(session.getAttribute("user")==null){
            return "redirect:/index";
        }else{
            UserMaster loginuser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            if(loginuser.getUserType().equals("ADMIN")){
                userService.deleteMember(userService.getUser(userNo));
                return "redirect:/adminUserList";
            }else{
                return "redirect:/my_page";
            }
        }
    }



    @GetMapping("adminUserPost") // 해당 유저의 글 리스트
    public String userPostList(HttpSession session, Long userNo, Model model, @PageableDefault(page = 0,size = 10, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster user=(UserMaster) session.getAttribute("user");
            if(user.getUserType().equals("ADMIN")){
                if(userRepository.findById(userNo).isEmpty()){
                    //model.addAttribute("err","해당유저가 존재하지않습니다.");
                    return "redirect:/adminUserList";
                }else{
                    Page<CommunityMaster> boardListFindByUserNO= communityService.boardListByUserNO(userNo, pageable);

                    int nowPage = boardListFindByUserNO.getPageable().getPageNumber()+1 ;
                    int startPage = Math.max(0 , 1);
                    int endPage = Math.min(nowPage + 10 , boardListFindByUserNO.getTotalPages());

                    model.addAttribute("nowPage", nowPage);
                    model.addAttribute("startPage", startPage);
                    model.addAttribute("endPage", endPage);
                    model.addAttribute("maxPage",10);

                    model.addAttribute("userNickName",userService.getUser(userNo).getNickName());
                    model.addAttribute("postList",boardListFindByUserNO);
                    model.addAttribute("userType",user.getUserType());

                    // 추가
                    model.addAttribute("userNo",userNo);
                    return "user/user_post_list";
                }
            }else{
                return "redirect:/";
            }
        }
    }



    @GetMapping("adminUserDonate") // 해당 유저의 도네이트(후원) 리스트
    public String userAllDonate(HttpSession session, Long userNo, Model model, @PageableDefault(page = 0,size = 10, sort = "donateCode", direction = Sort.Direction.DESC) Pageable pageable){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster user=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            if(user.getUserType().equals("ADMIN")){
                //model.addAttribute("list", donateService.donateList());
                Page<DonateMaster> userAllDonateList=donateService.donateMyList(pageable, userNo);

                model.addAttribute("user",userService.getUser(userNo));
                model.addAttribute("list", userAllDonateList);

                int nowPage = userAllDonateList.getPageable().getPageNumber()+1 ;
                int startPage = Math.max(0 , 1);
                int endPage = Math.min(nowPage + 10 , userAllDonateList.getTotalPages());

                model.addAttribute("nowPage", nowPage);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
                model.addAttribute("maxPage",10);

                //추가
                model.addAttribute("userType", userService.getUser(userNo).getUserType());

                return "admin/admin_donate_list";//"donate/my_donate_list";
            }else{
                return "redirect:/";
            }
        }
    }



    @GetMapping("adminUserReply") // 해당 유저의 덧글 리스트
    public String userAllReply(HttpSession session, Long userNo, Model model, @PageableDefault(page = 0,size = 10, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster getSessionUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            if(getSessionUser.getUserType().equals("ADMIN")){
                Page<Reply> userAllReplyList=replyService.getAllReplyByUserNo(userNo, pageable);

                int nowPage = userAllReplyList.getPageable().getPageNumber()+1 ;
                int startPage = Math.max(0 , 1);
                int endPage = Math.min(nowPage + 10 , userAllReplyList.getTotalPages());

                model.addAttribute("nowPage", nowPage);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
                model.addAttribute("maxPage",10);

                model.addAttribute("userNickName",userService.getUser(userNo).getNickName());
                model.addAttribute("replies",userAllReplyList);

                model.addAttribute("userType", getSessionUser.getUserType());
                model.addAttribute("userNo",userNo); // 추가

                return "user/user_reply_list";

            }else{return "redirect:/";}
        }
    }



    @GetMapping("adminUserTypeChange") // 유저 등급 변경
    public String userClassChange(HttpSession session, Long userNo){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            if(userService.getUserMaster((UserMaster)session.getAttribute("user")).getUserType().equals("ADMIN")){
                if(userService.getUser(userNo).getUserType().equals("ADMIN")){
                    if(userService.getUser(userNo).getUserId().equals("meow@gmail.com")){
                        return "redirect:/adminUserList";
                    }else{
                        userService.getUser(userNo).setUserType("MEMBER");
                        userRepository.save(userService.getUser(userNo));
                    }
                }else{
                    userService.getUser(userNo).setUserType("ADMIN");
                    userRepository.save(userService.getUser(userNo));
                }
                return "redirect:/adminUserDetail?userNo="+userNo;    // "redirect:/adminUserDetail?userNo="+userNo; / "adminUserList";
            }else{return "redirect:/";}

        }
    }



    @GetMapping("adminPostManage") // 모든 게시글 보기
    public String postManage(HttpSession session, Model model, @PageableDefault(page = 0,size = 10, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster user=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            //model.addAttribute("user",user);
            if(user.getUserType().equals("ADMIN")){
                Page<CommunityMaster> everyPost=communityService.getEveryPost(pageable);

                int nowPage = everyPost.getPageable().getPageNumber()+1 ;
                int startPage = Math.max(0 , 1);
                int endPage = Math.min(nowPage + 10 , everyPost.getTotalPages());

                model.addAttribute("nowPage", nowPage);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
                model.addAttribute("maxPage",10);

                model.addAttribute("postList",everyPost);
                model.addAttribute("userType",user.getUserType());
                //return "admin/admin_post_list"; // 어드민 마이페이지로 이동
                return "user/user_post_list";
            }else{
                return "redirect:/"; // 유저 마이페이지로 이동?
            }
        }
    }

    @GetMapping("adminDonatemanage") // 모든 도네이트 보기
    public String donateManage(HttpSession session, Model model, @PageableDefault(page = 0,size = 10, sort = "donateCode", direction = Sort.Direction.DESC) Pageable pageable){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster user=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            if(user.getUserType().equals("ADMIN")){
                Page<DonateMaster> donateMaster=donateService.donateList(pageable);

                int nowPage = donateMaster.getPageable().getPageNumber()+1 ;
                int startPage = Math.max(0 , 1);
                int endPage = Math.min(nowPage + 10 , donateMaster.getTotalPages());

                model.addAttribute("list",donateMaster);
                model.addAttribute("nowPage", nowPage);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
                model.addAttribute("maxPage",10);

                //추가
                model.addAttribute("userType",user.getUserType());

                return "admin/admin_donate_list";
            }else{
                return "redirect:/";
            }
        }
    }



    @GetMapping("adminInsertNotice")//공지적는 폼으로 이동
    public String insertNoticeForm(HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster userMaster=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            //model.addAttribute("user",userMaster);
            model.addAttribute("adminNo",userMaster.getUserNo());
            return "admin/admin_post_insert";
        }
    }



    @PostMapping("adminInsertNotice")//공지등록
    public String insertNotice(CommunityMaster com, HttpSession session/*, Model model*/){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
            UserMaster userMaster=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            //model.addAttribute("user",userMaster);
            com.setCreatePostDate(LocalDateTime.now());
            communityMasterRepository.save(com);
            return "redirect:adminMypage"; // 새로고침해도 인서트되지않게끔 함니다.
        }
    }
}
