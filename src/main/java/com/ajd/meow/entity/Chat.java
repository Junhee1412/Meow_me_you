package com.ajd.meow.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.*;

//논리명 관리자채팅
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "CHAT")
public class Chat {

    @Id
    @Column(name="USER_NO")
    private Long userNo;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name="MESSAGE")
    private String message;

    @Column(name="CHAT_DATE")
    private LocalDateTime chatDate;
}
