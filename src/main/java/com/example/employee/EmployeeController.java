package com.example.employee;

import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final JdbcTemplate jdbc;

    public EmployeeController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping
    public Employee[] getEmployees() {
        return jdbc.query(
                "SELECT id, name, salary FROM employees ORDER BY id",
                (row, index) -> new Employee(
                        row.getLong("id"),
                        row.getString("name"),
                        row.getBigDecimal("salary")
                )
        ).toArray(Employee[]::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployees(@Valid @RequestBody Employee employee) {
        try {
            jdbc.update(
                    "INSERT INTO employees (id, name, salary) VALUES (?, ?, ?)",
                    employee.id(), employee.name(), employee.salary()
            );
        } catch (DuplicateKeyException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Employee ID already exists", ex
            );
        }

        return employee;
    }

    @PutMapping
    public Employee updateEmployee(@Valid @RequestBody Employee employee) {
        int updated = jdbc.update(
                "UPDATE employees SET name = ?, salary = ? WHERE id = ?",
                employee.name(), employee.salary(), employee.id()
        );

        if (updated == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee ID does not exist"
            );
        }

        return employee;
    }
}