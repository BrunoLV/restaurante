package br.com.valhala.restaurante.aplicacao.conversao;

public interface Conversor<S, D> {
    D converte(S source);
}
