package br.com.gerenciamento.estoque.services.impl;

import br.com.gerenciamento.estoque.domain.enums.Perfil;
import br.com.gerenciamento.estoque.domain.enums.Status;
import br.com.gerenciamento.estoque.domain.request.CategoriaRequest;
import br.com.gerenciamento.estoque.domain.response.CategoriaResponse;
import br.com.gerenciamento.estoque.entity.Categoria;
import br.com.gerenciamento.estoque.repository.AcessoRepository;
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

    @Autowired
    private AcessoRepository acessoRepository;

    @Override
    public CategoriaResponse find(Long id) throws NotFoundException {

        return categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada")).toDtoCategoria();
    }

    @Override
    public List<CategoriaResponse> findAll(boolean isDesativado) {
        if (isDesativado) {
            return categoriaRepository.findAll().stream().filter(cat -> cat.getStatus().equals(Status.DESATIVO.getNome()))
                    .map(Categoria::toDtoCategoria).collect(Collectors.toList());

        } else {
            return categoriaRepository.findAll().stream().filter(cat -> cat.getStatus().equals(Status.ATIVO.getNome()))
                    .map(Categoria::toDtoCategoria).collect(Collectors.toList());
        }
    }

    @Override
    public void deletar(Long idCategoria, Long usuario) throws Exception {
        var categoia = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new NotFoundException("Categoria nao encontrada"));
        var acesso = acessoRepository.findById(usuario)
                .orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));

        if (acesso.getPerfis().contains(Perfil.ADMIN) && acesso.getStatus().equals(Status.ATIVO.getNome())) {
            categoia.setStatus(Status.DESATIVO.getNome());
            categoriaRepository.save(categoia);
        } else {
            throw new Exception("Voce nao tem permissao para isso");
        }
    }

    @Override
    public void alterarContato(CategoriaRequest categoriaRequest, Long usuario) throws NotFoundException {
        var categoria = categoriaRepository.findById(categoriaRequest.getIdCategoria())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        categoria.setNome(categoria.getNome());

        categoriaRepository.save(categoria);
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
