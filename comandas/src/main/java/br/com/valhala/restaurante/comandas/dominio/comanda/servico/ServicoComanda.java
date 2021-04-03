package br.com.valhala.restaurante.comandas.dominio.comanda.servico;

import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Comanda;
import br.com.valhala.restaurante.dominio.servico.Servico;

public interface ServicoComanda extends Servico {

    Comanda cria(Comanda dados);

    void edita(String guid, Comanda dados);

    void exclui(String guid);

}
