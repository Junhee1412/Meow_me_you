package com.ajd.meow.repository.user;

import com.ajd.meow.entity.UserMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserMaster, Long> {
    //Page<UserMaster> findByIdContaining(Long id, Pageable pageable);
    // String id? Long id? // 얘 잇으면 프로젝트 실행안됨.
    Page<UserMaster> UserNameContaining(String name, Pageable pageable);
    Optional<UserMaster> findByUserId(String userId);
    Optional<UserMaster> findByUserName(String userName);
    Optional<UserMaster> findByNickName(String nickName);

    List<UserMaster> findAllByUserName(String userName);


    // 중복체크
    boolean existsByUserId(String userId); // 아이디 존재여부
    boolean existsByNickName(String nickName); // 닉네임 존재여부
    boolean existsByPhoneNumber(String phoneNumber); // 핸드폰 존재여부
    boolean existsByUserName(String userName); // 이름 존재여부

    @Override
    Optional<UserMaster> findById(Long userNo);
}
