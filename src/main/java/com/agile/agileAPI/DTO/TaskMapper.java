package com.agile.agileAPI.DTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.agile.agileAPI.entidades.Task;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    void mapAtualizarTaskDTOToTask(AtualizarTasksDTO dto, @MappingTarget Task task);

    RecuperarTasksDTO retornarTasksDTO(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "status", ignore = true)
    Task criarTaskDTO(CriarTaskDTO criarTaskDTO);
}
