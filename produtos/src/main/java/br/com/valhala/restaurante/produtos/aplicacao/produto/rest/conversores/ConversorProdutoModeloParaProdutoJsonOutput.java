package br.com.valhala.restaurante.produtos.aplicacao.produto.rest.conversores;

import br.com.valhala.restaurante.aplicacao.conversao.Conversor;
import br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json.ProdutoJsonOutput;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import org.springframework.stereotype.Component;

@Component
public class ConversorProdutoModeloParaProdutoJsonOutput implements Conversor<Produto, ProdutoJsonOutput> {

    @Override
    public ProdutoJsonOutput converte(Produto source) {
        return ProdutoJsonOutput
                .builder()
                .guid(source.getGuid())
                .nome(source.getNome())
                .descricao(source.getDescricao())
                .valor(source.getValor())
                .dataCadastro(source.getDataCadastro())
                .fabricante(source.getFabricante())
                .build();
    }

}
