package br.com.gerenciamento.estoque.services.impl;

import br.com.gerenciamento.estoque.domain.enums.Status;
import br.com.gerenciamento.estoque.domain.enums.TipoMovimentacao;
import br.com.gerenciamento.estoque.domain.request.ProdutoRequest;
import br.com.gerenciamento.estoque.domain.response.ProdutoResponse;
import br.com.gerenciamento.estoque.entity.Categoria;
import br.com.gerenciamento.estoque.entity.Fornecedor;
import br.com.gerenciamento.estoque.entity.MovimentacaoProduto;
import br.com.gerenciamento.estoque.entity.Produto;
import br.com.gerenciamento.estoque.repository.AcessoRepository;
import br.com.gerenciamento.estoque.repository.CategoriaRepository;
import br.com.gerenciamento.estoque.repository.FornecedorRepository;
import br.com.gerenciamento.estoque.repository.MovimentacaoProdutoRepository;
import br.com.gerenciamento.estoque.repository.ProdutoRepository;
import br.com.gerenciamento.estoque.services.ProdutoService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    public static final String PRODUTO_EXCLUIDO = "PRODUTO EXCLUIDO";
    public static final String PRODUTO_NAO_ENCONTRADO = "Produto nao encontrado";
    public static final String FORNECEDOR_NAO_ENCONTRADO = "Fornecedor nao encontrado";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MovimentacaoProdutoRepository movimentacaoProdutoRepository;

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;


    @Override
    public ProdutoResponse find(Long id) throws NotFoundException {

        var produtoResponse = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PRODUTO_NAO_ENCONTRADO)).toDtoProdutos();

        if (produtoResponse.getStatus().equals(Status.ATIVO.getNome())) {
            return produtoResponse;

        } else {
            throw new NotFoundException(PRODUTO_NAO_ENCONTRADO);
        }

    }

    @Override
    public List<ProdutoResponse> findAll(boolean isDesativado) throws NotFoundException {

        if (isDesativado) {
            return produtoRepository.findAll().stream().filter(pro -> pro.getStatus().equals(Status.DESATIVO.getNome())).map(Produto::toDtoProdutos).collect(Collectors.toList());
        } else {
            return produtoRepository.findAll().stream().filter(pro -> pro.getStatus().equals(Status.ATIVO.getNome())).map(Produto::toDtoProdutos).collect(Collectors.toList());
        }
    }

    @Override
    public void salvar(ProdutoRequest request, Long acesso) throws NotFoundException {

        var produto = toEntity(request);

        var fornecedor = fornecedorRepository.findById(request.getFornecedorId())
                .orElseThrow(() -> new NotFoundException(FORNECEDOR_NAO_ENCONTRADO));

        var acessoUsuario = acessoRepository.findById(acesso)
                .orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));

        produto.setFornecedor(fornecedor);

        produtoRepository.save(produto);

        var movimentacao = new MovimentacaoProduto();


        var produtoMov = produtoRepository.findByFornecedorIdAndDescricao(request.getFornecedorId(), request.getDescricao());

        movimentacao.setProduto(produtoMov);
        movimentacao.setTipoMovimentacao(TipoMovimentacao.AUMENTO);
        movimentacao.setData(LocalDateTime.now());
        movimentacao.setFornecedor(fornecedor);
        movimentacao.setQuantidade(request.getQuantidade());
        movimentacao.setIdAcesso(acessoUsuario);

        movimentacaoProdutoRepository.save(movimentacao);

    }

    @Override
    public void alterarProduto(ProdutoRequest produtoRequest, Long usuario, Long produtoId) throws NotFoundException {


        var categoria = categoriaRepository.findById(produtoRequest.getCategoria())
                .orElseThrow(() -> new NotFoundException("Categoria nao encontrada"));

        var fornecedor = fornecedorRepository.findById(produtoRequest.getFornecedorId())
                .orElseThrow(() -> new NotFoundException(FORNECEDOR_NAO_ENCONTRADO));

        var produtoParaAlterar = toEntity(produtoRequest);
        produtoParaAlterar.setProdutoId(produtoId);
        produtoParaAlterar.setDescricao(produtoRequest.getDescricao());
        produtoParaAlterar.setMarca(produtoRequest.getMarca());
        produtoParaAlterar.setPreco(produtoRequest.getPreco());
        produtoParaAlterar.setTamanho(produtoRequest.getTamanho());
        produtoParaAlterar.setModelo(produtoRequest.getModelo());
        produtoParaAlterar.setCategoria(categoria);
        produtoParaAlterar.setCor(produtoRequest.getCor());

        produtoRepository.save(produtoParaAlterar);


        var produtoMov = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new NotFoundException(PRODUTO_NAO_ENCONTRADO));

        var movimentacaoProduto = new MovimentacaoProduto();

        movimentacaoProduto.setData(LocalDateTime.now());
        movimentacaoProduto.setTipoMovimentacao(TipoMovimentacao.ALTERACAO);
        movimentacaoProduto.setIdAcesso(acessoRepository.findById(usuario)
                .orElseThrow(() -> new NotFoundException(PRODUTO_NAO_ENCONTRADO)));
        movimentacaoProduto.setProduto(produtoMov);
        movimentacaoProduto.setQuantidade(produtoMov.getQuantidade());
        movimentacaoProduto.setFornecedor(fornecedor);

        movimentacaoProdutoRepository.save(movimentacaoProduto);


    }

    @Override
    public void deletar(Long idProduto, Long usuario) throws NotFoundException {
        var produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new NotFoundException(PRODUTO_NAO_ENCONTRADO));

        produto.setStatus(Status.DESATIVO.getNome());
        produtoRepository.save(produto);

        var movimentacaoProduto = new MovimentacaoProduto();
        movimentacaoProduto.setData(LocalDateTime.now());
        movimentacaoProduto.setTipoMovimentacao(TipoMovimentacao.DELETADO);
        movimentacaoProduto.setIdAcesso(acessoRepository.findById(usuario)
                .orElseThrow(() -> new NotFoundException("Usuario nao encontrado")));
        movimentacaoProduto.setProduto(produto);
        movimentacaoProdutoRepository.save(movimentacaoProduto);

    }

    private void delecaoDeDados(Produto produto, Long usuario) throws NotFoundException {


    }

    private List<MovimentacaoProduto> movimentacaoParaDeletar(Long id, Long usuario) throws NotFoundException {
        List<MovimentacaoProduto> movimentacaoProdutoList = new ArrayList<>();


        return movimentacaoProdutoList;
    }

    public Produto toEntity(ProdutoRequest request) throws NotFoundException {
        var produto = new Produto();

        var categoria = categoriaRepository.findById(request.getCategoria())
                .orElseThrow(() -> new NotFoundException("Categoria Nao encontrada"));

        produto.setCategoria(categoria);
        produto.setDescricao(request.getDescricao());
        produto.setMarca(request.getMarca());
        produto.setQuantidade(request.getQuantidade());
        produto.setPreco(request.getPreco());
        produto.setPreco(request.getPreco());
        produto.setTamanho(request.getTamanho());
        produto.setModelo(request.getModelo());
        produto.setCor(request.getCor());
        produto.setStatus(Status.ATIVO.getNome());

        return produto;
    }
}
