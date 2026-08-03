package com.agile.agileAPI.servicos;

import com.agile.agileAPI.DTO.ErroRespostaDTO;
import com.agile.agileAPI.excecoes.TaskNaoEncontrada;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class TaskNaoEncontradaHandler {

    @ExceptionHandler(TaskNaoEncontrada.class)
    public ResponseEntity tratarTaskNaoEncontrada(TaskNaoEncontrada excecao){

        ErroRespostaDTO corpoDoErro = new ErroRespostaDTO("Task não encontrada",
                404, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(corpoDoErro);
    }
}
