package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

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
    private String donateCode;

    @Column(name="USER_NO")
    private UUID userNo;

    @Column(name="BANK")
    private String bank;

    @Column(name="ACCOUNT")
    private String account;

    @Column(name="ACNT_PASS")
    private int accountPassword;

}
