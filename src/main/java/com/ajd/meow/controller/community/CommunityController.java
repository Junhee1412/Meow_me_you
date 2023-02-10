package com.ajd.meow.controller.community;

import com.ajd.meow.entity.CommunityImage;
import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.CommunityImageRepository;
import com.ajd.meow.service.community.CommunityService;
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
    private CommunityImageRepository communityImageRepository;


    @GetMapping("/board/write") //localhost:8080/board/write 작성시 이동
    public String boardWriteForm(HttpSession session, Model model) {
        UserMaster loginUser = (UserMaster) session.getAttribute("user");
        model.addAttribute("user", loginUser);

        return "community/insert_post";
    }


    @PostMapping("/board/writepro")
    public String boardWritePro(HttpSession session, Model model, CommunityMaster communityMaster,
                                @RequestParam("files") List<MultipartFile> files) throws Exception {

        UserMaster loginUser = (UserMaster) session.getAttribute("user");
        model.addAttribute("user", loginUser);

        communityService.write(communityMaster);

        for (MultipartFile ddd : files) {
            communityMaster.setPostNo(communityMaster.getPostNo());
            communityMaster.setUserNo(loginUser.getUserNo());

            communityService.saveFile(ddd, session, model, communityMaster);
        }
            model.addAttribute("message", "글 작성 완료.");
            model.addAttribute("SearchUrl", "/board/list");
//        return "redirect:/board/list";
        return "community/community_message";
    }

    @GetMapping("/board/list")
    public String communityList(@PageableDefault(page = 0, size = 12, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable, HttpSession session, Model model,
                            CommunityMaster communityMaster, String searchKeyword) {

        UserMaster loginUser = (UserMaster) session.getAttribute("user");
        model.addAttribute("user", loginUser);

        //검색
        Page<CommunityMaster> lists = null;

            if(searchKeyword == null ) {
                 lists = communityService.communityList(pageable);
            }else {
                lists = communityService.communitySearchKeyword(searchKeyword,pageable);
            }

        int nowPage = lists.getPageable().getPageNumber() + 1;
        int startPage = Math.max(0, 1);

        int endPage = Math.min(nowPage + 10, lists.getTotalPages());

        model.addAttribute("list", lists);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("maxPage", 10);

        return "community/post_list";
    }

    @GetMapping("/board/view") //localhost:8080/post/view?postNo=1
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
        return "community/post_view";
    }

    @GetMapping("/board/delete")
    public String communityPostDelete(HttpSession session, Model model, Long postNo) {
        UserMaster loginUser = (UserMaster) session.getAttribute("user");
        model.addAttribute("user", loginUser);

        communityService.communityPostDelete(postNo);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{postNo}")
    public String boardModify(@PathVariable("postNo") Long postNo, HttpSession session, Model model,CommunityImage communityImage) {
        UserMaster loginUser = (UserMaster) session.getAttribute("user");
        model.addAttribute("user", loginUser);

        model.addAttribute("board", communityService.communityPostView(postNo));
        model.addAttribute("cimg", communityService.communityImgView(postNo));

            return "community/post_modify";
    }

    @PostMapping("/board/update/{postNo}")
    public String communityPostModify(@PathVariable("postNo") Long postNo, HttpSession session, CommunityMaster communityMaster, Model model, @RequestParam("files") List<MultipartFile> files,CommunityImage communityImage) throws Exception {
        UserMaster loginUser = (UserMaster) session.getAttribute("user");

        model.addAttribute("user", loginUser);
        CommunityMaster boardTemp = communityService.communityPostView(postNo);

        // 수정버튼 클릭시 기존 이미지 제거 , 추후 이미지 파일 불러오기 작업
        communityService.deleteImg(postNo);
        communityMaster.setSumImg(null);

        //파일업로드 반복문
        for (MultipartFile ddd : files) {
            communityService.saveFile(ddd, session, model, communityMaster);
        }

        boardTemp.setSubject(communityMaster.getSubject());
        boardTemp.setContent(communityMaster.getContent());
//      boardTemp.setSumImg();

        communityService.communityPostModify(boardTemp);

        // 글 작성 완료 안내문
        model.addAttribute("message", "글 수정 완료.");
        model.addAttribute("SearchUrl", "/board/list");

        return "community/community_message";
    }
}