package com.ajd.meow.controller.community;

import com.ajd.meow.entity.CommunityImage;
import com.ajd.meow.entity.CommunityLike;
import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.CommunityImageRepository;
import com.ajd.meow.repository.community.CommunityMasterRepository;
import com.ajd.meow.service.community.CommunityService;
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

import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private CommunityMasterRepository communityMasterRepository;

    @Autowired
    private CommunityImageRepository communityImageRepository;

    @Autowired
    private UserService userService;


    @GetMapping("/boardwrite") //localhost:8080/boardwrite 작성시 이동
    public String boardWriteForm(HttpSession session, Model model) {
        UserMaster loginUser = (UserMaster) session.getAttribute("user");
        model.addAttribute("user", loginUser);

        return "community/post_insert";
    }


    @PostMapping("/boardwritepro")
    public String boardWritePro(HttpSession session, Model model, CommunityMaster communityMaster,
                                @RequestParam("files") List<MultipartFile> files) throws Exception {

        UserMaster loginUser = (UserMaster) session.getAttribute("user");
        model.addAttribute("user", loginUser);

        communityService.write(communityMaster);

        for (MultipartFile img : files) {
            communityMaster.setPostNo(communityMaster.getPostNo());
            communityMaster.setUserNo(loginUser.getUserNo());

            communityService.saveFile(img, session, model, communityMaster);
        }
            model.addAttribute("message", "글 작성 완료.");
            model.addAttribute("SearchUrl", "/boardlist");
//        return "redirect:/boardlist";
        return "community/community_message";
    }

//    @GetMapping("/boardlist")
//    public String communityList(@PageableDefault(page = 0, size = 12, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable, HttpSession session, Model model,
//                            String id, CommunityMaster communityMaster, String searchKeyword) {
//
//        UserMaster loginUser = (UserMaster) session.getAttribute("user");
//        model.addAttribute("user", loginUser);
//
//
//        if(id=="ADP_ACT"){
//            communityService.communityList(pageable);
//        }
//        //검색
//        Page<CommunityMaster> lists = null;
//
//            if(searchKeyword == null ) {
//                 lists = communityService.communityList(pageable);
//            }else {
//                lists = communityService.communitySearchKeyword(searchKeyword,pageable);
//            }
//
//        int nowPage = lists.getPageable().getPageNumber() + 1;
//        int startPage = Math.max(0, 1);
//        int endPage = Math.min(nowPage + 10, lists.getTotalPages());
//
//        model.addAttribute("list", lists);
//        model.addAttribute("nowPage", nowPage);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//        model.addAttribute("maxPage", 10);
//
//        return "community/post_list";
//    }
    @GetMapping("boardlist") // 내가 해보고 잇는거 230223 추가
    public String communityList(String id, @PageableDefault(page = 0, size = 12, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable, HttpSession session, Model model, String searchKeyword){
        if(session.getAttribute("user")==null){
            return "redirect:/";
        }else{
        UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
        model.addAttribute("user", loginUser);

        //검색
        Page<CommunityMaster> lists = null;

        if(searchKeyword == null ) {
            lists = communityMasterRepository.findAllByCommunityId(id, pageable);
        }else {
            lists = communityService.communitySearchKeyword(searchKeyword,pageable); // 수정해야함.
        } // 검색 끝?

        int nowPage = lists.getPageable().getPageNumber() + 1;
        int startPage = Math.max(0, 1);
        int endPage = Math.min(nowPage + 10, lists.getTotalPages());

        model.addAttribute("list", lists);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("maxPage", 10);
        model.addAttribute("comid", id);

        return "community/post_list";

    }
}

    @GetMapping("/boardview") //localhost:8080/post/view?postNo=1
    public String communityPostView(HttpSession session, Model model, Long postNo, CommunityImage communityImage) {
        UserMaster loginUser = (UserMaster) session.getAttribute("user");
        model.addAttribute("user", loginUser);

        List<CommunityImage> filess = communityImageRepository.findByPostNo(postNo);

        if (communityService.communityImgFindByPostNo(postNo) != null) {
            model.addAttribute("board", communityService.communityPostView(postNo));
            model.addAttribute("cimg", filess);
        } else {
            model.addAttribute("board", communityService.communityPostView(postNo));
        }
        model.addAttribute("numberOfHeart",communityService.countNumberOfHeart(postNo));

        if(communityService.getInfoAboutClickLike(loginUser.getUserNo(), postNo)){
            System.out.println("색칠하트");
            model.addAttribute("clickHeart",true);
        }else{
            System.out.println("빈하트");
            model.addAttribute("clickHeart",false);
        }
        return "community/post_view";
    }

    @GetMapping("/boarddelete")
    public String communityPostDelete(HttpSession session, Model model, Long postNo) {
        UserMaster loginUser = (UserMaster) session.getAttribute("user");
        model.addAttribute("user", loginUser);

        communityService.communityPostDelete(postNo);
        return "redirect:/boardlist";
    }

    @GetMapping("/boardmodify/{postNo}")
    public String boardModify(@PathVariable("postNo") Long postNo, HttpSession session, Model model,CommunityImage communityImage) {
        UserMaster loginUser = (UserMaster) session.getAttribute("user");
        model.addAttribute("user", loginUser);

        model.addAttribute("board", communityService.communityPostView(postNo));
        model.addAttribute("orgName", communityService.communityImgFindByPostNo(postNo));
            return "community/post_modify";
    }

    @PostMapping("/boardupdate/{postNo}")
    public String communityPostModify(@PathVariable("postNo") Long postNo, HttpSession session, CommunityMaster communityMaster, Model model, @RequestParam("files") List<MultipartFile> files,CommunityImage communityImage) throws Exception {
        UserMaster loginUser = (UserMaster) session.getAttribute("user");

        model.addAttribute("user", loginUser);
        CommunityMaster boardTemp = communityService.communityPostView(postNo);

        int count = 0;
        //파일 업로드 반복문
        for (MultipartFile img : files) {
            if (img.isEmpty()){
            }else {
                // 게시글 수정시 이미지 첨부가 있으면 기존에 있는 이미지 지우기위함.
                if (count == 0) {
                    communityService.deleteImg(postNo);
                }
                communityService.saveFile(img, session, model, communityMaster);
                count++;
                boardTemp.setSumImg(communityService.communityImgFindByPostNo(postNo).get(0).getImgPath());
            }
        }

        boardTemp.setSubject(communityMaster.getSubject());
        boardTemp.setContent(communityMaster.getContent());

        communityService.communityPostModify(boardTemp);

        // 글 작성 완료 안내문
        model.addAttribute("message", "글 수정 완료.");
        model.addAttribute("SearchUrl", "/boardlist");

        return "community/community_message";
    }


    // 주희 추가
    @GetMapping("countHeart")
    public String countHeart(HttpSession session, Long postNo, Model model, CommunityLike communityLike){
        if(session.getAttribute("user")==null){
            return "rediect:/";
        }
        else{
            UserMaster loginUser=userService.getUserMaster((UserMaster)session.getAttribute("user"));
            communityService.countHeart(postNo, loginUser.getUserNo());
            return "redirect:/boardview?postNo="+postNo;
        }
    }
    // 주희 추가 끝
}