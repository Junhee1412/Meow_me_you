package com.ajd.meow.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

//논리명 후원
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="DNTN_MSTR")
@SequenceGenerator(name="DNTN_CODE_NO_SEQ_GEN", sequenceName="DNTN_CODE_NO_SEQ", initialValue=1, allocationSize=1)
public class DonateMaster {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DNTN_CODE_NO_SEQ_GEN")
    @Column(name="DNTN_CODE")
    private Long donateCode;

    @Column(name="DNTN_BSNS_CODE")
    private String donateBusinessCode;

    @Column(name="USER_NO")
    private Long userNo;

    @NotBlank(message = "후원자명은 필수 입력값입니다.")
    @Column(name="DNTN_NAME")
    private String donateName;

    @Column(name = "DNTN_AMNT")
    private int donateAmount;

    @Column(name="DNTN_TYPE")
    private String donateType;

    @NotBlank(message = "통신사는 필수 입력값입니다.")
    @Column(name = "PHONE_TYPE")
    private String phoneType;

    @NotBlank(message = "핸드폰번호는 필수 입력 값입니다.")
    @Column(name = "PHONE_NUM")
    private String phoneNumber;

    @NotNull(message = "생일은 필수 입력 값입니다.")
    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    @NotBlank(message = "성별은 필수 입력 값입니다.")
    @Column(name = "GENDER")
    private String gender;

    @Column(name="DNTN_WAYCODE")
    private String donateWayCode;

    @Column(name="DNTN_STATECODE")
    private String donateStateCode;

    @Column(name="DNTN_DATE")
    private Date donateDate;
}
