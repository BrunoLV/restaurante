package br.com.valhala.restaurante.aplicacao.conversao;

@FunctionalInterface
public interface Conversor<S, D> {
    D converte(S source);
}
