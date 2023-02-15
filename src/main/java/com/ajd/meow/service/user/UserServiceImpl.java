package com.ajd.meow.service.user;

import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.*;
import com.ajd.meow.repository.donate.DonateRepository;
import com.ajd.meow.repository.user.UserRepository;
import com.ajd.meow.service.donate.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommunityLikeRepository communityLikeRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private CommunityImageRepository communityImageRepository;
    @Autowired
    private CommunityMasterRepository communityMasterRepository;
    @Autowired
    private SecondHandTradeRepository secondHandTradeRepository;
    @Autowired
    private DonateRepository donateRepository;
    @Autowired
    private DonateService donateService;

    @Override
    public void insertMember(UserMaster user, MultipartFile file) throws Exception {
        //  파일 경로 생성
        String imgPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(imgPath, fileName);

        file.transferTo(saveFile);

        user.setProfileImageName(fileName);
        user.setProfileImagePath("/files/" + fileName);

        user.setUserJoinDate(LocalDateTime.now());
        user.setUserType("MEMBER");
        userRepository.save(user);
    }

    @Override
    public void updateMember(UserMaster user, MultipartFile file) throws Exception{
        String imgPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(imgPath, fileName);

        user.setProfileImageName(fileName);
        user.setProfileImagePath("/files/" + fileName);

        file.transferTo(saveFile);

        Optional<UserMaster> useruser=userRepository.findByUserId(user.getUserId());
        useruser.get().setAddress(user.getAddress());
        useruser.get().setPhoneType(user.getPhoneType());
        useruser.get().setPhoneNumber(user.getPhoneNumber());
        useruser.get().setUserPassword(user.getUserPassword());
        useruser.get().setIntroduce(user.getIntroduce());
        useruser.get().setProfileImageName(fileName);
        useruser.get().setProfileImagePath("/files/" + fileName);
        userRepository.save(useruser.get());
    }

    @Override
    public void updateMemberPassword(UserMaster user){
        Optional<UserMaster> useruser=userRepository.findByUserId(user.getUserId());
        useruser.get().setUserPassword(user.getUserPassword());
        userRepository.save(useruser.get());
    }


    @Override
    public void updateNickName(Long userNo, String nickName){ // 유저 닉네임 업데이트
        UserMaster user=userRepository.findById(userNo).get();
        user.setNickName(nickName);
        userRepository.save(user);
    }




    @Override
    @Modifying
    @Transactional
    public void deleteMember(UserMaster user) {
        // 도네이트 지우기
        if(donateRepository.existsByUserNo(user.getUserNo())){
            for(DonateMaster donate:donateRepository.findAllByUserNo(user.getUserNo())){
                donateService.deleteDonate(donate.getDonateCode());
            }
        }
        // 유저의 좋아요 지우기
        if(!communityLikeRepository.findAllByUserNo(user.getUserNo()).isEmpty()/*existsByUserNo(user.getUserNo())*/){
            //communityLikeRepository.deleteAllByUserNo(user.getUserNo());
            communityLikeRepository.deleteLikesByUser(user.getUserNo());
        }
        // 유저의 덧글 지우기
        if(!replyRepository.findAllByUserNo(user.getUserNo()).isEmpty()){
            replyRepository.deleteAllByUserNo(user.getUserNo());
        }
        // 유저의 커뮤니티 지우기
        if(!communityMasterRepository.findAllByUserNo(user.getUserNo()).isEmpty()/*existsByUserNo(user.getUserNo())*/){
            List<CommunityMaster> communityMaster=communityMasterRepository.findAllByUserNo(user.getUserNo());
            for(CommunityMaster com:communityMaster){
                // 게시글의 좋아요 삭제
                if(!communityLikeRepository.findAllByPostNo(com.getPostNo()).isEmpty()/*existsByPostNo(com.getPostNo())*/){
                    //communityLikeRepository.deleteAllByPostNo(com.getPostNo());
                    communityLikeRepository.deleteLikesByPost(com.getPostNo());
                }
                // 게시글의 이미지 삭제
                if(!communityImageRepository.findAllByPostNo(com.getPostNo()).isEmpty()/*existsById(com.getPostNo())*/){
                    communityImageRepository.deleteAllByPostNo(com.getPostNo());
                }
                // 게시글의 덧글 삭제
                if(!replyRepository.findAllByPostNo(com.getPostNo()).isEmpty()/*existsByPostNo(com.getPostNo())*/){
                    replyRepository.deleteAllByPostNo(com.getPostNo());
                }
                // 게시글 중 중고거래 삭제
                if(com.getCommunityId().equals("USD_TRN")){
                    secondHandTradeRepository.deleteByPostNo(com.getPostNo());
                }
                communityMasterRepository.delete(com);
            } // for문 end
        } // 커뮤니티 지우기 end
        // 유저 지우기
        userRepository.deleteById(user.getUserNo());
    }


    @Override
    public long getTotalRowCount() {
        return userRepository.count();
    }

    @Override
    public UserMaster getUserMaster(UserMaster user) {
        //Optional<UserMaster> findUser=userRepository.findById(user.getUserNo());
        Optional<UserMaster> findUser=userRepository.findByUserId(user.getUserId());
        /*if(findUser.isPresent()){return findUser.get();}
        else {return null;}*/
        if(findUser.isPresent()){return findUser.get();}
        else {return null;}
    }
    @Override
    public UserMaster getUser(Long userNo){
        return userRepository.findById(userNo).get();
    }
}
