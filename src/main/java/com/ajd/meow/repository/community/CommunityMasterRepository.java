package com.ajd.meow.repository.community;

import java.util.List;
import com.ajd.meow.entity.CommunityImage;
import com.ajd.meow.entity.CommunityMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface CommunityMasterRepository extends JpaRepository<CommunityMaster,Long> {

    Page<CommunityMaster> findBySubjectContaining(String searchKeyword, Pageable pageable);
}