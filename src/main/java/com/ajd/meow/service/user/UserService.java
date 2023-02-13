package com.ajd.meow.service.user;

import com.ajd.meow.entity.UserMaster;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void insertMember(UserMaster user, MultipartFile file) throws Exception;// 유저 생성

    void updateMember(UserMaster user, MultipartFile file) throws Exception;// 유저 수정

    void updateMemberPassword(UserMaster user);

    void deleteMember(UserMaster user);// 유저 삭제

    long getTotalRowCount(/*UserMaster user*/); //  유저 회원수 조회

    UserMaster getUserMaster(UserMaster user); // 유저 정보 가져오기1

    UserMaster getUser(Long userNo); // 유저 정보 가져오기2

    void updateNickName(Long userNo, String nickName); // 유저의 닉네임 변경

    //페이징
}
