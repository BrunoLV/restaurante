package br.com.valhala.restaurante.dominio.validacao.resultado;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class ResultadoValidacao {

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
