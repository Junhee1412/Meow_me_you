package com.ajd.meow.entity;

import lombok.*;

import javax.persistence.*;

//논리명 댓글
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="REPLY")
@SequenceGenerator(name="REPLY_NO_SEQ_GEN", sequenceName="REPLY_NO_SEQ", initialValue=1, allocationSize=1)
public class Reply {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REPLY_NO_SEQ_GEN")
    @Column(name = "REPLY_NO")
    private Long replyNo;

    @Column(name="USER_NO")
    private Long userNo;

    @Column(name="POST_NO")
    private Long postNo;

    @Column(name = "REPLY_CONTENT")
    private String replyContent;

    @ManyToOne
    @JoinColumn(name = "post_no" ,insertable = false, updatable = false)
    private CommunityMaster communityMaster; // 커뮤니티 엔티티

    @ManyToOne
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private UserMaster userMaster; // 유저 엔티티

    public void save(CommunityMaster communityMaster,UserMaster userMaster) {
        this.userMaster=userMaster;
        this.communityMaster=communityMaster;
    }
}
