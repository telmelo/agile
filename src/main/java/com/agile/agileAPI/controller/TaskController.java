package com.agile.agileAPI.controller;

import com.agile.agileAPI.DTO.AtualizarTasksDTO;
import com.agile.agileAPI.DTO.CriarTaskDTO;
import com.agile.agileAPI.DTO.RecuperarTasksDTO;
import com.agile.agileAPI.servicos.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<RecuperarTasksDTO> criar(@RequestBody @Valid CriarTaskDTO dto){
        RecuperarTasksDTO taskCriar = taskService.criarTask(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskCriar);
    }

    @GetMapping
    public ResponseEntity<List<RecuperarTasksDTO>> listarTodas(){
        List<RecuperarTasksDTO> dtosRecuperados = taskService.buscarTodas();

        return ResponseEntity.status(HttpStatus.OK).body(dtosRecuperados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecuperarTasksDTO> buscarPorId(@PathVariable Long id){
        RecuperarTasksDTO taskEncontrada = taskService.buscarPorId(id);

        return ResponseEntity.status(HttpStatus.OK).body(taskEncontrada);
    }


    @PutMapping("/{id}")
    public ResponseEntity<RecuperarTasksDTO> atualizarTask(@PathVariable Long id,
                                                           @RequestBody @Valid AtualizarTasksDTO atualizarTasksDTO){
        RecuperarTasksDTO taskAtualizada = taskService.atualizarTask(id, atualizarTasksDTO);

        return ResponseEntity.status(HttpStatus.OK).body(taskAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTask(@PathVariable Long id){
        taskService.deletarTask(id);

        return ResponseEntity.noContent().build();
    }
}
