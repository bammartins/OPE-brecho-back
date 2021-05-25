package br.com.gerenciamento.estoque.services.impl;

import br.com.gerenciamento.estoque.domain.enums.Status;
import br.com.gerenciamento.estoque.domain.enums.TipoMovimentacao;
import br.com.gerenciamento.estoque.domain.request.FornecedoresRequest;
import br.com.gerenciamento.estoque.domain.response.FornecedoresResponse;
import br.com.gerenciamento.estoque.entity.Fornecedores;
import br.com.gerenciamento.estoque.entity.MovimentacaoProduto;
import br.com.gerenciamento.estoque.repository.AcessoRepository;
import br.com.gerenciamento.estoque.repository.CidadeRepository;
import br.com.gerenciamento.estoque.repository.EstadoRepository;
import br.com.gerenciamento.estoque.repository.FornecedoresRepository;
import br.com.gerenciamento.estoque.repository.MovimentacaoProdutoRepository;
import br.com.gerenciamento.estoque.services.FornecedoresService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FornecedoresServiceImpl implements FornecedoresService {

    @Autowired
    private FornecedoresRepository fornecedoresRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private MovimentacaoProdutoRepository movimentacaoProdutoRepository;

    @Autowired
    private AcessoRepository acessoRepository;


    @Override
    public FornecedoresResponse find(Long idFornecedores) throws NotFoundException {
        Fornecedores fornecedor = fornecedoresRepository.findById(idFornecedores)
                .orElseThrow(() -> new NotFoundException("Fornecedor nao encontrado"));

        return toFornecedoresResponse(fornecedor);

    }

    @Override
    public List<FornecedoresResponse> findAll() throws NotFoundException {
        return fornecedoresRepository.findAll().stream().filter(f -> f.getStatus().equals(Status.ATIVO.getNome())).map(Fornecedores::toDtoFornecedores).collect(Collectors.toList());
    }

    @Override
    public void cadastrar(FornecedoresRequest fornecedoresRequest) throws NotFoundException {

        var fornecedor = toFornecedores(fornecedoresRequest);
        fornecedoresRepository.save(fornecedor);
    }

    @Override
    public void alterarProduto(FornecedoresRequest fornecedoresRequest, Long usuario) throws NotFoundException {
        var fornecedoresParaAlterar = toFornecedores(fornecedoresRequest);
        fornecedoresParaAlterar.setId(fornecedoresRequest.getIdFornecedor());
        fornecedoresParaAlterar.setMovimentacaoProdutos(movimentacaoParaAlterar(fornecedoresRequest.getIdFornecedor(), usuario));

        fornecedoresRepository.save(fornecedoresParaAlterar);
    }

    @Override
    public void deletar(Long idFornecedor, Long usuario) throws NotFoundException {

        Fornecedores fornecedor = fornecedoresRepository.findById(idFornecedor)
                .orElseThrow(() -> new NotFoundException("Fornecedor nao encontrado"));

        deletarFornecedor(fornecedor, usuario);


    }

    private void deletarFornecedor(Fornecedores fornecedor, Long usuario) throws NotFoundException {

        fornecedor.setStatus(Status.DESATIVO.getNome());
        fornecedor.setMovimentacaoProdutos(movimentacaoParaDeletar(fornecedor.getId(), usuario));

    }

    private List<MovimentacaoProduto> movimentacaoParaDeletar(Long id, Long usuario) throws NotFoundException {
        List<MovimentacaoProduto> movimentacaoProdutoList = new ArrayList<>();

        var movimentacaoProduto = new MovimentacaoProduto();
        movimentacaoProduto.setData(LocalDateTime.now());
        movimentacaoProduto.setTipoMovimentacao(TipoMovimentacao.DELETADO);
        movimentacaoProduto.setIdAcesso(acessoRepository.findById(usuario)
                .orElseThrow(() -> new NotFoundException("Usuario nao encontrado")));
        movimentacaoProdutoList.add(movimentacaoProduto);

        return movimentacaoProdutoList;
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

    private Fornecedores toFornecedores(FornecedoresRequest fornecedoresRequest) throws NotFoundException {

        var fornecedor = new Fornecedores();

        fornecedor.setCep(fornecedoresRequest.getCep());
        fornecedor.setBairro(fornecedoresRequest.getBairro());

        fornecedor.setCidade(cidadeRepository.findById(fornecedoresRequest.getCidade())
                .orElseThrow(() -> new NotFoundException("Cidade Nao encontrada")));

        fornecedor.setCnpj(fornecedoresRequest.getCnpj());
        fornecedor.setComplemento(fornecedoresRequest.getComplemento());
        fornecedor.setResponsavel(fornecedoresRequest.getResponsavel());
        fornecedor.setLogradouro(fornecedoresRequest.getLogradouro());
        fornecedor.setNumero(fornecedoresRequest.getNumero());
        fornecedor.setNomeFantasia(fornecedoresRequest.getNomeFantasia());
        fornecedor.setRazaoSocial(fornecedoresRequest.getRazaoSocial());

        return fornecedor;
    }

    private FornecedoresResponse toFornecedoresResponse(Fornecedores fornecedor) {

        var fornecedoresResponse = new FornecedoresResponse();

        fornecedoresResponse.setCep(fornecedor.getCep());
        fornecedoresResponse.setBairro(fornecedor.getBairro());
        //fornecedoresResponse.setCidade(fornecedor.getCidade().getNome());
        //fornecedoresResponse.setEstado(fornecedor.getEstado().getNome());
        fornecedoresResponse.setCnpj(fornecedor.getCnpj());
        fornecedoresResponse.setComplemento(fornecedor.getComplemento());
        fornecedoresResponse.setResponsavel(fornecedor.getResponsavel());
        fornecedoresResponse.setLogradouro(fornecedor.getLogradouro());
        fornecedoresResponse.setNumero(fornecedor.getNumero());
        fornecedoresResponse.setId(fornecedor.getId());
        fornecedoresResponse.setNomeFantasia(fornecedor.getNomeFantasia());
        fornecedoresResponse.setRazaoSocial(fornecedor.getRazaoSocial());

        for (var i = 0; i <= fornecedor.getContatos().size(); i++) {
            fornecedoresResponse.setContatos(i);
        }

        for (var i = 0; i <= fornecedor.getMovimentacaoProdutos().size(); i++) {
            fornecedoresResponse.setMovimentacaoProdutos(i);
        }

        return fornecedoresResponse;

    }
}
