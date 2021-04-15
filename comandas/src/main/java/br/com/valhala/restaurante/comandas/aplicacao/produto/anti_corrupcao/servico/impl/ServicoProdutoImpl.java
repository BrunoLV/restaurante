package br.com.valhala.restaurante.comandas.aplicacao.produto.anti_corrupcao.servico.impl;

import br.com.valhala.restaurante.comandas.aplicacao.produto.anti_corrupcao.servico.ServicoProduto;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Produto;
import br.com.valhala.restaurante.comandas.infra.externo.produto.ClienteRestProduto;
import br.com.valhala.restaurante.comandas.infra.externo.produto.dto.ProdutoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServicoProdutoImpl implements ServicoProduto {

    private final ClienteRestProduto clienteRestProduto;

    @Override
    public Produto buscaPorGuid(String guid) {
        ProdutoDTO resposta = clienteRestProduto.getByGuid(guid);
        if (resposta != null) {
            return Produto.of(resposta.getGuid(), resposta.getNome(), resposta.getFabricante());
        }
        return null;
    }
}
