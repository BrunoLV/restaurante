package br.com.valhala.restaurante.comandas.infra.comanda.repositorio.jpa;

import br.com.valhala.restaurante.comandas.infra.comanda.orm.ComandaORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface RepositorioComandaSpringData extends JpaRepository<ComandaORM, Long> {

    @Query(value = "SELECT c FROM ComandaORM c JOIN FETCH c.itens WHERE c.guid = :guid")
    ComandaORM findByGuid(@Param("guid") String guid);

    @Query(value = "SELECT c FROM ComandaORM c JOIN FETCH c.itens")
    Collection<ComandaORM> listAll();

}
