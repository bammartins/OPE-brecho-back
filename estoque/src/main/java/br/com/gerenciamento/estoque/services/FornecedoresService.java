package br.com.gerenciamento.estoque.services;

import br.com.gerenciamento.estoque.domain.request.FornecedoresRequest;
import br.com.gerenciamento.estoque.domain.response.FornecedoresResponse;
import javassist.NotFoundException;

import java.util.List;

public interface FornecedoresService {

    FornecedoresResponse find (Long idFornecedores) throws NotFoundException;

    List<FornecedoresResponse> findAll () throws NotFoundException;

    void cadastrar(FornecedoresRequest fornecedoresRequest) throws NotFoundException;

    void alterarProduto(FornecedoresRequest produto, Long usuario) throws NotFoundException;

    void deletar(Long idProduto, Long usuario) throws NotFoundException;
}
