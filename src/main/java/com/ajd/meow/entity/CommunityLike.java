package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//논리명 좋아요
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CMNTY_LIKE")
public class CommunityLike {

    @Id
    @Column(name="POST_NO")
    private Long postNo;

    @Column(name="USER_NO")
    private Long userNo;
}
