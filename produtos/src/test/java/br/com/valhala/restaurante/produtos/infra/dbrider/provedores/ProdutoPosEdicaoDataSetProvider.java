package br.com.valhala.restaurante.produtos.infra.dbrider.provedores;

import com.github.database.rider.core.api.dataset.DataSetProvider;
import com.github.database.rider.core.dataset.builder.DataSetBuilder;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class ProdutoPosEdicaoDataSetProvider implements DataSetProvider {

    @Override
    public IDataSet provide() throws DataSetException {
        DataSetBuilder builder = new DataSetBuilder();

        return new DataSetBuilder()
                .table("tb_produtos")
                .columns("id", "guid", "nome", "descricao", "data_cadastro", "valor", "fabricante")
                .values(null, "8fa845b01c134bcf9de0de1ec2ff8765", "Teste-edicao", "Teste-edicao", Date.from(LocalDate.of(2021, 03, 26).atStartOfDay(ZoneId.systemDefault()).toInstant()), new BigDecimal("101.00"), "Teste-edicao")
                .build();


    }
}
