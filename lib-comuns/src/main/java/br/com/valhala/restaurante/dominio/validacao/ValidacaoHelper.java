package br.com.valhala.restaurante.dominio.validacao;

import br.com.valhala.restaurante.dominio.validacao.resultado.ResultadoValidacao;

public interface ValidacaoHelper<M> {

    ResultadoValidacao valida(M modelo);

}
