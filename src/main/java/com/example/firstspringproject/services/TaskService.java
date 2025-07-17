package com.example.firstspringproject.services;

import com.example.firstspringproject.models.RegisterDetails;
import com.example.firstspringproject.models.Task;
import com.example.firstspringproject.repository.RegisterDetailsRepository;
import com.example.firstspringproject.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Task> getTasksByEmployeeId(int empId) {
        return taskRepository.findByAssignedEmployee_EmpId(empId);
    }

    public String updateTaskStatus(int taskId, String newStatus, String username) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task with ID " + taskId + " not found"));
        if (!task.getAssignedEmployee().getUserName().trim().equalsIgnoreCase(username.trim())) {
            throw new SecurityException("You are not authorized to update this task.");
        }
        task.setStatus(newStatus);
        taskRepository.save(task);
        return "Status updated to: " + newStatus;
    }


}
