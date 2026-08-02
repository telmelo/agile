package com.agile.agileAPI.servicos;

import com.agile.agileAPI.repositorios.TaskRepository;
import org.springframework.stereotype.Service;
import com.agile.agileAPI.DTO.TaskMapper;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository,
                       TaskMapper taskMapper){
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }
}
