package br.com.valhala.restaurante.comandas.infra.comanda.rest.controller;

import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.ComandoCriaComanda;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.ComandoEditaComanda;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.ComandoExcluiComanda;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.executores.ExecutorComandoCriaComanda;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.executores.ExecutorComandoEditaComanda;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.executores.ExecutorComandoExcluiComanda;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.consulta.ConsultaComandaService;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Comanda;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.conversores.ConversorComandaModeloParaComandaOutputJson;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.conversores.ConversorComandaPostInputParaComandaCriaComanda;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.conversores.ConversorComandaPutInputParaComandaEditaComanda;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.entrada.ComandaJsonPostInput;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.entrada.ComandaJsonPutInput;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.saida.ComandaJsonOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/comanda")
@RequiredArgsConstructor
@Slf4j
class ComandaRestController {

    final ConsultaComandaService consultaService;
    final ExecutorComandoCriaComanda executorComandoCriaComanda;
    final ExecutorComandoEditaComanda executorComandoEditaComanda;
    final ExecutorComandoExcluiComanda executorComandoExcluiComanda;
    final ConversorComandaModeloParaComandaOutputJson conversorModeloParaOutputJson;
    final ConversorComandaPostInputParaComandaCriaComanda conversorComandaPostInputParaComandaCriaComanda;
    final ConversorComandaPutInputParaComandaEditaComanda conversorComandaPutInputParaComandaEditaComanda;

    @GetMapping(value = "/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ComandaJsonOutput> buscoPorGuid(@PathVariable("guid") String guid) {
        Comanda comanda = consultaService.buscaPorGuid(guid);
        ComandaJsonOutput output = conversorModeloParaOutputJson.converte(comanda);
        return ResponseEntity.ok(output);
    }

    @GetMapping(value = "/lista", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Collection<ComandaJsonOutput>> lista() {
        Collection<Comanda> comandas = consultaService.lista();
        List<ComandaJsonOutput> outputs = comandas
                .stream()
                .map(conversorModeloParaOutputJson::converte)
                .collect(Collectors.toList());
        return ResponseEntity.ok(outputs);
    }

    @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ComandaJsonOutput> cria(@RequestBody ComandaJsonPostInput input, UriComponentsBuilder builder) {
        log.info("Executando operação de criacao de comanda...");
        ComandoCriaComanda comando = conversorComandaPostInputParaComandaCriaComanda.converte(input);
        Comanda comanda = executorComandoCriaComanda.executaRetornandoComandaCriada(comando);
        URI uri = builder.path("/comanda/").path(comanda.getGuid()).build().toUri();
        ComandaJsonOutput output = conversorModeloParaOutputJson.converte(comanda);
        return ResponseEntity.created(uri).body(output);
    }

    @PutMapping(value = "/{guid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> edita(@PathVariable("guid") String guid, @RequestBody ComandaJsonPutInput input) {
        ComandoEditaComanda comando = conversorComandaPutInputParaComandaEditaComanda.converte(input);
        executorComandoEditaComanda.executa(comando);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{guid}")
    ResponseEntity<Void> exclui(@PathVariable("guid") String guid) {
        ComandoExcluiComanda comando = ComandoExcluiComanda.builder().guid(guid).build();
        executorComandoExcluiComanda.executa(comando);
        return ResponseEntity.noContent().build();
    }

}
