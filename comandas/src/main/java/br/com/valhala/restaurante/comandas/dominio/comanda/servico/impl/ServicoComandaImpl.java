package br.com.valhala.restaurante.comandas.dominio.comanda.servico.impl;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloInvalidoException;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Comanda;
import br.com.valhala.restaurante.comandas.dominio.comanda.repositorio.RepositorioComanda;
import br.com.valhala.restaurante.comandas.dominio.comanda.servico.ServicoComanda;
import br.com.valhala.restaurante.comandas.dominio.comanda.validacao.ValidadorComanda;
import br.com.valhala.restaurante.dominio.validacao.resultado.ResultadoValidacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicoComandaImpl implements ServicoComanda {

    private final RepositorioComanda repositorio;
    private final ValidadorComanda validador;

    @Override
    public Comanda cria(Comanda dados) {
        Comanda comanda = Comanda.novo(dados.getNumeroMesa(), dados.getItens());
        validaComanda(comanda);
        repositorio.cria(comanda);
        return comanda;
    }

    @Override
    public void edita(String guid, Comanda dados) {
        Comanda comandaPersistente = repositorio.buscaPorGuid(guid);
        Comanda comandaEditada = comandaPersistente.edita(dados);
        validaComanda(comandaEditada);
        repositorio.edita(comandaEditada);
    }

    private void validaComanda(Comanda comanda) {
        ResultadoValidacao resultadoValidacao = validador.valida(comanda);
        if (resultadoValidacao.temErros()) {
            throw new ModeloInvalidoException("Comanda inválido.", resultadoValidacao.getErros());
        }
    }

    @Override
    public void exclui(String guid) {
        Comanda comandaPersistente = repositorio.buscaPorGuid(guid);
        repositorio.exclui(comandaPersistente);
    }

}
