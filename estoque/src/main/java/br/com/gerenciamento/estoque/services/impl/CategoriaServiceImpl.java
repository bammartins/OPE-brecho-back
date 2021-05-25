package br.com.gerenciamento.estoque.services.impl;

import br.com.gerenciamento.estoque.domain.enums.Status;
import br.com.gerenciamento.estoque.domain.request.CategoriaRequest;
import br.com.gerenciamento.estoque.domain.response.CategoriaResponse;
import br.com.gerenciamento.estoque.entity.Categoria;
import br.com.gerenciamento.estoque.repository.CategoriaRepository;
import br.com.gerenciamento.estoque.services.CategoriaService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public CategoriaResponse find(Long id) throws NotFoundException {

        return categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada")).toDtoCategoria();
    }

    @Override
    public List<CategoriaResponse> findAll(boolean isDesativado) {
        if (isDesativado) {
            return categoriaRepository.findAll().stream().filter(cat -> cat.getStatus().equals(Status.DESATIVO.getNome()))
                    .map(Categoria::toDtoVazio).collect(Collectors.toList());

        } else {
            return categoriaRepository.findAll().stream().filter(cat -> cat.getStatus().equals(Status.ATIVO.getNome()))
                    .map(Categoria::toDtoVazio).collect(Collectors.toList());
        }
    }


    @Override
    public void salvar(CategoriaRequest categoriaRequest) {

        var categoria = toEntityCategoria(categoriaRequest);
        categoriaRepository.save(categoria);
    }

    public Categoria toEntityCategoria(CategoriaRequest request) {
        var mapper = new ModelMapper();
        return mapper.map(request, Categoria.class);
    }
}
