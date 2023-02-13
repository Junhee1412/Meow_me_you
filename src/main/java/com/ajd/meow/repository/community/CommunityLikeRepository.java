package com.ajd.meow.repository.community;

import com.ajd.meow.entity.CommunityLike;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityLikeRepository extends JpaRepository<CommunityLike, Long> {

    Page<CommunityLike> findAllByUserNo(Long userNo, Pageable pageable);
    List<CommunityLike> findAllByUserNo(Long userNo);

    void deleteAllByUserNo(Long userNo);
    boolean existsByUserNo(Long userNo);
    boolean existsByPostNo(Long userNo);
    boolean existsByUserNoAndPostNo(Long userNo,Long postNo);

    long countByPostNo(Long postNo);

    void deleteAllByPostNo(Long postNo);


    // 하트 채우기
    @Transactional
    @Modifying
    @Query(value = "insert into CMNTY_LIKE (POST_NO, USER_NO) values (:postNo,:userNo)", nativeQuery = true)
    void insertHeart(@Param("postNo")Long postNo, @Param("userNo")Long userNo);

    // 하트 버리기
    @Transactional
    @Modifying
    @Query(value = "delete from CMNTY_LIKE where POST_NO=:postNo and USER_NO=:userNo", nativeQuery = true)
    void deleteHeart(@Param("postNo")Long postNo, @Param("userNo")Long userNo);

    // 유저번호로 좋아요 지우기
    @Transactional
    @Modifying
    @Query(value = "delete from CMNTY_LIKE where USER_NO=:userNo", nativeQuery = true)
    void deleteLikesByUser(@Param("userNo")Long userNo);

    // 포스트번호로 좋아요 지우기
    @Transactional
    @Modifying
    @Query(value = "delete from CMNTY_LIKE where POST_NO=:postNo", nativeQuery = true)
    void deleteLikesByPost(@Param("postNo")Long postNo);

    //유저번호로 좋아요 찾기 - 페이징
    @Transactional
    //@Modifying
    @Query(value = "select * from CMNTY_LIKE where USER_NO=:userNo order by POST_NO DESC", nativeQuery = true) // order by POST_NO
    Page<CommunityLike> selectLikesByUser(@Param("userNo")Long userNo, Pageable pageable);


    //유저번호로 좋아요 찾기 - 페이징
    /*@Transactional
    //@Modifying
    @Query(value = "select * from CMNTY_LIKE where USER_NO=:userNo order by POST_NO", nativeQuery = true)
    List<CommunityLike> selectLikesByUser(@Param("userNo")Long userNo);*/



    List<CommunityLike> findAllByPostNo(Long postNo);


}
