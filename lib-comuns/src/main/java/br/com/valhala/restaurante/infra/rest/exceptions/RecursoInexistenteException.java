package br.com.valhala.restaurante.infra.rest.exceptions;

import lombok.Getter;

@Getter
public class RecursoInexistenteException extends RuntimeException {

    private String recurso;

    public RecursoInexistenteException(String recurso, String mensagem) {
        super(mensagem);
        this.recurso = recurso;
    }

}
