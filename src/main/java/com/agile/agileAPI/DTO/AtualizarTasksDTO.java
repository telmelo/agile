package com.agile.agileAPI.DTO;

import com.agile.agileAPI.entidades.Prioridade;
import com.agile.agileAPI.entidades.Status;

import java.time.LocalDate;

public record AtualizarTasksDTO(String titulo,
                                String descricao,
                                LocalDate dataLimite,
                                Prioridade prioridade,
                                Status status) {
}
