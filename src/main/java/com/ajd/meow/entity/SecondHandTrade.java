package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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
    private Long postNo;

    @Positive(message = "금액은 0원 이상이어야 합니다!")
    @Column(name="PRICE")
    private int price;
}
