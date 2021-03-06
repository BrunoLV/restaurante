package br.com.valhala.restaurante.comandas.infra.externo.produto;

import br.com.valhala.restaurante.comandas.infra.externo.produto.dto.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "produtos", name = "produtos")
public interface ClienteRestProduto {

    @NewSpan(name = "get-produto-by-guid")
    @RequestMapping(value = "/api-produto/produto/{guid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ProdutoDTO getByGuid(@PathVariable("guid") String guid);

}
