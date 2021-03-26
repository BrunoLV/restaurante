package br.com.valhala.restaurante.produtos.aplicacao.produto.rest.controller;

import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.ComandoCriaProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.ComandoEditaProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.ComandoExcluiProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.executores.ExecutorComandoCriaProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.executores.ExecutorComandoEditaProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.executores.ExecutorComandoExcluiProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json.ProdutoJsonInput;
import br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json.ProdutoJsonOutput;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoRestController {

    private final ExecutorComandoCriaProduto executorComandoCriaProduto;
    private final ExecutorComandoEditaProduto executorComandoEditaProduto;
    private final ExecutorComandoExcluiProduto executorComandoExcluiProduto;

    @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoJsonOutput> cria(@RequestBody ProdutoJsonInput input, UriComponentsBuilder builder) {

        ComandoCriaProduto comando = ComandoCriaProduto
                .builder()
                .nome(input.getNome())
                .descricao(input.getDescricao())
                .valor(input.getValor())
                .fabricante(input.getFabricante())
                .build();

        Produto produto = executorComandoCriaProduto.executaRetornoProdutoCriado(comando);

        URI uri = builder.path("/produto").path(produto.getGuid()).build().toUri();

        ProdutoJsonOutput output = ProdutoJsonOutput
                .builder()
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .dataCadastro(produto.getDataCadastro())
                .valor(produto.getValor())
                .fabricante(produto.getFabricante())
                .build();

        return ResponseEntity.created(uri).body(output);
    }

    @PutMapping(value = "/{guid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> edita(@PathVariable("guid") String guid, @RequestBody ProdutoJsonInput input) {

        ComandoEditaProduto comando = ComandoEditaProduto
                .builder()
                .guid(guid)
                .nome(input.getNome())
                .descricao(input.getDescricao())
                .valor(input.getValor())
                .fabricante(input.getFabricante())
                .build();

        executorComandoEditaProduto.executa(comando);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{guid}")
    public ResponseEntity<Void> exclui(@PathVariable("guid") String guid) {
        ComandoExcluiProduto comando = ComandoExcluiProduto.builder().guid(guid).build();
        executorComandoExcluiProduto.executa(comando);
        return ResponseEntity.noContent().build();
    }
}
