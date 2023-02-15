package com.ajd.meow.service.community;

import com.ajd.meow.entity.CommunityImage;
import com.ajd.meow.entity.CommunityLike;
import com.ajd.meow.entity.CommunityMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public interface CommunityService {
    //글 작성
    void write(CommunityMaster communityMaster);
    //파일 저장
    Long saveFile(MultipartFile files, HttpSession session , Model model , CommunityMaster communityMaster) throws IOException;
    //페이징
    Page<CommunityMaster> communityList(Pageable pageable);
    //글 상세보기
    CommunityMaster communityPostView(Long postNo);
    //글 이미지보기
    CommunityImage communityImgView(Long postNo);
    //글 제거
    void communityPostDelete(Long postNo);
    //글 수정
    void communityPostModify(CommunityMaster communityMaster);
    //글 번호로 이미지 찾기
    List<CommunityImage> communityImgFindByPostNo(Long postNo);
    //커뮤니티 검색기능(제목으로)
    Page<CommunityMaster> communitySearchKeyword(String searchKeyword,Pageable pageable);
    //글 삭제
    List<CommunityImage> deleteImg(Long postNo);

    Page<CommunityMaster> boardListByUserNO(Long userId, Pageable pageable);

    CommunityMaster findPost(Long postNo);

    Page<CommunityMaster> getEveryPost(Pageable pageable);

    boolean getInfoAboutClickLike(Long userNo, Long postNo);

    Page<CommunityLike> getAllLikeByUserNo(Long userNo, Pageable pageable);

    void countHeart(Long postNo, Long userNo);

    Long countNumberOfHeart(Long postNo);

    void deleteAllLikeByUserNo(Long userNo);

    void deleteAllLikeByPostNo(Long postNo);

    void deleteAllcomIMG(Long postNo);

}
