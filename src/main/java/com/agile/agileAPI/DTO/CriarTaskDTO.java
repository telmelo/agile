package com.agile.agileAPI.DTO;

import com.agile.agileAPI.entidades.Prioridade;
import com.agile.agileAPI.entidades.Status;
import java.time.LocalDate;

public record CriarTaskDTO(String titulo,
                           String descricao,
                           LocalDate dataLimite,
                           Prioridade prioridade
) {
}
