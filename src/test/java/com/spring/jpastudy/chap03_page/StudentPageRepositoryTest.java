package com.spring.jpastudy.chap03_page;

import com.spring.jpastudy.chap02.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class StudentPageRepositoryTest {

    @Autowired
    StudentPageRepository repository;

    @BeforeEach
    void bulkInsert() {
        for (int i = 1; i <= 147; i++) {
            Student s = Student.builder()
                    .name("김시골" + i)
                    .city("도시" + i)
                    .major("숨쉬기" + i)
                    .build();
            repository.save(s);
        }
    }

    @Test
    @DisplayName("asd")
    void basicPageTest() {
        //given
        int pageNo = 6;
        int amount = 10;
        Pageable pageInfo = PageRequest.of(pageNo - 1, amount);
        //when
        Page<Student> students = repository.findAll(pageInfo);

        List<Student> studentList = students.getContent();
        int totalPages = students.getTotalPages();

        long totalElements = students.getTotalElements();
        //then


        System.out.println("\n\n\n\n");
        studentList.forEach(System.out::println);
        System.out.println("totalPages = " + totalPages);
        System.out.println("totalElements = " + totalElements);
        System.out.println("\n\n\n\n");
    }


    @Test
    @DisplayName("asdasd")
    void pagingAndSortTest() {
        //given
        Pageable pageInfo = PageRequest.of(
                0,
                10,
//                Sort.by("name").descending()
                Sort.by(Sort.Order.desc("name"),
                        Sort.Order.asc("city")
                )
        );
        //when
        Page<Student> studentPage = repository.findAll(pageInfo);
        //then
        System.out.println("\n\n\n\n");
        studentPage.forEach(System.out::println);
        System.out.println("\n\n\n\n");
    }

}