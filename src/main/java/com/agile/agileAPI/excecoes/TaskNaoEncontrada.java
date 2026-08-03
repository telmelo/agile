package com.agile.agileAPI.excecoes;

public class TaskNaoEncontrada extends RuntimeException {
    public TaskNaoEncontrada(String message) {
        super(message);
    }
}
