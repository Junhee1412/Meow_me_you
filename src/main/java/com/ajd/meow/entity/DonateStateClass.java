package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//논리명 후원상태
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="DNTN_STATECLS")
public class DonateStateClass {

    @Id
    @Column(name = "DNTN_STATECODE")
    private String donateStateCode;

    @Column(name = "DNTN_STATE")
    private String donateState;
}
