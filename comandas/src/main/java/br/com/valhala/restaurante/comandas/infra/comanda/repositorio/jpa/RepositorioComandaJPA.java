package br.com.valhala.restaurante.comandas.infra.comanda.repositorio.jpa;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloNaoEncontradoException;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Comanda;
import br.com.valhala.restaurante.comandas.infra.comanda.orm.ComandaORM;
import br.com.valhala.restaurante.comandas.infra.comanda.orm.ItemORM;
import br.com.valhala.restaurante.comandas.infra.comanda.orm.conversores.ConversorComandaModeloParaORM;
import br.com.valhala.restaurante.comandas.infra.comanda.orm.conversores.ConversorComandaORMParaModelo;
import br.com.valhala.restaurante.dominio.repositorio.Repositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RepositorioComandaJPA implements Repositorio<Comanda> {

    private final RepositorioComandaSpringData repositorio;
    private final ConversorComandaModeloParaORM conversorComandaModeloParaORM;
    private final ConversorComandaORMParaModelo conversorComandaORMParaModelo;

    @Override
    public void cria(Comanda comanda) {
        ComandaORM orm = conversorComandaModeloParaORM.converte(comanda);
        repositorio.save(orm);
    }

    @Override
    public void edita(Comanda comanda) {
        ComandaORM ormEdicao = repositorio.findByGuid(comanda.getGuid());
        if (ormEdicao == null) {
            throw new ModeloNaoEncontradoException(String.format("Comanda com guid [%s] não encontrado.", comanda.getGuid()));
        }

        ComandaORM dadosEdicao = conversorComandaModeloParaORM.converte(comanda);

        ormEdicao.setData(comanda.getData());
        ormEdicao.setNumeroMesa(comanda.getNumeroMesa());

        List<ItemORM> itensParaRemover = ormEdicao.getItens().stream().filter(i -> !dadosEdicao.getItens().contains(i)).collect(Collectors.toList());
        ormEdicao.removeItens(itensParaRemover);
        ormEdicao.adicionaItens(dadosEdicao.getItens());

        repositorio.save(ormEdicao);
    }

    @Override
    public void exclui(Comanda comanda) {
        ComandaORM orm = repositorio.findByGuid(comanda.getGuid());
        if (orm == null) {
            throw new ModeloNaoEncontradoException(String.format("Comanda com guid [%s] não encontrado.", comanda.getGuid()));
        }
        orm.desvinculaItens();
        repositorio.delete(orm);
    }

    @Override
    public Comanda buscaPorGuid(String guid) {
        ComandaORM orm = repositorio.findByGuid(guid);
        if (orm == null) {
            throw new ModeloNaoEncontradoException(String.format("Comanda com guid [%s] não encontrado.", guid));
        }
        return conversorComandaORMParaModelo.converte(orm);
    }

    @Override
    public Collection<Comanda> lista() {
        Collection<ComandaORM> orms = repositorio.listAll();
        return orms.stream().map(conversorComandaORMParaModelo::converte).collect(Collectors.toList());
    }

}
