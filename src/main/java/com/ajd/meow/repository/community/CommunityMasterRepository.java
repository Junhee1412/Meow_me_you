package com.ajd.meow.repository.community;

import com.ajd.meow.entity.CommunityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityMasterRepository extends JpaRepository<CommunityMaster,Long> {




}