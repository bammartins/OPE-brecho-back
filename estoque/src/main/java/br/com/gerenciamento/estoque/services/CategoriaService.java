package br.com.gerenciamento.estoque.services;

import br.com.gerenciamento.estoque.domain.request.CategoriaRequest;
import br.com.gerenciamento.estoque.domain.response.CategoriaResponse;
import javassist.NotFoundException;

import java.util.List;

public interface CategoriaService {

    CategoriaResponse find(Long id) throws NotFoundException;

    void salvar (CategoriaRequest categoriaRequest);

    List<CategoriaResponse> findAll(boolean isDesativado);
}
