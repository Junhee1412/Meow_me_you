package com.ajd.meow.repository.community;

import com.ajd.meow.entity.CommunityImage;
import com.ajd.meow.entity.CommunityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityImageRepository extends JpaRepository<CommunityImage, Long> {

    CommunityImage findByPostNo(Long postNo);



}
