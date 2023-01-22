package com.ajd.meow.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;


//논리명 커뮤니티글정보
@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CMNTY_MSTR")
public class CommunityMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="POST_NO")
    private Long postNo;

    @Column(name="USER_NO")
    private UUID userNo;

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

    @Column(name="UPDATE_DATE")
    private LocalDateTime updatePostDate;

    @Column(name = "DELETE_DATE")
    private LocalDateTime deletePostDate;
}
