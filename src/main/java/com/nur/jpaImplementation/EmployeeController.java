package com.nur.jpaImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getAllProducts() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getProductById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/employee")
    public void addProduct(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }

    @PutMapping("/employee/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody Employee employee) {
        // Ensure the ID in the path and the ID in the request body match
        if (!id.equals(employee.getId())) {
            throw new IllegalArgumentException("ID in the path and request body must match");
        }
        employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/employee/{id}")
    public void deleteProduct(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
