package com.ajd.meow.repository.donate;

import com.ajd.meow.entity.AccountTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountTransfer, Long> {
}
