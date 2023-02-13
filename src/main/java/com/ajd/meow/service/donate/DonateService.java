package com.ajd.meow.service.donate;

import com.ajd.meow.entity.AccountTransfer;
import com.ajd.meow.entity.BankTransfer;
import com.ajd.meow.entity.CreditcardPayment;
import com.ajd.meow.entity.DonateMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DonateService {

    //후원 등록
    void createDonate(DonateMaster donateMaster);
    void bankTransferDonate(BankTransfer bankTransfer);
    void creditcardDonate(CreditcardPayment creditcardPayment);
    void accountDonate(AccountTransfer accountTransfer);

    //후원등록 시 후원상태
    void updateDonateBankStateCode(DonateMaster donateMaster);
    void updateDonateCreditStateCode(DonateMaster donateMaster);
    void updateDonateAccountStateCode(DonateMaster donateMaster);

    //후원 확정
    void confirmDonate(Long donateCode);
    //후원 취소
    void deleteDonate(Long donateCode);

    //후원페이징
    Page<DonateMaster> donateList(Pageable pageable);

    //후원페이징
    Page<DonateMaster> donateMyList(Pageable pageable, Long userNo);

    //전체 후원목록 보기
    List<DonateMaster> donateList();

    // 특정 후원 기부금영수증(상세보기)
    DonateMaster donateReceipt(Long donateCode);
}
