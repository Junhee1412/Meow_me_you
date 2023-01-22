package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//논리명 커뮤니티종류
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CMNTY_CLS")
public class CommunityClass {

    @Id
    @Column(name="CMNTY_ID")
    private String communityId;

    @Column(name="CMNTY_TYPE")
    private String communityType;

}
