package com.spring.jpastudy.chap02.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Setter @Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_student")
public class Student {

    @Id
    @Column(name = "stu_id")
    @GeneratedValue(generator = "uid")
    @GenericGenerator(strategy = "uuid", name= "uid")
    private String id;

    @Column(name = "stu_name", nullable = false)
    private String name;


    private String city;

    private String major;
}
