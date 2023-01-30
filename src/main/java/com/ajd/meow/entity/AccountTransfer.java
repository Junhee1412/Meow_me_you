package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//논리명(계좌이체)
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ACNT_TRNSF")
public class AccountTransfer {

    @Id
    @Column(name="DNTN_CODE")
    private Long donateCode;

    @Column(name="USER_NO")
    private Long userNo;

    @Column(name="ACCOUNT_BANK")
    private String accountBank;

    @Column(name="ACCOUNT_NUM")
    private String accountNumber;

    @Column(name="ACNT_PASS")
    private String accountPassword;

}
