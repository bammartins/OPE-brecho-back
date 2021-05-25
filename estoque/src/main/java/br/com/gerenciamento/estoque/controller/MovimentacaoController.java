package br.com.gerenciamento.estoque.controller;

import br.com.gerenciamento.estoque.domain.request.MovimentacaoProdutoResquest;
import br.com.gerenciamento.estoque.domain.response.MovimentacaoProdutoResponse;
import br.com.gerenciamento.estoque.services.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    //ADICIONAR E RETIRAR PRODUTO PRECISA TER UMA REGRA DE ATUALIZAR O CAMPO QUANTIDADE
    //NA TABELA DE PRODUTO
    @PostMapping(path = "/salvar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void salvarMovimentacao(@RequestBody MovimentacaoProdutoResquest request) throws Exception {
        movimentacaoService.salvarMovimentacao(request);
    }

    @GetMapping(path = "/todas", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MovimentacaoProdutoResponse> findAll(
    ) {
        return movimentacaoService.findAll();
    }
}
