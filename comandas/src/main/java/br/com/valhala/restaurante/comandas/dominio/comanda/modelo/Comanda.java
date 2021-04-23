package br.com.valhala.restaurante.comandas.dominio.comanda.modelo;

import br.com.valhala.restaurante.dominio.RaizAgregado;
import br.com.valhala.restaurante.infra.geradores.GeradorGuid;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Comanda implements RaizAgregado<Comanda>, Cloneable {

    @NotBlank(message = "{comanda.guid.obrigatorio}")
    private String guid;
    @NotNull(message = "{comanda.data.obrigatorio}")
    private LocalDate data;
    @NotNull(message = "{produto.numero_mesa.obrigatorio}")
    private Integer numeroMesa;

    @Valid
    @NotEmpty(message = "{produto.itens.obrigatorio}")
    private List<Item> itens;

    public static Comanda novo(Integer numeroMesa, Collection<Item> itens) {
        List<Item> listaItens = itens
                .stream()
                .map(i -> Item.novo(i.getProduto(), i.getValorUnitario(), i.getQuantidade()))
                .collect(Collectors.toList());

        return Comanda
                .builder()
                .guid(GeradorGuid.geraGuid())
                .data(LocalDate.now())
                .numeroMesa(numeroMesa)
                .itens(listaItens)
                .build();
    }

    public Collection<Item> getItens() {
        if (itens == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.itens);
    }

    public Comanda removeItem(final Item item) {
        try {
            Comanda novaComanda = (Comanda) this.clone();
            novaComanda.itens.remove(item);
            return novaComanda;
        } catch (CloneNotSupportedException e) {
            this.itens.remove(item);
            return this;
        }
    }

    public Comanda removeItens(final Collection<Item> itens) {
        try {
            Comanda novaComanda = (Comanda) this.clone();
            novaComanda.itens.removeAll(itens);
            return novaComanda;
        } catch (CloneNotSupportedException e) {
            this.itens.removeAll(itens);
            return this;
        }
    }

    public Comanda adicionaItem(final Item item) {
        try {
            Comanda novaComanda = (Comanda) this.clone();
            novaComanda.itens.add(item);
            return novaComanda;
        } catch (CloneNotSupportedException e) {
            this.itens.add(item);
            return this;
        }
    }

    public Comanda adicionaItens(final Collection<Item> itens) {
        try {
            Comanda novaComanda = (Comanda) this.clone();
            novaComanda.itens.addAll(itens);
            return novaComanda;
        } catch (CloneNotSupportedException e) {
            this.itens.addAll(itens);
            return this;
        }
    }

    public BigDecimal calculaTotal() {
        if (this.itens == null || this.itens.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return itens.stream().map(Item::calculaValorTotalItem).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Comanda edita(final Comanda dadosAtualizacao) {

        List<Item> itensAtualizados = dadosAtualizacao.getItens()
                .stream()
                .filter(i -> this.getItens().contains(i))
                .map(i -> this.itens.get(this.itens.indexOf(i)).edita(i))
                .collect(Collectors.toList());


        List<Item> itensNovos = dadosAtualizacao.getItens()
                .stream()
                .filter(i -> !this.itens.contains(i))
                .map(i -> Item.novo(i.getProduto(), i.getValorUnitario(), i.getQuantidade()))
                .collect(Collectors.toList());

        List<Item> itens = new ArrayList<>();
        itens.addAll(itensAtualizados);
        itens.addAll(itensNovos);

        return Comanda.of(this.guid, this.data, dadosAtualizacao.getNumeroMesa(), itens);

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Comanda clone = (Comanda) super.clone();
        List<Item> cloneItens = new ArrayList<>();
        for (Item i : this.itens) {
            cloneItens.add((Item) i.clone());
        }
        clone.itens = cloneItens;
        return clone;
    }

}
