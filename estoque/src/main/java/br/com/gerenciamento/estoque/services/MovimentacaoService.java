package br.com.gerenciamento.estoque.services;

import br.com.gerenciamento.estoque.domain.request.MovimentacaoProdutoResquest;
import br.com.gerenciamento.estoque.domain.response.MovimentacaoProdutoResponse;

import java.util.List;

public interface MovimentacaoService {

    void salvarMovimentacao(MovimentacaoProdutoResquest movimentacaoProduto) throws Exception;

    List<MovimentacaoProdutoResponse> findAll();
}
