package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//논리명 커뮤니티 이미지
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CMNTY_IMG")
public class CommunityImage {

    @Id
    @Column(name="POST_NO")
    private Long postNo;

    @Column(name="USER_NO")
    private Long userNo;

    @Column(name="THUM_NAIL_IMG_ADR")
    private String thumbnailImageAddress;

    @Column(name="IMG_ADR")
    private String imageAddress;

    @Column(name="IMG_NAME")
    private String imageName;

    @Column(name="IMG_SIZE")
    private String imageSize;
}
