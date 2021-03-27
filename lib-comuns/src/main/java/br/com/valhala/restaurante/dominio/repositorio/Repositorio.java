package br.com.valhala.restaurante.dominio.repositorio;

import br.com.valhala.restaurante.dominio.RaizAgregado;

import java.util.Collection;

public interface Repositorio<A extends RaizAgregado<A>> {

    void cria(A a);

    void edita(A a);

    void exclui(A a);

    A buscaPorGuid(String guid);

    Collection<A> lista();

}
