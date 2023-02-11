package com.ajd.meow.service.community;


import com.ajd.meow.entity.CommunityImage;
import com.ajd.meow.entity.CommunityLike;
import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.repository.community.CommunityImageRepository;
import com.ajd.meow.repository.community.CommunityLikeRepository;
import com.ajd.meow.repository.community.CommunityMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommunityMasterService {

    @Autowired
    private CommunityMasterRepository communityMasterRepository;

    @Autowired
    private CommunityImageRepository communityImageRepository;

    @Autowired
    private CommunityLikeRepository communityLikeRepository; // 주희 추가


    //글 작성
    public void write(CommunityMaster communityMaster,CommunityImage communityImage, MultipartFile file) throws Exception{

        System.out.println("111111111111111111" + file.getOriginalFilename());

        if(file.getOriginalFilename().isEmpty()){
            communityMaster.setCommunityId("ADP_ACT");
            communityMaster.setPostId("FOOD_SELL");
            communityMaster.setCreatePostDate(LocalDateTime.now());


            communityMasterRepository.save(communityMaster);


        }else {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

            UUID uuid = UUID.randomUUID();

            String fileName = uuid + "_" + file.getOriginalFilename();

            File saveImg = new File(projectPath,fileName);
            file.transferTo(saveImg);

            communityImage.setImgName(fileName);
            communityImage.setImgPath("/files/" + fileName);


            communityMaster.setCommunityId("ADP_ACT");
            communityMaster.setPostId("FOOD_SELL");
            communityMaster.setCreatePostDate(LocalDateTime.now());

            communityMasterRepository.save(communityMaster);
            communityImage.setPostNo(communityMaster.getPostNo());
            communityImageRepository.save(communityImage);

        }
        }



    // 유저번호로 게시글찾기
    public Page<CommunityMaster> boardListByUserNO(Long userId, Pageable pageable){
        return communityMasterRepository.findAllByUserNo(userId, pageable);
    }
    // 포스트번호로 게시글찾기
    public CommunityMaster findPost(Long postNo){
        return communityMasterRepository.findByPostNo(postNo);
    }
    public Page<CommunityMaster> getEveryPost(Pageable pageable){
        return communityMasterRepository.findAll(pageable);
    }

    // 게시글 리스트
    public Page<CommunityMaster> boardList(Pageable pageable){return communityMasterRepository.findAll(pageable);}

    //특정 게시글 불러오기
    public CommunityMaster boardView(Long postNo){return communityMasterRepository.findById(postNo).get();
    }
    //게시글 삭제 하기
    public void boardDelete(Long postNo) {
        communityMasterRepository.deleteById(postNo);
    }

    //게시글 수정
    public void boardUpdate(CommunityMaster communityMaster){

        communityMaster.setCommunityId("ADP_ACT");
        communityMaster.setPostId("FOOD_SELL");
        communityMasterRepository.save(communityMaster);
    }

    public CommunityImage commuImg(Long postNo){return communityImageRepository.findByPostNo(postNo);}





    // 주희  추가
    public Page<CommunityLike> getAllLikeByUserNo(Long userNo, Pageable pageable){
        return communityLikeRepository.findAllByUserNo(userNo,pageable);
    }


    public void countHeart(Long postNo, Long userNo){
        if(communityLikeRepository.existsByUserNoAndPostNo(userNo,postNo)){
            communityLikeRepository.deleteHeart(postNo, userNo);
        }else{
            communityLikeRepository.insertHeart(postNo,userNo);
        }
    }

    public Long countNumberOfHeart(Long postNo){
        return communityLikeRepository.countByPostNo(postNo);
    }

    /*@Transactional
    @Modifying
    @Query(value = "insert into CommunityLike (postNo, userNo) values (:postNo,:userNo))", nativeQuery = true)
    public void insertHeart(@Param("postNo")Long postNo, @Param("userNo")Long userNo){
    }*/
    // 주희 추가 끝


    // 좋아요 삭제 - 유저이름으로 삭제
    public void deleteAllLikeByUserNo(Long userNo){
        if(communityLikeRepository.findAllByUserNo(userNo).isEmpty()){
        }else{
            communityLikeRepository.deleteAllByUserNo(userNo);
        }
    }
    // 좋아요 삭제 - 포스트 번호로 삭제
    public void deleteAllLikeByPostNo(Long postNo){
        if(communityLikeRepository.findAllByPostNo(postNo).isEmpty()){
        }else{
            communityLikeRepository.deleteAllByPostNo(postNo);
        }
    }

    // 커뮤니티 이미지 삭제
    public void deleteAllcomIMG(Long postNo){
        if(communityImageRepository.findAllByPostNo(postNo).isEmpty()){
        }else{
            communityImageRepository.deleteAllByPostNo(postNo);
        }
    }
}
