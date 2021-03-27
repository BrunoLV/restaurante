package br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErroValidacaoDadosJsonOutput implements Serializable {

    private String path;
    private String mensagem;
    private Collection<ErroValidacaoJsonOutput> erros;

    public void adicionaErro(ErroValidacaoJsonOutput erro) {
        if (this.erros == null) {
            erros = new LinkedList<>();
        }
        erros.add(erro);
    }

}
