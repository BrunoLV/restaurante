package br.com.valhala.restaurante.dominio.validacao.resultado;

import br.com.valhala.restaurante.dominio.ObjetoValor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Erro implements ObjetoValor {

    @EqualsAndHashCode.Include
    private String campo;

    @EqualsAndHashCode.Include
    private String mensagem;

}
