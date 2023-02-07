package com.ajd.meow.repository.community;

import java.util.List;
import com.ajd.meow.entity.CommunityImage;
import com.ajd.meow.entity.CommunityMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CommunityMasterRepository extends JpaRepository<CommunityMaster,Long> {
    Page<CommunityMaster> findAllByUserNo(Long userNo, Pageable pageable); // 유저넘버로 게시글 찾기 - 페이징?
    List<CommunityMaster> findAllByUserNo(Long userNo); // 유저넘버로 게시글 찾기
}