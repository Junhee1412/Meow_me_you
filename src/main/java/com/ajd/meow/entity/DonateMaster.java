package com.ajd.meow.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

import static javax.persistence.GenerationType.SEQUENCE;

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
