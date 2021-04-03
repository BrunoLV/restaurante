package br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.executores;

import br.com.valhala.restaurante.aplicacao.cqs.ExecutorComando;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.ComandoCriaComanda;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Comanda;

public interface ExecutorComandoCriaComanda extends ExecutorComando<ComandoCriaComanda> {

    Comanda executaRetornandoComandaCriada(ComandoCriaComanda comando);

}
