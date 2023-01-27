package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

//논리명 중고거래
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="SCND_HNDTR")
public class SecondHandTrade {

    @Column(name="USER_NO")
    private Long userNo;

    @Id
    @Column(name="POST_NO")
    private Long PostNo;

    @Min(value=3000, message = "금액은 필수 입력 값입니다.")
    @Column(name="PRICE")
    private int price;
}
