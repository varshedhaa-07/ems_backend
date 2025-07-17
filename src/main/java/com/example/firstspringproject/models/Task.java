package com.example.firstspringproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    private String title;

    private String status = "Yet to start";

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private RegisterDetails assignedEmployee;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RegisterDetails getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(RegisterDetails assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }
}
