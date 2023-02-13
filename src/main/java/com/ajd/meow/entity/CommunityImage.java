package com.ajd.meow.entity;

import lombok.*;

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

    @Column(name="IMG_ADR")
    private String imgPath;

    @Column(name="IMG_NAME")
    private String imgName;

    @Column(name="IMG_ORGNAME")
    private String imageOrgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no" ,insertable = false, updatable = false)
    private CommunityMaster communityMaster; // 커뮤니티 엔티티

    @ManyToOne
    @JoinColumn(name = "user_no" ,insertable = false, updatable = false)
    private UserMaster userMaster; // 유저 엔티티

    @Builder
    public CommunityImage(Long imageNo, String imageOrgName, String imgName, String imgPath,Long userNo, Long postNo) {
        this.imageNo = imageNo;
        this.imageOrgName = imageOrgName;
        this.imgName = imgName;
        this.imgPath = imgPath;
        this.userNo = userNo;
        this.postNo = postNo;

    }
}
