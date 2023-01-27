package com.ajd.meow.repository.donate;

import com.ajd.meow.entity.CreditcardPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditcardRepository extends JpaRepository <CreditcardPayment, Long> {
}
