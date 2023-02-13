package com.ajd.meow.repository.donate;

import com.ajd.meow.entity.DonateMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonateRepository extends JpaRepository<DonateMaster, Long> {
        //test용도
        List<DonateMaster> findByUserNo(Long UserNo);

        List<DonateMaster> findAllByUserNo(Long userNo);
        Page<DonateMaster> findAllByUserNo(Pageable pageable, Long usrNo);
        boolean existsByUserNo(Long userNo);
        void deleteAllByUserNo(Long userNo);
}
