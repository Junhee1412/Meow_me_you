package com.ajd.meow.service.user;

import com.ajd.meow.entity.UserMaster;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void insertMember(UserMaster user, MultipartFile file) throws Exception;
    void updateMember(UserMaster user, MultipartFile file) throws Exception;
    void updateMemberPassword(UserMaster user);
    void deleteMember(UserMaster user);

    long getTotalRowCount(/*UserMaster user*/); //  유저 회원수 조회
    UserMaster getUserMaster(UserMaster user); // 유저 정보 가져오기

    //페이징
}
