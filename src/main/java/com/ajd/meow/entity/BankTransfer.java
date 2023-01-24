package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//논리명(무통장입금)
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="BANK_TRNSF")
public class BankTransfer {

    @Id
    @Column(name="DNTN_CODE")
    private Long DonateCode;

    @Column(name="USER_NO")
    private Long userNo;

    @Column(name="BANK")
    private String bank;

    @Column(name="ACCOUNT")
    private String account;
}
