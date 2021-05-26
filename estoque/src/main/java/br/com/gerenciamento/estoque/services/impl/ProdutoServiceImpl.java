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
    public void salvar(ProdutoRequest request) throws NotFoundException {
        produtoRepository.save(toEntity(request));
    }

    @Override
    public void alterarProduto(ProdutoRequest produtoRequest, Long usuario) throws NotFoundException {


        var categoria = categoriaRepository.findById(produtoRequest.getCategoria())
                .orElseThrow(() -> new NotFoundException("Categoria nao encontrada"));

        var produtoParaAlterar = toEntity(produtoRequest);
        produtoParaAlterar.setDescricao(produtoRequest.getDescricao());
        produtoParaAlterar.setMarca(produtoRequest.getMarca());
        produtoParaAlterar.setPreco(produtoRequest.getPreco());
        produtoParaAlterar.setTamanho(produtoRequest.getTamanho());
        produtoParaAlterar.setModelo(produtoRequest.getModelo());
        produtoParaAlterar.setCategoria(categoria);
        produtoParaAlterar.setCor(produtoRequest.getCor());

        var movimentacaoProduto = new MovimentacaoProduto();

        movimentacaoProduto.setData(LocalDateTime.now());
        movimentacaoProduto.setTipoMovimentacao(TipoMovimentacao.ALTERACAO);
        movimentacaoProduto.setIdAcesso(acessoRepository.findById(usuario)
                .orElseThrow(() -> new NotFoundException(PRODUTO_NAO_ENCONTRADO)));

        movimentacaoProduto.setProduto(produtoParaAlterar);

    //    Fornecedor fornecedor = fornecedorRepository.findFornecedorByProduto(produtoParaAlterar.getProdutoId());

      //  movimentacaoProduto.setFornecedor(fornecedor);

        movimentacaoProdutoRepository.save(movimentacaoProduto);

        List<MovimentacaoProduto> movimentacaoProdutoList = movimentacaoProdutoRepository.findbyProdutoProdutoId(produtoRequest.getProdutoId());

        movimentacaoProdutoList.add(movimentacaoProduto);

        produtoParaAlterar.setMovimentacaoProduto(movimentacaoProdutoList);

        produtoRepository.save(produtoParaAlterar);

    }

    @Override
    public void deletar(Long idProduto, Long usuario) throws NotFoundException {
        var produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new NotFoundException(PRODUTO_NAO_ENCONTRADO));

        delecaoDeDados(produto, usuario);
    }

    private void delecaoDeDados(Produto produto, Long usuario) throws NotFoundException {

        produto.setStatus(Status.DESATIVO.getNome());
        produto.setMovimentacaoProduto(movimentacaoParaDeletar(produto.getProdutoId(), usuario));

        produtoRepository.save(produto);
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
