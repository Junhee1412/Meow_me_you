package com.ajd.meow.repository.user;

import com.ajd.meow.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <UserMaster, Long>{
}
