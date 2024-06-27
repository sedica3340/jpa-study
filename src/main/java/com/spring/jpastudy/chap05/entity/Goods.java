package com.spring.jpastudy.chap05.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@ToString(exclude = "purchaseList")
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_mtm_goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;

    @Column(name = "goods_name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "goods", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Purchase> purchaseList = new ArrayList<>();


}
