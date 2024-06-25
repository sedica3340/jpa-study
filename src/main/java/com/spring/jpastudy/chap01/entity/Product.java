package com.spring.jpastudy.chap01.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @ToString(exclude = "nickName")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long id;

    @Setter
    @Column(name = "prod_nm", length = 30, nullable = false)
    private String name;

    @Column(name = "price")
    private int price;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Transient
    private String nickName;

    public enum Category {
        FOOD, FASHION, ELECTRONIC
    }

    // 컬럼 기본값 설정
    @PrePersist
    public void prePersist() {
        if (this.price == 0) {
            this.price = 10000;
        }
        if(this.category == null) {
            this.category = Category.FOOD;
        }
    }
}
