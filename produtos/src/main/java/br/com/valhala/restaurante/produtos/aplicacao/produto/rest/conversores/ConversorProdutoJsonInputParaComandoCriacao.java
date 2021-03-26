package br.com.valhala.restaurante.produtos.aplicacao.produto.rest.conversores;

import br.com.valhala.restaurante.aplicacao.conversao.Conversor;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.ComandoCriaProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json.ProdutoJsonPostInput;
import org.springframework.stereotype.Component;

@Component
public class ConversorProdutoJsonInputParaComandoCriacao implements Conversor<ProdutoJsonPostInput, ComandoCriaProduto> {

    @Override
    public ComandoCriaProduto converte(ProdutoJsonPostInput source) {
        return ComandoCriaProduto
                .builder()
                .nome(source.getNome())
                .descricao(source.getDescricao())
                .valor(source.getValor())
                .fabricante(source.getFabricante())
                .build();
    }

}
