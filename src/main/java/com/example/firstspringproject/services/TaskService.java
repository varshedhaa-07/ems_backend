package com.example.firstspringproject.services;

import com.example.firstspringproject.models.RegisterDetails;
import com.example.firstspringproject.models.Task;
import com.example.firstspringproject.repository.RegisterDetailsRepository;
import com.example.firstspringproject.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    RegisterDetailsRepository registerDetailsRepository;

    public String assignTaskById(int empId, Task task) {
        RegisterDetails user = registerDetailsRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        task.setAssignedEmployee(user);
        taskRepository.save(task);
        return "Task assigned to employee ID: " + empId;
    }
}
