package br.com.gerenciamento.estoque.services;

import br.com.gerenciamento.estoque.domain.request.AcessoRequest;
import br.com.gerenciamento.estoque.domain.response.AcessoResponse;

import java.util.List;

public interface AcessoService {

    void salvarAcesso(AcessoRequest request);

    void deletar(Long idAcesso, Long usuario) throws Exception;

    List<AcessoResponse> findAll(Long idUsuario, boolean isDesativado) throws Exception;

    AcessoResponse findByLogin(String login, boolean isDesativado) throws Exception;




}
