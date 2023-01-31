package com.ajd.meow.repository.user;

import com.ajd.meow.entity.UserMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserMaster, Long> {
    //Page<UserMaster> findByIdContaining(Long id, Pageable pageable);
    // String id? Long id? // 얘 잇으면 프로젝트 실행안됨.
    Page<UserMaster> UserNameContaining(String name, Pageable pageable);
    Optional<UserMaster> findByUserId(String userId);
    Optional<UserMaster> findByUserName(String userName);

    @Override
    Optional<UserMaster> findById(Long userNo);
}
