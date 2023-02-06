package com.ajd.meow.repository.community;

import com.ajd.meow.entity.CommunityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityMaster, Long> {
    //List<CommunityMaster> findAllById(Long userNo);
}
