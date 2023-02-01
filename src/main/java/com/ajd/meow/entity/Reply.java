package com.ajd.meow.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

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
    private Long PostNo;

    @Column(name = "REPLY_CONTENT")
    private String replyContent;

    @Column(name="REPLY_USER_NO")
    private Long replyUserNo;

//    @ManyToOne
//    @JoinColumn(name = "post_no")
//    private CommunityMaster communityMaster; // 커뮤니티 엔티티
//
//    @ManyToOne
//    @JoinColumn(name = "user_no")
//    private UserMaster userMaster; // 회원정보 엔티티
}
