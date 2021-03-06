package br.com.gerenciamento.estoque.services;

import br.com.gerenciamento.estoque.domain.request.ProdutoRequest;
import br.com.gerenciamento.estoque.domain.response.ProdutoResponse;
import javassist.NotFoundException;

import java.util.List;

public interface ProdutoService {
    ProdutoResponse find(Long id) throws NotFoundException;

    List<ProdutoResponse> findAll(boolean isDesativado) throws NotFoundException;

    void salvar(ProdutoRequest produto, Long acesso) throws NotFoundException;

    void alterarProduto(ProdutoRequest produto, Long usuario, Long produtoId) throws NotFoundException;

    void deletar(Long idProduto, Long usuario) throws NotFoundException;

}
