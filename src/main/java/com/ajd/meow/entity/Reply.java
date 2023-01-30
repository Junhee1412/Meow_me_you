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
public class Reply {

    @Column(name="USER_NO")
    private Long userNo;

    @Id
    @Column(name="POST_NO")
    private Long PostNo;

    @Column(name = "REPLY_CONTENT")
    private String replyContent;

    @Column(name="REPLY_USER_NO")
    private Long replyUserNo;

}
