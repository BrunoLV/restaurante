package br.com.valhala.restaurante.dominio.validacao.resultado;

import br.com.valhala.restaurante.dominio.ObjetoValor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedList;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class ResultadoValidacao implements ObjetoValor {

    @EqualsAndHashCode.Include
    private LinkedList<Erro> erros;

    public void adicionaErro(Erro erro) {
        if (erros == null) {
            erros = new LinkedList<>();
        }
        erros.add(erro);
    }

    public boolean temErros() {
        return erros != null && !erros.isEmpty();
    }

}
