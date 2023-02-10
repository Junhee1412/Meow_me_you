package com.ajd.meow.service.community;


import com.ajd.meow.entity.CommunityImage;
import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.CommunityImageRepository;
import com.ajd.meow.repository.community.CommunityMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService{

    @Autowired
    private CommunityMasterRepository communityMasterRepository;

    @Autowired
    private CommunityImageRepository communityImageRepository;


    //글 작성
    public void write(CommunityMaster communityMaster){

        communityMaster.setCommunityId("ADP_ACT");
        communityMaster.setPostId("FOOD_SELL");
        communityMaster.setCreatePostDate(LocalDateTime.now());

        communityMasterRepository.save(communityMaster);
    }

    //파일 업로드
    public Long saveFile(MultipartFile files, HttpSession session , Model model , CommunityMaster communityMaster) throws IOException {

        UserMaster loginUser=(UserMaster)session.getAttribute("user");
        model.addAttribute("user",loginUser);
        //파일이 없을경우 리턴 null, 있을 경우  아래 작업 진행
        if (files.isEmpty()) {
            return null;
        }
        //프로젝트 경로
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        //uuid 랜덤
        UUID uuid = UUID.randomUUID();
        //파일 랜덤이름지정
        String fileName = uuid + "_" + files.getOriginalFilename();
        //파일 오리지널 이름
        String origName = files.getOriginalFilename();

        File saveImg = new File(projectPath,fileName);
        //빌더
        CommunityImage file = CommunityImage.builder()
                .imageOrgName(origName)
                .imgName(fileName)
                .imgPath("/files/"+fileName)
                .userNo(loginUser.getUserNo())
                .postNo(communityMaster.getPostNo())
                .build();

        files.transferTo(saveImg);
        communityMaster.setSumImg(file.getImgName());
        CommunityImage savedFile = communityImageRepository.save(file);

        System.out.println("asdfasfasdf" + communityMaster.getSumImg());
        return savedFile.getImageNo();
    }

    // 게시글 리스트
    public Page<CommunityMaster> communityList(Pageable pageable){ return communityMasterRepository.findAll(pageable); }

    //특정 게시글 불러오기
    public CommunityMaster communityPostView(Long postNo){return communityMasterRepository.findById(postNo).get();
    }

    public CommunityImage communityImgView(Long postNo){return communityImageRepository.findById(postNo).get();
    }

    //게시글 삭제 하기
    public void communityPostDelete(Long postNo) {
        communityMasterRepository.deleteById(postNo);
    }

    //게시글 수정
    public void communityPostModify(CommunityMaster communityMaster){

        communityMasterRepository.save(communityMaster);
    }

    public List<CommunityImage> communityImgFindByPostNo(Long postNo){return communityImageRepository.findByPostNo(postNo);}


    public Page<CommunityMaster> communitySearchKeyword(String searchKeyword,Pageable pageable){

        return communityMasterRepository.findBySubjectContaining(searchKeyword, pageable);
    }

    public List<CommunityImage> deleteImg(Long postNo) { return communityImageRepository.deleteByPostNo(postNo);
    }
}



