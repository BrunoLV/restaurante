package br.com.valhala.restaurante.comandas.infra.dbrider.provedores;

import com.github.database.rider.core.api.dataset.DataSetProvider;
import com.github.database.rider.core.dataset.builder.DataSetBuilder;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class ProvedorDatasetListaComandas implements DataSetProvider {

    @Override
    public IDataSet provide() throws DataSetException {
        return new DataSetBuilder()
                .table("tb_comandas")
                    .row()
                        .column("id", 1)
                        .column("guid", "ce1f42c0a17b11ebbcbc0242ac130002")
                        .column("data", Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                        .column("numero_mesa", 1)
                    .row()
                        .column("id", 2)
                        .column("guid", "ce1f42c0a17b11ebbcbc0242ac130003")
                        .column("data", Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                        .column("numero_mesa", 1)
                .table("tb_itens")
                    .row()
                        .column("id", 1)
                        .column("guid", "9498ed66a17c11ebbcbc0242ac130002")
                        .column("nome", "Teste")
                        .column("fabricante", "Teste")
                        .column("valor_unitario", new BigDecimal("100.00"))
                        .column("quantidade", 2)
                        .column("guid_produto", "d86f9b16a17c11ebbcbc0242ac130002")
                        .column("id_comanda", 1)
                    .row()
                        .column("id", 2)
                        .column("guid", "9498ed66a17c11ebbcbc0242ac130003")
                        .column("nome", "Teste-1")
                        .column("fabricante", "Teste-1")
                        .column("valor_unitario", new BigDecimal("100.00"))
                        .column("quantidade", 2)
                        .column("guid_produto", "d86f9b16a17c11ebbcbc0242ac130003")
                        .column("id_comanda", 2)
                .build();
    }

}
