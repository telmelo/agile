package com.agile.agileAPI.integracoes;

import com.agile.agileAPI.entidades.Task;

public interface IntegracaoCalendario {
    String criarEvento(Task task);
    void atualizarEvento(Task task);
    void removerEvento(String googleEventId);
}
