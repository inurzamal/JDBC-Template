package com.nur.jpaImplementation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeTest {

    @Test
    void testEqualsAndHashCode() {
        Employee employee1 = new Employee("John", 30.5);
        Employee employee2 = new Employee("John", 30.5);
        Employee employee3 = new Employee("Jane", 25.0);


        // Test equals method
        assertEquals(employee1, employee2);
        assertNotEquals(employee1, employee3);

        // Test hashCode method
        assertEquals(employee1.hashCode(), employee2.hashCode());
        assertNotEquals(employee1.hashCode(), employee3.hashCode());
    }

    @Test
    void testInHashMap() {
        Employee employee1 = new Employee(1L, "John", 30.5);
        Employee employee2 = new Employee(2L, "John", 30.5);
        Employee employee3 = new Employee(3L, "Jane", 25.0);

        Map<Employee, String> employeeMap = new HashMap<>();
        employeeMap.put(employee1, "Employee 1 details");
        employeeMap.put(employee2, "Employee 2 details"); // emp1 value will get replaced
        employeeMap.put(employee3, "Employee 3 details");
        System.out.println(employeeMap.toString());

        assertTrue(employeeMap.containsKey(employee2));
    }

    @Test
    void testInHashSet() {
        Employee employee1 = new Employee("John", 30.5);
        Employee employee2 = new Employee("John", 30.5);

        Set<Employee> employeeSet = new HashSet<>();
        employeeSet.add(employee1);

        assertTrue(employeeSet.contains(employee2));
    }

    // You can add more test cases as needed
}
