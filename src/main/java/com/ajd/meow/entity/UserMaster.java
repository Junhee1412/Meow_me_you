package com.ajd.meow.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Set;

//논리명 사용자정보
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER_MSTR")
@SequenceGenerator(name="USER_NO_SEQ_GEN", sequenceName="USER_NO_SEQ", initialValue=1, allocationSize=1)
public class UserMaster {

    @Id
    @Column(name="USER_NO")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_NO_SEQ_GEN")
    private Long userNo;

    @Column(name="USER_TYPE")
    private String userType;

    @NotBlank(message = "ID는 필수 입력 값입니다.")
    @Email(message = "ID는 이메일 형식이어야 합니다.")
    @Column(name = "USER_ID")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @Column(name = "USER_PW")
    private String userPassword;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Column(name = "USER_NAME")
    private String userName;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "ADDR")
    private String Address;

    @NotBlank(message = "통신사는 필수 입력값입니다.")
    @Column(name = "PHONE_TYPE")
    private String phoneType;

    @NotBlank(message = "핸드폰번호는 필수 입력 값입니다.")
    @Column(name = "PHONE_NUM")
    private String phoneNumber;

    @Column(name = "INTRODUCE")
    private String introduce;

    @Column(name = "PRF_IMG_NAME")
    private String profileImageName;

    @Column(name = "PRF_IMG_PATH")
    private String profileImagePath;

    @Column(name = "USER_JOIN_DATE")
    private LocalDateTime userJoinDate;

    @OneToMany(mappedBy = "userMaster" ,orphanRemoval = true)
    private Set<CommunityMaster> communityMasters;

}
