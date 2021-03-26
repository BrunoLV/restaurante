package br.com.valhala.restaurante.produtos.infra.produto.repositorio.conversores;

import br.com.valhala.restaurante.aplicacao.conversao.Conversor;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.infra.produto.orm.ProdutoORM;
import org.springframework.stereotype.Component;

@Component
public class ConversorProdutoModeloParaORM implements Conversor<Produto, ProdutoORM> {

    @Override
    public ProdutoORM converte(Produto source) {
        return ProdutoORM
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
