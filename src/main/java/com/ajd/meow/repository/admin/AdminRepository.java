package com.ajd.meow.repository.admin;

import com.ajd.meow.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<UserMaster, Long> {
}
