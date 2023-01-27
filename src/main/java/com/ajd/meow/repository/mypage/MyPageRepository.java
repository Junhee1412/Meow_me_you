package com.ajd.meow.repository.mypage;

import com.ajd.meow.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyPageRepository extends JpaRepository<UserMaster, Long> {
}
