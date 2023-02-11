package com.ajd.meow.repository.community;

import com.ajd.meow.entity.DonateMaster;
import com.ajd.meow.entity.SecondHandTrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecondHandTradeRepository extends JpaRepository<SecondHandTrade, Long> {
    void deleteByPostNo(Long postNo);
}
