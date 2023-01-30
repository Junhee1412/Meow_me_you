package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//논리명 계좌코드
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="BANK_CODE")
public class BankTrasferCode {

    @Id
    @Column(name="COM_BANK_CODE")
    private String bank;

    @Column(name="COM_ACCOUNT")
    private String companyAccount;
}
