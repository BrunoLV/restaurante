package br.com.valhala.restaurante.produtos.infra.produto.rest.controller;

import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.ComandoCriaProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.ComandoEditaProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.ComandoExcluiProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.executores.ExecutorComandoCriaProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.executores.ExecutorComandoEditaProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.executores.ExecutorComandoExcluiProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.consulta.ConsultaProdutoService;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.infra.produto.rest.conversores.ConversorProdutoJsonInputParaComandoCriacao;
import br.com.valhala.restaurante.produtos.infra.produto.rest.conversores.ConversorProdutoJsonInputParaComandoEdicao;
import br.com.valhala.restaurante.produtos.infra.produto.rest.conversores.ConversorProdutoModeloParaProdutoJsonOutput;
import br.com.valhala.restaurante.produtos.infra.produto.rest.json.entrada.ProdutoJsonPostInput;
import br.com.valhala.restaurante.produtos.infra.produto.rest.json.entrada.ProdutoJsonPutInput;
import br.com.valhala.restaurante.produtos.infra.produto.rest.json.saida.ProdutoJsonOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
class ProdutoRestController {

    final ExecutorComandoCriaProduto executorComandoCriaProduto;
    final ExecutorComandoEditaProduto executorComandoEditaProduto;
    final ExecutorComandoExcluiProduto executorComandoExcluiProduto;
    final ConsultaProdutoService consultaService;
    final ConversorProdutoJsonInputParaComandoCriacao conversorProdutoJsonInputParaComandoCriacao;
    final ConversorProdutoJsonInputParaComandoEdicao conversorProdutoJsonInputParaComandoEdicao;
    final ConversorProdutoModeloParaProdutoJsonOutput conversorProdutoModeloParaProdutoJsonOutput;

    @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProdutoJsonOutput> cria(@RequestBody ProdutoJsonPostInput input, UriComponentsBuilder builder) {
        ComandoCriaProduto comando = conversorProdutoJsonInputParaComandoCriacao.converte(input);
        Produto produto = executorComandoCriaProduto.executaRetornandoProdutoCriado(comando);
        URI uri = builder.path("/produto/").path(produto.getGuid()).build().toUri();
        ProdutoJsonOutput output = conversorProdutoModeloParaProdutoJsonOutput.converte(produto);
        return ResponseEntity.created(uri).body(output);
    }

    @PutMapping(value = "/{guid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> edita(@PathVariable("guid") String guid, @RequestBody ProdutoJsonPutInput input) {
        ComandoEditaProduto comando = conversorProdutoJsonInputParaComandoEdicao.converte(input);
        if (!comando.getGuid().equals(guid)) {
            throw new IllegalStateException("O guid informado na URL do recurso não é igual ao informado no corpo da requisição.");
        }
        executorComandoEditaProduto.executa(comando);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{guid}")
    ResponseEntity<Void> exclui(@PathVariable("guid") String guid) {
        ComandoExcluiProduto comando = ComandoExcluiProduto.builder().guid(guid).build();
        executorComandoExcluiProduto.executa(comando);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProdutoJsonOutput> buscaPorId(@PathVariable("guid") String guid) {
        Produto produto = consultaService.buscaPorGuid(guid);
        ProdutoJsonOutput output = conversorProdutoModeloParaProdutoJsonOutput.converte(produto);
        return ResponseEntity.ok().body(output);
    }

    @GetMapping(value = "/lista", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Collection<ProdutoJsonOutput>> lista() {
        Collection<Produto> produtos = consultaService.lista();
        List<ProdutoJsonOutput> outputs = produtos.stream().map(p -> conversorProdutoModeloParaProdutoJsonOutput.converte(p))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(outputs);
    }

}
