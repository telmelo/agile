package com.agile.agileAPI.servicos;

import com.agile.agileAPI.DTO.AtualizarTasksDTO;
import com.agile.agileAPI.DTO.CriarTaskDTO;
import com.agile.agileAPI.DTO.RecuperarTasksDTO;
import com.agile.agileAPI.excecoes.TaskNaoEncontrada;
import com.agile.agileAPI.repositorios.TaskRepository;
import org.mapstruct.MappingTarget;
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

    public RecuperarTasksDTO buscarPorId(Long id)  {
        Optional<Task> taskPorId = taskRepository.findById(id);

        if (taskPorId.isEmpty()){
            throw new TaskNaoEncontrada("Tarefa com id " + id + " não encontrada");
        }else {
            Task task = taskPorId.get();

            RecuperarTasksDTO taskDTO = taskMapper.retornarTasksDTO(task);

            return taskDTO;
        }
    }

    public RecuperarTasksDTO atualizarTask(Long id, AtualizarTasksDTO atualizarTasksDTO) {
        Optional<Task> taskPorId = taskRepository.findById(id);

        if (taskPorId.isEmpty()){
            throw new TaskNaoEncontrada("Task com id " + id + " não encontrada");
        }else {
            Task task = taskPorId.get();

            taskMapper.mapAtualizarTaskDTOToTask(atualizarTasksDTO, task);

            task = taskRepository.save(task);

            RecuperarTasksDTO dto = taskMapper.retornarTasksDTO(task);

            return dto;
        }

    }

    public void deletarTask(Long id){
        if (taskRepository.existsById(id)){
            taskRepository.deleteById(id);
        }else {
            throw new TaskNaoEncontrada("Task com id " + id + " não encontrada");
        }
    }
}
