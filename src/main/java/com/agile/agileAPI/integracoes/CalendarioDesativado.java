package com.agile.agileAPI.integracoes;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import com.agile.agileAPI.entidades.Task;

@Service 
@ConditionalOnProperty(name = "agile.google-calendar.enabled", havingValue = "false",
matchIfMissing = true)
public class CalendarioDesativado implements IntegracaoCalendario {
    @Override
    public String criarEvento(Task task){
        return null;
    }

    @Override
    public void atualizarEvento(Task task){

    }

    @Override
    public void removerEvento(String googleEventId){

    }
}
