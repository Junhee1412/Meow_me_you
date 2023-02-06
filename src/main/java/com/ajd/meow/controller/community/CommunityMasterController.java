package com.ajd.meow.controller.community;

import com.ajd.meow.entity.CommunityImage;
import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.CommunityImageRepository;
import com.ajd.meow.service.community.CommunityMasterService;
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
public class CommunityMasterController {

    @Autowired
    private CommunityMasterService communityMasterService;

    @Autowired
    private CommunityImageRepository communityImageRepository;



    @GetMapping("/board/write") //localhost:8080/board/write 작성시 이동
    public String boardWriteForm(HttpSession session, Model model){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);


        return "community/insertBoard";
    }


    @PostMapping("/board/writepro")
    public String boardWritePro(HttpSession session, Model model, CommunityMaster communityMaster, MultipartFile file,CommunityImage communityImage) throws  Exception{

        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);


        communityMasterService.write(communityMaster,communityImage,file);
        return "redirect:/board/list";
    }


    @GetMapping("/board/list")
    public String boardList(@PageableDefault(page = 0,size = 12, sort = "postNo", direction = Sort.Direction.DESC) Pageable pageable, HttpSession session, Model model
    ,CommunityImage communityImage){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);

        Page<CommunityMaster> lists = communityMasterService.boardList(pageable);
        int nowPage = lists.getPageable().getPageNumber()+1 ;
        int startPage = Math.max(0 , 1);

        int endPage = Math.min(nowPage + 10 , lists.getTotalPages());

        model.addAttribute("list", lists);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("maxPage",10);




        return "community/getBoardList";
    }

    @GetMapping("/board/view") //localhost:8080/post/view?postNo=1
    public String boardView(HttpSession session, Model model, Long postNo){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);




        if(communityMasterService.commuImg(postNo) != null ) {
            model.addAttribute("board", communityMasterService.boardView(postNo));
            model.addAttribute("cimg", communityMasterService.commuImg(postNo).getImgPath());

        }else {

            model.addAttribute("board", communityMasterService.boardView(postNo));

        }

//        System.out.println("asdfasdfasdfasdfasdf" +communityMasterService.commuImg(postNo).getImgPath() );
        return "community/getBoard";
    }

    @GetMapping("/board/delete")
    public String boardDelete(HttpSession session, Model model, Long postNo){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);
        communityMasterService.boardDelete(postNo);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{postNo}")
    public String boardModify(@PathVariable("postNo") Long postNo, HttpSession session, Model model){
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);
        model.addAttribute("board", communityMasterService.boardView(postNo));
        return "community/modifyBoard";
    }

    @PostMapping("/board/update/{postNo}")
    public String boardUpdate(@PathVariable("postNo") Long postNo, HttpSession session, CommunityMaster communityMaster , Model model, MultipartFile file , CommunityImage communityImage) throws Exception {
        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);
        CommunityMaster boardTemp = communityMasterService.boardView(postNo);
        boardTemp.setSubject(communityMaster.getSubject());
        boardTemp.setContent(communityMaster.getContent());

        model.addAttribute("message", "글 수정 완료.");
        model.addAttribute("SearchUrl", "/board/list");

        communityMasterService.write(boardTemp,communityImage,file);

        return "community/boardMessage";
  }

    }



