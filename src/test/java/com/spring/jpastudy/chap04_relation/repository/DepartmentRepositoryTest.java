package com.spring.jpastudy.chap04_relation.repository;

import com.spring.jpastudy.chap04_relation.entity.Department;
import com.spring.jpastudy.chap04_relation.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class DepartmentRepositoryTest {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeRepository employeeRepository;

//    @BeforeEach
    void bulkInsert() {

        for (int j = 1; j<= 10; j++ ) {
            Department dept = Department.builder()
                    .name("부서" + j)
                    .build();
            departmentRepository.save(dept);
            for (int i=1; i <= 100; i++) {
                Employee emp = Employee.builder()
                        .name("사원" + j +"부서"+ i)
                        .department(dept)
                        .build();
                employeeRepository.save(emp);
            }
        }
    }


    @Test
    @DisplayName("몰루")
    void findDeptTest() {
        //given
        Long id = 1L;
        //when
        Department department = departmentRepository.findById(id).orElseThrow();
        //then
        System.out.println("\n\n\n");
        System.out.println("department = " + department);
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("양방향 연관관계에서 연관데이터 수정")
    void changeTest() {
        //given
        // 3번 사원의 부서를 2번부서에서 1번부서로 수정

        // 3번사원
        Employee employee = employeeRepository.findById(3L).orElseThrow();
        // 1번부서
        Department department = departmentRepository.findById(1L).orElseThrow();

        //when

        // 사원정보 수정
        employee.setDepartment(department);
        department.getEmployees().add(employee);

        employeeRepository.save(employee);


        //then
        // 바뀐부서의 사원목록 조회
        List<Employee> employees = department.getEmployees();
        System.out.println("\n\n\n");
        employees.forEach(System.out::println);
        System.out.println("\n\n\n");


    }

    @Test
    @DisplayName("고아 객체 삭제하기")
    void orphanRemovalTest() {
        //given
        // 1번 부서 조회
        Department department = departmentRepository.findById(1L).orElseThrow();

        // 2번 사원 조회
        Employee employee = employeeRepository.findById(2L).orElseThrow();

        //when

        List<Employee> employeeList = department.getEmployees();
        department.removeEmployee(employee);

        departmentRepository.save(department);

        //then
    }

    @Test
    @DisplayName("양방향 관계에서 리스트에 데이터를 추가하면 db에도 insert된다")
    void cascadePersistTest() {
        //given

        // 2번 부서 조회
        Department department = departmentRepository.findById(2L).orElseThrow();

        // 새 사원 생성
        Employee employee = Employee.builder()
                .name("뽀로로")
                .build();

        //when
        department.addEmployee(employee);
        //then
    }

    @Test
    @DisplayName("부서가 사라지면 소속된 사원들도 함께 사라진다")
    void cascadeRemoveTest() {
        //given
        Department department = departmentRepository.findById(2L).orElseThrow();
        //when
        departmentRepository.delete(department);

        //then
    }



    @Test
    @DisplayName("N+1 문제")
    void nPlusOneTest() {
        //given
        List<Department> department = departmentRepository.findAll();
        //when

        for (Department dept : department) {
            List<Employee> employees = dept.getEmployees();
            System.out.println("사원목록 가져옴" + employees.get(0).getName());
        }
        //then
    }

    @Test
    @DisplayName("fetch join으로 n+1문제 해결하기")
    void fetchJoinTest() {
        //given


        //when
        List<Department> fetchEmployees = departmentRepository.getFetchEmployees();
        for (Department dept : fetchEmployees) {
            List<Employee> employees = dept.getEmployees();
            System.out.println("employees.get(0).getName() = " + employees.get(0).getName());
        }
        //then
    }




}