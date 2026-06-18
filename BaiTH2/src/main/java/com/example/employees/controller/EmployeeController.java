package com.example.employees.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @GetMapping
    public List<String> getEmployees() {
        // Trả về danh sách 3 nhân viên cứng như yêu cầu
        return Arrays.asList("Nguyen Van A", "Tran Thi B", "Le Van C");
    }
}