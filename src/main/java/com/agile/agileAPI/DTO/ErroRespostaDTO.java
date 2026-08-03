package com.agile.agileAPI.DTO;

import java.time.LocalDateTime;

public record ErroRespostaDTO(String mensagem,
                              int status,
                              LocalDateTime timeStamp) {
}
