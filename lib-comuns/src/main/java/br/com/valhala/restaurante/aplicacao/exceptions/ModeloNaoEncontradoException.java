package br.com.valhala.restaurante.aplicacao.exceptions;

public class ModeloNaoEncontradoException extends RuntimeException {

    public ModeloNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}
