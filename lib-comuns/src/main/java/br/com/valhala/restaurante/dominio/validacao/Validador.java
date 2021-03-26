package br.com.valhala.restaurante.dominio.validacao;

import br.com.valhala.restaurante.dominio.validacao.resultado.ResultadoValidacao;

public interface Validador<M> {

    ResultadoValidacao valida(M modelo);

}
