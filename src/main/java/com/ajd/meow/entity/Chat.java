package com.ajd.meow.entity;

// import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

//논리명 관리자채팅
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "CHAT")
public class Chat {

    @Id
    @Column(name="USER_NO")
    @GeneratedValue(generator = "uuids")
    @GenericGenerator(name= "uuid2", strategy = "uuid")
    private UUID userNo;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name="MESSAGE")
    private String message;

    @Column(name="CHAT_DATE")
    private LocalDateTime chatDate;
}
