package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


//논리명 신용카드
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CRCRD_PYMNT")
public class CreditcardPayment {

    @Id
    @Column(name = "DNTN_CODE")
    private Long DonateCode;

    @Column(name="USER_NO")
    private Long userNo;

    @Column(name = "BANK")
    private String bank;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "CVC")
    private int cvc;

    @Column(name = "VLDT_PRD")
    private int validityPeriod;

    @Column(name = "CRCRD_PASS")
    private int creditcardPassword;

}
