package br.com.gerenciamento.estoque.services.impl;

import br.com.gerenciamento.estoque.domain.enums.Status;
import br.com.gerenciamento.estoque.domain.enums.TipoMovimentacao;
import br.com.gerenciamento.estoque.domain.request.ProdutoRequest;
import br.com.gerenciamento.estoque.domain.response.ProdutoResponse;
import br.com.gerenciamento.estoque.entity.MovimentacaoProduto;
import br.com.gerenciamento.estoque.entity.Produto;
import br.com.gerenciamento.estoque.repository.AcessoRepository;
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
    public void salvar(ProdutoRequest request) {
        produtoRepository.save(toEntity(request));
    }

    @Override
    public void alterarProduto(ProdutoRequest produto, Long usuario) throws NotFoundException {

        var produtoParaAlterar = toEntity(produto);
        produtoParaAlterar.setProdutoId(produto.getProdutoId());
        produtoParaAlterar.setMovimentacaoProduto(movimentacaoParaAlterar(produto.getProdutoId(), usuario));

        produtoRepository.save(produtoParaAlterar);

    }

    @Override
    public void deletar(Long idProduto, Long usuario) throws NotFoundException {
        var produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new NotFoundException(PRODUTO_NAO_ENCONTRADO));

        delecaoDeDados(produto, usuario);
    }

    private void delecaoDeDados(Produto produto, Long usuario) throws NotFoundException {
        var produtoAlterado = produto;

        produtoAlterado.setStatus(Status.DESATIVO.getNome());
        produtoAlterado.setMovimentacaoProduto(movimentacaoParaDeletar(produto.getProdutoId(), usuario));

        produtoRepository.save(produtoAlterado);
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

        var movimentacaoProduto = new MovimentacaoProduto();
        movimentacaoProduto.setData(LocalDateTime.now());
        movimentacaoProduto.setTipoMovimentacao(TipoMovimentacao.ALTERACAO);
        movimentacaoProduto.setIdAcesso(acessoRepository.findById(usuario)
                .orElseThrow(() -> new NotFoundException(PRODUTO_NAO_ENCONTRADO)));
        movimentacaoProdutoList.add(movimentacaoProduto);

        return movimentacaoProdutoList;
    }

    public Produto toEntity(ProdutoRequest request) {
        var produto = new Produto();

        produto.setCategoria(request.getCategoria());
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
