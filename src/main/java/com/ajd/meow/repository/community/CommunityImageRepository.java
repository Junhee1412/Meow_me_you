package com.ajd.meow.repository.community;

import com.ajd.meow.entity.CommunityImage;
import com.ajd.meow.entity.CommunityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommunityImageRepository extends JpaRepository<CommunityImage, Long> {

    List<CommunityImage> findByPostNo(Long postNo);

    @Transactional
    List<CommunityImage> deleteByPostNo(Long postNo);

    List<CommunityImage> findAllByPostNo(Long postNo);

    void deleteAllByPostNo(Long postNo);

}
