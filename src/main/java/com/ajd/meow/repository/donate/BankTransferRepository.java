package com.ajd.meow.repository.donate;

import com.ajd.meow.entity.BankTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransferRepository extends JpaRepository<BankTransfer, Long> {
}
