package br.com.valhala.restaurante.produtos.aplicacao.produto.rest.conversores;

import br.com.valhala.restaurante.aplicacao.conversao.Conversor;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.ComandoEditaProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json.ProdutoJsonPutInput;
import org.springframework.stereotype.Component;

@Component
public class ConversorProdutoJsonInputParaComandoEdicao implements Conversor<ProdutoJsonPutInput, ComandoEditaProduto> {

    @Override
    public ComandoEditaProduto converte(ProdutoJsonPutInput source) {
        return ComandoEditaProduto
                .builder()
                .guid(source.getGuid())
                .nome(source.getNome())
                .descricao(source.getDescricao())
                .valor(source.getValor())
                .fabricante(source.getFabricante())
                .build();
    }

}
