package com.agile.agileAPI.servicos;

import com.agile.agileAPI.repositorios.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public void criarTask
}
