package com.example.firstspringproject.repository;

import com.example.firstspringproject.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;

public interface TaskRepository extends JpaRepository<Task,Integer> {
}
