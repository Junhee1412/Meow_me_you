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
    private Long donateCode;

    @Column(name="USER_NO")
    private Long userNo;

    @Column(name = "CARD_COM")
    private String cardCompany;

    @Column(name = "CARD_NUM")
    private String cardNumber;

    @Column(name = "CVC")
    private String cvc;

    @Column(name = "VLDT_PRD")
    private String validityPeriod;

    @Column(name = "CRCRD_PASS")
    private String creditcardPassword;

}
