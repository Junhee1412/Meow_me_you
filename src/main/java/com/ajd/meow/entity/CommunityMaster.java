package com.ajd.meow.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


//논리명 커뮤니티글정보
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CMNTY_MSTR")
@SequenceGenerator(name="POST_NO_SEQ_GEN", sequenceName="POST_NO_SEQ", initialValue=1, allocationSize=1)
public class CommunityMaster {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="POST_NO_SEQ_GEN")
    @Column(name="POST_NO")
    private Long postNo;

    @Column(name="USER_NO")
    private Long userNo;

    @Column(name="CMNTY_ID")
    private String communityId;

    @Column(name="POST_ID")
    private String postId;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    @Column(name="SUBJECT")
    private String subject;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    @Column(name="CONTENT")
    private String content;

    @Column(name="CRT_DATE")
    private LocalDateTime createPostDate;

    @Column(name="VIEW_COUNT")
    private int viewCount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name="user_no" ,insertable = false, updatable = false)
    private UserMaster userMaster;

    @OrderBy("reply_no desc")
    @OneToMany(mappedBy = "communityMaster",cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonIgnoreProperties({"communityMaster"})
    private List<Reply> replyList;

    @OneToMany(mappedBy = "communityMaster",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CommunityImage> communityImageList = new ArrayList<>();


}
