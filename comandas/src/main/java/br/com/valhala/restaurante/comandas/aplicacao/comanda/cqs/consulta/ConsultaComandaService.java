package br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.consulta;

import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Comanda;

import java.util.Collection;

public interface ConsultaComandaService {

    Comanda buscaPorGuid(String guid);

    Collection<Comanda> lista();

}
