package com.agile.agileAPI.integracoes;

import com.agile.agileAPI.entidades.Task;

public interface IntegracaoCalendario {
    public String criarEvento(Task task);
    public void atualizarEvento(Task task);
    public void removerEvento(String googleEventId);
}
