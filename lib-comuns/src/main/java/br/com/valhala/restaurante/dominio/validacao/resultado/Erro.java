package br.com.valhala.restaurante.dominio.validacao.resultado;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Erro {

    private String campo;
    private String mensagem;

}
