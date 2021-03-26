package br.com.valhala.restaurante.dominio.repositorio;

import br.com.valhala.restaurante.dominio.RaizAgregado;

import java.util.Collection;
import java.util.Optional;

public interface Repositorio<A extends RaizAgregado<A>> {

    void cria(A a);

    void edita(A a);

    void exclui(A a);

    Optional<A> buscaPorGuid(String guid);

    Collection<A> lista();

}
