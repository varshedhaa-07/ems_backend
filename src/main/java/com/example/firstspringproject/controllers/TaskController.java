package com.example.firstspringproject.controllers;

import com.example.firstspringproject.models.Task;
import com.example.firstspringproject.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService t;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/id/{empId}")
    public String assignTaskById(@PathVariable int empId, @RequestBody Task task) {
        return t.assignTaskById(empId, task);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/id/{empId}/tasks")
    public List<Task> getTasksByEmployee(@PathVariable int empId) {
        return t.getTasksByEmployeeId(empId);
    }

}
