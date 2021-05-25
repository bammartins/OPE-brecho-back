package br.com.gerenciamento.estoque.services;

import br.com.gerenciamento.estoque.domain.request.ContatoRequest;
import br.com.gerenciamento.estoque.domain.response.ContatoResponse;
import javassist.NotFoundException;

import java.util.List;

public interface ContatoService {


    ContatoResponse find (Long idContato) throws NotFoundException;

    List<ContatoResponse> findAll () throws NotFoundException;

    void cadastrar(ContatoRequest contatoRequest) throws NotFoundException;

    void alterarProduto(ContatoRequest contatoRequest, Long usuario) throws NotFoundException;

    void deletar(Long idProduto, Long usuario) throws Exception;
}
