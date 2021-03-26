package br.com.valhala.restaurante.aplicacao.cqs;

public interface ExecutorComando<C extends Comando> {
    void executa(C c);
}
