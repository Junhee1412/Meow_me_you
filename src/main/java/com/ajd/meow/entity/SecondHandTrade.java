package com.ajd.meow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

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

    @NotBlank(message = "금액은 필수 입력 값입니다.")
    @Column(name="PRICE")
    private int price;
}
