package com.agile.agileAPI.servicos;

import com.agile.agileAPI.DTO.CriarTaskDTO;
import com.agile.agileAPI.DTO.RecuperarTasksDTO;
import com.agile.agileAPI.repositorios.TaskRepository;
import org.springframework.stereotype.Service;
import com.agile.agileAPI.DTO.TaskMapper;
import com.agile.agileAPI.entidades.Task;
import com.agile.agileAPI.entidades.Status;
import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository,
                       TaskMapper taskMapper){
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public RecuperarTasksDTO criarTask(CriarTaskDTO criarTaskDTO){
        Task novaTask = taskMapper.criarTaskDTO(criarTaskDTO);

        novaTask.setDataCriacao(LocalDate.now());
        novaTask.setStatus(Status.PENDENTE);

        Task taskSalva = taskRepository.save(novaTask);

        RecuperarTasksDTO dtoResposta = taskMapper.retornarTasksDTO(taskSalva);

        return dtoResposta;

    }

    public List<RecuperarTasksDTO> buscarTodas(){
        List<Task> listaDeTasks = taskRepository.findAll();

        List<RecuperarTasksDTO> listaDeDtos = listaDeTasks.stream().map(task ->
                taskMapper.retornarTasksDTO(task)).toList();

        return listaDeDtos;
    }

    public RecuperarTasksDTO buscarPorId(Long id){
        Optional<Task> taskPorId = taskRepository.findById(id);

        if (taskPorId.isEmpty()){
            //lança exceção
        }else {
            taskPorId.stream().map(task -> taskMapper.retornarTasksDTO(task));
        }

        return taskPorId;
    }
}
