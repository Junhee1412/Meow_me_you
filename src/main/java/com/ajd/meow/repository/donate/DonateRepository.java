package com.ajd.meow.repository.donate;

import com.ajd.meow.entity.DonateMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonateRepository extends JpaRepository<DonateMaster, String> {

}
