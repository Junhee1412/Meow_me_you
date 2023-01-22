package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//논리명 후원사업
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="DNTN_BSNS_MSTR")
public class DonateBusinessMaster {

    @Id
    @Column(name = "DNTN_BSNS_CODE")
    private String donateBusinessCode;

    @Column(name = "DNTN_BSNS")
    private String donateBusiness;
}
