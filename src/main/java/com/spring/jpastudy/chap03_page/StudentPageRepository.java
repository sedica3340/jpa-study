package com.spring.jpastudy.chap03_page;

import com.spring.jpastudy.chap02.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentPageRepository extends JpaRepository<Student, String> {

    // 전체 조회 상황에서 페이징 처리하기
    Page<Student> findAll(Pageable pageable);

    Page<Student> findByNameContaining(String name, Pageable pageable);

//    @Query(value = "", nativeQuery = true)
//    Page<Student> getList();
}
