package br.com.valhala.restaurante.infra.rest.exceptions;

import lombok.Getter;

@Getter
public class ErroRecursoExternoException extends RuntimeException {

    private String recurso;

    public ErroRecursoExternoException(String recurso, String mensagem) {
        super(mensagem);
        this.recurso = recurso;
    }

}
