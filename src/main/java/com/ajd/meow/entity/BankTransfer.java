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
    private Long donateCode;

    @Column(name="USER_NO")
    private Long userNo;

    @Column(name="COM_BANK_CODE")
    private String bank;
}
