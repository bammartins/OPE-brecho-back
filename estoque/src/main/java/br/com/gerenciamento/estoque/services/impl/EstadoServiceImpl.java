package br.com.gerenciamento.estoque.services.impl;

import br.com.gerenciamento.estoque.entity.Estado;
import br.com.gerenciamento.estoque.repository.EstadoRepository;
import br.com.gerenciamento.estoque.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoServiceImpl implements EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;


    @Override
    public List<Estado> findAll() {
        return estadoRepository.findAllByOrderById();
    }
}
