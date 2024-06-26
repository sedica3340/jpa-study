package com.spring.jpastudy.chap04_relation.entity;


import lombok.*;

import javax.persistence.*;


@Setter @Getter
@ToString(exclude = "department")
@EqualsAndHashCode(of ="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id;

    @Column(name = "emp_name", nullable = false)
    private String name;


    // 단방향 매핑 - 데이터베이스처럼 한쪽에 상대방의 PK를 FK로 갖는 형태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "dept_id")
    private Department department;

    public void changeDepartment(Department department) {
        this.department = department;
        department.getEmployees().add(this);
    }
}
