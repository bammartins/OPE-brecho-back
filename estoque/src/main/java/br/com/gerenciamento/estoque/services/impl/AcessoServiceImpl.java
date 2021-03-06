package br.com.gerenciamento.estoque.services.impl;

import br.com.gerenciamento.estoque.domain.enums.Perfil;
import br.com.gerenciamento.estoque.domain.enums.Status;
import br.com.gerenciamento.estoque.domain.request.AcessoRequest;
import br.com.gerenciamento.estoque.domain.response.AcessoResponse;
import br.com.gerenciamento.estoque.entity.Acesso;
import br.com.gerenciamento.estoque.entity.MovimentacaoProduto;
import br.com.gerenciamento.estoque.repository.AcessoRepository;
import br.com.gerenciamento.estoque.services.AcessoService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcessoServiceImpl implements AcessoService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AcessoRepository acessoRepository;

    @Override
    public void salvarAcesso(AcessoRequest request) {

        var acesso = toEntity(request);

        List<MovimentacaoProduto> movimentacaoProdutoList = new ArrayList<>();
        acesso.setMovimentacaoProduto(movimentacaoProdutoList);
        acessoRepository.save(acesso);
    }

    @Override
    public void deletar(Long idAcesso, Long usuario) throws Exception {
        var acesso = acessoRepository.findById(idAcesso)
                .orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));
        if (acesso.getPerfis().contains(Perfil.ADMIN) && acesso.getStatus().equals(Status.ATIVO.getNome())) {
            Acesso usuarioDeletar = acessoRepository.findById(usuario)
                    .orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));
            usuarioDeletar.setStatus(Status.DESATIVO.getNome());
            acessoRepository.save(usuarioDeletar);
        } else {
            throw new Exception("Voce nao tem permissao para isso");
        }
    }

    @Override
    public List<AcessoResponse> findAll(Long idUsuario, boolean isDesativado) throws Exception {
        var acesso = acessoRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));
        if (acesso.getPerfis().contains(Perfil.ADMIN) && acesso.getStatus().equals(Status.ATIVO.getNome())) {
            if (!isDesativado)
                return acessoRepository.findAll().stream().filter(ace -> ace.getStatus().equals(Status.ATIVO.getNome())).map(Acesso::toDtoAcesso).collect(Collectors.toList());
            else {
                return acessoRepository.findAll().stream().filter(ace -> ace.getStatus().equals(Status.DESATIVO.getNome())).map(Acesso::toDtoAcesso).collect(Collectors.toList());

            }
        } else {
            throw new Exception("Voce nao tem permissao para isso");
        }

    }

    @Override
    public AcessoResponse findByLogin(String login, boolean isDesativado) throws Exception {
        return acessoRepository.findByLogin(login).toDtoAcesso();
    }

    private Acesso toEntity(AcessoRequest request) {
        var acesso = new Acesso();

        acesso.setLogin(request.getLogin());
        acesso.setSenha(bCryptPasswordEncoder.encode(request.getSenha()));
        acesso.addPerfil(Perfil.toEnum(request.getPerfis()));
        acesso.setStatus(Status.ATIVO.getNome());

        return acesso;
    }

}
