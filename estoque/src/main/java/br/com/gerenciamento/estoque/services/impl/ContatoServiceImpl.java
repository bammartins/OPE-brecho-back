package br.com.gerenciamento.estoque.services.impl;

import br.com.gerenciamento.estoque.domain.enums.Perfil;
import br.com.gerenciamento.estoque.domain.enums.Status;
import br.com.gerenciamento.estoque.domain.request.ContatoRequest;
import br.com.gerenciamento.estoque.domain.response.ContatoResponse;
import br.com.gerenciamento.estoque.entity.Acesso;
import br.com.gerenciamento.estoque.entity.Contato;
import br.com.gerenciamento.estoque.repository.AcessoRepository;
import br.com.gerenciamento.estoque.repository.ContatoRepository;
import br.com.gerenciamento.estoque.repository.FornecedoresRepository;
import br.com.gerenciamento.estoque.services.ContatoService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.gerenciamento.estoque.entity.Contato.toDtoContatoEntity;

@Service
public class ContatoServiceImpl implements ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private FornecedoresRepository fornecedoresRepository;

    @Autowired
    private AcessoRepository acessoRepository;

    @Override
    public ContatoResponse find(Long idFornecedores) throws NotFoundException {
        var contato = contatoRepository.findById(idFornecedores)
                .orElseThrow(() -> new NotFoundException("Fornecedor nao encontrado"));

        return toDtoContatoEntity(contato);

    }

    @Override
    public List<ContatoResponse> findAll() throws NotFoundException {
        return contatoRepository.findAll().stream().filter(f -> f.getStatus().equals(Status.ATIVO.getNome())).map(Contato::toDtoContato).collect(Collectors.toList());
    }

    @Override
    public void cadastrar(ContatoRequest contatoRequest) throws NotFoundException {
        contatoRepository.save(toEntity(contatoRequest));
    }

    private Contato toEntity(ContatoRequest contatoRequest) throws NotFoundException {
        var contato = new Contato();
        contato.setStatus(Status.ATIVO.getNome());
        contato.setEmail(contatoRequest.getEmail());
        contato.setTelefone(contatoRequest.getTelefone());

        contato.setFornecedores(fornecedoresRepository.findById(contatoRequest.getFornecedores())
                .orElseThrow(() -> new NotFoundException("Fornecedor nao encontrado")));

        return contato;
    }

    @Override
    public void alterarProduto(ContatoRequest contatoRequest, Long usuario) throws NotFoundException {

    }

    @Override
    public void deletar(Long idContato, Long usuario) throws Exception {

        var contato = contatoRepository.findById(idContato)
                .orElseThrow(() -> new NotFoundException("Contato nao encontrado"));
        var acesso = acessoRepository.findById(usuario)
                .orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));

        if (acesso.getPerfis().equals(Perfil.ADMIN) && acesso.getStatus().equals(Status.ATIVO.getNome())) {
            contato.setStatus(Status.DESATIVO.getNome());
        } else {
            throw new Exception("Voce nao tem permissao para isso");
        }
    }

}
