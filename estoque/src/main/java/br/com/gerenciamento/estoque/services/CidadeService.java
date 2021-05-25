package br.com.gerenciamento.estoque.services;

import br.com.gerenciamento.estoque.entity.Cidade;

import java.util.List;

public interface CidadeService {

    List<Cidade> findByEstado(Integer estadoId);

}
