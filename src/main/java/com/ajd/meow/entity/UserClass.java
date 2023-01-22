package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//논리명 회원타입
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER_CLS")
public class UserClass {

    @Id
    @Column(name="USER_TYPE")
    private String userType;

    @Column(name="USER_DESC")
    private int userDescription;
}
