package com.agile.agileAPI.DTO;

import com.agile.agileAPI.entidades.Prioridade;
import com.agile.agileAPI.entidades.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;



public record CriarTaskDTO(@NotBlank String titulo,
                           String descricao,
                           @NotNull LocalDate dataLimite,
                           @NotNull Prioridade prioridade
) {
}
