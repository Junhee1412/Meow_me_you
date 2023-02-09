package com.ajd.meow.service.user;

import com.ajd.meow.entity.CommunityMaster;
import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.community.*;
import com.ajd.meow.repository.donate.DonateRepository;
import com.ajd.meow.repository.user.UserRepository;
import com.ajd.meow.service.donate.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Collections;
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
    SecondHandTradeRepository secondHandTradeRepository;
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
        file.transferTo(saveFile);

        Optional<UserMaster> useruser=userRepository.findByNickName(user.getNickName());
        useruser.get().setNickName(user.getNickName());
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
    @Transactional // 이거 안넣어도 되나?
    public void deleteMember(UserMaster user) {
        // 도네이트 지우기
        if(donateRepository.existsByUserNo(user.getUserNo())){
            for(DonateMaster donate:donateRepository.findAllByUserNo(user.getUserNo())){
                donateService.deleteDonate(donate.getDonateCode());
            }
        }
        // 커뮤니티 지우기
        if(communityMasterRepository.existsByUserNo(user.getUserNo())){
            List<CommunityMaster> communityMaster=communityMasterRepository.findAllById(Collections.singleton(user.getUserNo()));
            for(CommunityMaster com:communityMaster){

                // 좋아요 삭제
                if(communityLikeRepository.existsById(com.getPostNo())){
                    communityLikeRepository.deleteAllById(Collections.singleton(com.getPostNo()));
                }
                // 이미지 삭제
                if(communityImageRepository.existsById(com.getPostNo())){
                    communityImageRepository.deleteAllByPostNo(com.getPostNo());
                }
                // 덧글 삭제
                if(replyRepository.existsByPostNo(com.getPostNo())){
                    replyRepository.deleteAllByPostNo(com.getPostNo());
                }
                // 중고거래 삭제
                if(com.getCommunityId().equals("USD_TRN")){
                    secondHandTradeRepository.deleteById(com.getPostNo());
                }

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
