package com.ajd.meow.repository.community;

import com.ajd.meow.entity.CommunityLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityLikeRepository extends JpaRepository<CommunityLike, Long> {
}
