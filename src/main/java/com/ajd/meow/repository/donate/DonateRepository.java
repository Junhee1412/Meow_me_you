package com.ajd.meow.repository.donate;

import com.ajd.meow.entity.DonateMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonateRepository extends JpaRepository<DonateMaster, Long> {
    List<DonateMaster> findByUserNo(Long UserNo);
}
