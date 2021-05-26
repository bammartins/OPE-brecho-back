package br.com.gerenciamento.estoque.services;

import br.com.gerenciamento.estoque.domain.request.FornecedorRequest;
import br.com.gerenciamento.estoque.domain.response.FornecedorResponse;
import javassist.NotFoundException;

import java.util.List;

public interface FornecedorService {

    FornecedorResponse find(Long idFornecedor) throws NotFoundException;

    List<FornecedorResponse> findAll(boolean isDesativado) throws NotFoundException;

    void cadastrar(FornecedorRequest fornecedorRequest) throws NotFoundException;

    void alterar(FornecedorRequest produto, Long usuario, Long fornecedorId) throws NotFoundException;

    void deletar(Long idProduto, Long usuario) throws NotFoundException;
}
