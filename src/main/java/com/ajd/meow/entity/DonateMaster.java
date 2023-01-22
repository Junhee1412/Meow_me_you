package com.ajd.meow.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

//논리명 후원
@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="DNTN_MSTR")
public class DonateMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DNTN_CODE")
    private String donateCode;

    @Column(name="DNTN_BSNS_CODE")
    private String donateBusinessCode;

    @Column(name="USER_NO")
    private UUID userNo;

    @Column(name="DNTN_NAME")
    private String donateName;

    @Column(name = "DNTN_AMNT")
    private int donateAmount;

    @Column(name="DNTN_TYPE")
    private String donateType;

    @Column(name="DNTN_WAYCODE")
    private String donateWayCode;

    @Column(name="DNTN_RCPT_STATE")
    private String donateReceiptState;

    @Column(name="DNTN_STATECODE")
    private String donateStateCode;

    @Column(name="DNTN_DATE")
    private Date donateDate;

    @Column(name ="DNTN_STATE_DATE")
    private Date donateStateDate;

}
