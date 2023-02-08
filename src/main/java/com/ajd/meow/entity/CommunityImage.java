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
@SequenceGenerator(name="IMG_NO_SEQ_GEN", sequenceName="IMG_NO_SEQ", initialValue=1, allocationSize=1)
public class CommunityImage {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IMG_NO_SEQ_GEN")
    @Column(name="IMG_NO")
    private Long imageNo;

    @Column(name="POST_NO")
    private Long postNo;

    @Column(name="USER_NO")
    private Long userNo;

    @Column(name="THUM_NAIL_IMG_ADR")
    private String thumbnailImageAddress;

    @Column(name="IMG_ADR")
    private String imgPath;

    @Column(name="IMG_NAME")
    private String imgName;

    @Column(name="IMG_SIZE")
    private String imageSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no" ,insertable = false, updatable = false)
    private CommunityMaster communityMaster; // 커뮤니티 엔티티

    @ManyToOne
    @JoinColumn(name = "user_no" ,insertable = false, updatable = false)
    private UserMaster userMaster; // 유저 엔티티

}
