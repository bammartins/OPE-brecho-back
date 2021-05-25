package br.com.gerenciamento.estoque.services.impl;

import br.com.gerenciamento.estoque.entity.Cidade;
import br.com.gerenciamento.estoque.repository.CidadeRepository;
import br.com.gerenciamento.estoque.services.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeServiceImpl implements CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Override
    public List<Cidade> findByEstado(Integer estadoId) {
        return cidadeRepository.findCidade(estadoId);
    }
}
