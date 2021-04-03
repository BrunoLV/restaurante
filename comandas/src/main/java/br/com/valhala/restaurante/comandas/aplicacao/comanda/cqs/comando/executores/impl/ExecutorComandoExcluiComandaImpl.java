package br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.executores.impl;

import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.ComandoExcluiComanda;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.executores.ExecutorComandoExcluiComanda;
import br.com.valhala.restaurante.comandas.dominio.comanda.servico.ServicoComanda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExecutorComandoExcluiComandaImpl implements ExecutorComandoExcluiComanda {

    private final ServicoComanda servico;

    @Transactional
    @Override
    public void executa(ComandoExcluiComanda comandoExcluiComanda) {
        servico.exclui(comandoExcluiComanda.getGuid());
    }
}
