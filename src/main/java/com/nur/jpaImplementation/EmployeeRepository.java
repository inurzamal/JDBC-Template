package com.nur.jpaImplementation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // You can define custom query methods if needed
}
