package com.agile.agileAPI.excecoes;

public class IntegracaoCalendarioException extends RuntimeException {
    public IntegracaoCalendarioException(String mensagem, Throwable causa){
        super(mensagem, causa);
    }
    
}
