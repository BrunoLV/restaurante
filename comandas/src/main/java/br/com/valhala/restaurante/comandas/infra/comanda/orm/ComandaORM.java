package br.com.valhala.restaurante.comandas.infra.comanda.orm;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "tb_comanda")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ComandaORM implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "guid", length = 100, nullable = false)
    private String guid;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "numero_mesa", nullable = false)
    private Integer numeroMesa;

    @OneToMany(mappedBy = "comanda", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private List<ItemORM> itens = new ArrayList<>();

    public void adicionaItem(ItemORM item) {
        if (this.itens.contains(item)) {
            ItemORM itemORM = this.itens.get(this.itens.indexOf(item));
            itemORM.setNome(item.getNome());
            itemORM.setValorUnitario(item.getValorUnitario());
            itemORM.setQuantidade(item.getQuantidade());
            itemORM.setGuidProduto(item.getGuidProduto());
            itemORM.setComanda(this);
        } else {
            item.setComanda(this);
            this.itens.add(item);
        }
    }

    public void adicionaItens(final Collection<ItemORM> itens) {
        itens.forEach(this::adicionaItem);
    }

    public void removeItem(ItemORM item) {
        if (this.itens.contains(item)) {
            ItemORM itemORM = this.itens.get(this.itens.indexOf(item));
            itemORM.setComanda(null);
            this.itens.remove(itemORM);
        }
    }

    public void removeItens(final Collection<ItemORM> itens) {
        itens.forEach(this::removeItem);
    }

    public void desvinculaItens() {
        this.itens.forEach(i -> i.setComanda(null));
    }

}
