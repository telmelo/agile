package com.agile.agileAPI.servicos;

import com.agile.agileAPI.DTO.AtualizarTasksDTO;
import com.agile.agileAPI.DTO.CriarTaskDTO;
import com.agile.agileAPI.DTO.RecuperarTasksDTO;
import com.agile.agileAPI.excecoes.IntegracaoCalendarioException;
import com.agile.agileAPI.excecoes.TaskNaoEncontrada;
import com.agile.agileAPI.repositorios.TaskRepository;

import lombok.extern.slf4j.Slf4j;

import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Service;

import com.agile.agileAPI.DTO.TaskMapper;
import com.agile.agileAPI.entidades.Task;
import com.agile.agileAPI.entidades.Status;

import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;
import java.util.Optional;

import com.agile.agileAPI.integracoes.IntegracaoCalendario;

@Service
@Slf4j 
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final IntegracaoCalendario integracaoCalendario;

    public TaskService(TaskRepository taskRepository,
                       TaskMapper taskMapper, IntegracaoCalendario integracaoCalendario){
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.integracaoCalendario = integracaoCalendario;
    }

    private Task criarEventoNoCalendario(Task task) {
        try {
            String eventId = integracaoCalendario.criarEvento(task);

            if (eventId != null) {
                task.setGoogleEventId(eventId);
                task = taskRepository.save(task);
            }
        } catch (IntegracaoCalendarioException e) {
            log.warn("Não foi possível criar o evento da task {}", task.getId(), e);
        }

        return task;
    }

    public RecuperarTasksDTO criarTask(CriarTaskDTO criarTaskDTO){
        Task novaTask = taskMapper.criarTaskDTO(criarTaskDTO);

        novaTask.setDataCriacao(LocalDate.now());
        novaTask.setStatus(Status.PENDENTE);

        Task taskSalva = taskRepository.save(novaTask);
        taskSalva = criarEventoNoCalendario(taskSalva);

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
        Task task = buscarOuLancarTask(id);

        return taskMapper.retornarTasksDTO(task);
    }

    public RecuperarTasksDTO atualizarTask(Long id, AtualizarTasksDTO atualizarTasksDTO) {
        Task task = buscarOuLancarTask(id);

        taskMapper.mapAtualizarTaskDTOToTask(atualizarTasksDTO, task);
        task = taskRepository.save(task);

        if(task.getGoogleEventId() == null){
            task = criarEventoNoCalendario(task);
        }else {
            try {
                integracaoCalendario.atualizarEvento(task);
            } catch (IntegracaoCalendarioException e) {
                log.warn("Não foi possível atualizar o evento da task {}", task.getId(), e);
            }
        }

        return taskMapper.retornarTasksDTO(task);
    }

    public void deletarTask(Long id) {
        Task task = buscarOuLancarTask(id);

        if (task.getGoogleEventId() != null) {
            try {
                integracaoCalendario.removerEvento(task.getGoogleEventId());
            } catch (IntegracaoCalendarioException e) {
                log.warn("Não foi possível remover o evento da task {}", task.getId(), e);
            }
        }

        taskRepository.delete(task);
    }

    private Task buscarOuLancarTask(Long id){
        Optional<Task> taskPorId = taskRepository.findById(id);

        if(taskPorId.isEmpty()){
            throw new TaskNaoEncontrada("Tarefa com id " + id + " não encontrada");
        }else {
            Task task = taskPorId.get();
            
            return task;
        }
    }
}
