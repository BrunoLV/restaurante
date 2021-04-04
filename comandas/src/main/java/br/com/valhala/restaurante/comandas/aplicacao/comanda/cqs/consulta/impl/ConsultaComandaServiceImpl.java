package br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.consulta.impl;

import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.consulta.ConsultaComandaService;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Comanda;
import br.com.valhala.restaurante.comandas.dominio.comanda.repositorio.RepositorioComanda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ConsultaComandaServiceImpl implements ConsultaComandaService {

    private final RepositorioComanda repositorio;

    @Override
    public Comanda buscaPorGuid(String guid) {
        return repositorio.buscaPorGuid(guid);
    }

    @Override
    public Collection<Comanda> lista() {
        return repositorio.lista();
    }
}
