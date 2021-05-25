package br.com.gerenciamento.estoque.services;

import javassist.NotFoundException;

public interface RelatoriosService {


    void criar(Long produto, Long fornecedor) throws NotFoundException;

    void relatorioUsuarios(String tipo) throws NotFoundException;

}
