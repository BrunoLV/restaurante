package br.com.valhala.restaurante.aplicacao.exceptions;

import br.com.valhala.restaurante.dominio.validacao.resultado.Erro;
import lombok.Getter;

import java.util.Collection;

@Getter
public class ModeloInvalidoException extends RuntimeException {

    private Collection<Erro> erros;

    public ModeloInvalidoException(String mensagem, Collection<Erro> erros) {
        super(mensagem);
        this.erros = erros;
    }

}
