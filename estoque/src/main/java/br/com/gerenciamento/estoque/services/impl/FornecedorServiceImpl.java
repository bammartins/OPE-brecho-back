package br.com.gerenciamento.estoque.services.impl;

import br.com.gerenciamento.estoque.domain.enums.Status;
import br.com.gerenciamento.estoque.domain.enums.TipoMovimentacao;
import br.com.gerenciamento.estoque.domain.request.FornecedorRequest;
import br.com.gerenciamento.estoque.domain.response.FornecedorResponse;
import br.com.gerenciamento.estoque.entity.Fornecedor;
import br.com.gerenciamento.estoque.entity.MovimentacaoProduto;
import br.com.gerenciamento.estoque.repository.AcessoRepository;
import br.com.gerenciamento.estoque.repository.CidadeRepository;
import br.com.gerenciamento.estoque.repository.EstadoRepository;
import br.com.gerenciamento.estoque.repository.FornecedorRepository;
import br.com.gerenciamento.estoque.repository.MovimentacaoProdutoRepository;
import br.com.gerenciamento.estoque.services.FornecedorService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private MovimentacaoProdutoRepository movimentacaoProdutoRepository;

    @Autowired
    private AcessoRepository acessoRepository;


    @Override
    public FornecedorResponse find(Long idFornecedor) throws NotFoundException {
        Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor)
                .orElseThrow(() -> new NotFoundException("Fornecedor nao encontrado"));

        return toFornecedorResponse(fornecedor);

    }

    @Override
    public List<FornecedorResponse> findAll(boolean isDesativado) throws NotFoundException {

        if (isDesativado) {
            return fornecedorRepository.findAll().stream().filter(f -> f.getStatus().equals(Status.DESATIVO.getNome())).map(Fornecedor::toDtoFornecedor).collect(Collectors.toList());
        } else {
            return fornecedorRepository.findAll().stream().filter(f -> f.getStatus().equals(Status.ATIVO.getNome())).map(Fornecedor::toDtoFornecedor).collect(Collectors.toList());
        }
    }

    @Override
    public void cadastrar(FornecedorRequest fornecedorRequest) throws NotFoundException {

        var fornecedor = toFornecedor(fornecedorRequest);
        fornecedorRepository.save(fornecedor);
    }

    @Override
    public void alterar(FornecedorRequest fornecedorRequest, Long usuario, Long fornecedorId) throws NotFoundException {

        var fornecedorParaAlterar = new Fornecedor();

        fornecedorParaAlterar.setId(fornecedorId);

        fornecedorParaAlterar.setCep(fornecedorRequest.getCep());
        fornecedorParaAlterar.setBairro(fornecedorRequest.getBairro());

        fornecedorParaAlterar.setCidade(cidadeRepository.findById(fornecedorRequest.getCidade())
                .orElseThrow(() -> new NotFoundException("Cidade Nao encontrada")));

        fornecedorParaAlterar.setCnpj(fornecedorRequest.getCnpj());
        fornecedorParaAlterar.setComplemento(fornecedorRequest.getComplemento());
        fornecedorParaAlterar.setLogradouro(fornecedorRequest.getLogradouro());
        fornecedorParaAlterar.setNumero(fornecedorRequest.getNumero());
        fornecedorParaAlterar.setNomeFantasia(fornecedorRequest.getNomeFantasia());
        fornecedorParaAlterar.setRazaoSocial(fornecedorRequest.getRazaoSocial());
        fornecedorParaAlterar.setEmail(fornecedorRequest.getEmail());
        fornecedorParaAlterar.setResponsavel(fornecedorRequest.getResponsavel());
        fornecedorParaAlterar.setTelefone(fornecedorRequest.getTelefone());

        fornecedorRepository.save(fornecedorParaAlterar);
    }

    @Override
    public void deletar(Long idFornecedor, Long usuario) throws NotFoundException {

        var fornecedor = fornecedorRepository.findById(idFornecedor)
                .orElseThrow(() -> new NotFoundException("Fornecedor nao encontrado"));

        fornecedor.setStatus(Status.DESATIVO.getNome());

        fornecedorRepository.save(fornecedor);
    }

    private List<MovimentacaoProduto> movimentacaoParaAlterar(Long id, Long usuario) throws NotFoundException {
        List<MovimentacaoProduto> movimentacaoProdutoList = new ArrayList<>();

        var movimentacaoProduto = movimentacaoProdutoRepository.findbyFornecedorId(id);
        movimentacaoProduto.setData(LocalDateTime.now());
        movimentacaoProduto.setTipoMovimentacao(TipoMovimentacao.ALTERACAO);
        movimentacaoProduto.setIdAcesso(acessoRepository.findById(usuario)
                .orElseThrow(() -> new NotFoundException("Produto nao encontrado")));
        movimentacaoProdutoList.add(movimentacaoProduto);

        return movimentacaoProdutoList;
    }

    private Fornecedor toFornecedor(FornecedorRequest fornecedorRequest) throws NotFoundException {

        var fornecedor = new Fornecedor();

        fornecedor.setCep(fornecedorRequest.getCep());
        fornecedor.setBairro(fornecedorRequest.getBairro());

        fornecedor.setCidade(cidadeRepository.findById(fornecedorRequest.getCidade())
                .orElseThrow(() -> new NotFoundException("Cidade Nao encontrada")));

        fornecedor.setCnpj(fornecedorRequest.getCnpj());
        fornecedor.setComplemento(fornecedorRequest.getComplemento());
        fornecedor.setLogradouro(fornecedorRequest.getLogradouro());
        fornecedor.setNumero(fornecedorRequest.getNumero());
        fornecedor.setNomeFantasia(fornecedorRequest.getNomeFantasia());
        fornecedor.setRazaoSocial(fornecedorRequest.getRazaoSocial());
        fornecedor.setEmail(fornecedorRequest.getEmail());
        fornecedor.setResponsavel(fornecedorRequest.getResponsavel());
        fornecedor.setTelefone(fornecedorRequest.getTelefone());
        fornecedor.setStatus(Status.ATIVO.getNome());

        return fornecedor;
    }

    private FornecedorResponse toFornecedorResponse(Fornecedor fornecedor) {

        var fornecedorResponse = new FornecedorResponse();

        fornecedorResponse.setCep(fornecedor.getCep());
        fornecedorResponse.setBairro(fornecedor.getBairro());
        fornecedorResponse.setCidade(fornecedor.getCidade().getNome());
        fornecedorResponse.setEstado(fornecedor.getCidade().getEstado().getNome());
        fornecedorResponse.setCnpj(fornecedor.getCnpj());
        fornecedorResponse.setComplemento(fornecedor.getComplemento());
        fornecedorResponse.setLogradouro(fornecedor.getLogradouro());
        fornecedorResponse.setNumero(fornecedor.getNumero());
        fornecedorResponse.setId(fornecedor.getId());
        fornecedorResponse.setNomeFantasia(fornecedor.getNomeFantasia());
        fornecedorResponse.setRazaoSocial(fornecedor.getRazaoSocial());

        return fornecedorResponse;

    }
}
