package com.agile.agileAPI.repositorios;

import com.agile.agileAPI.entidades.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
