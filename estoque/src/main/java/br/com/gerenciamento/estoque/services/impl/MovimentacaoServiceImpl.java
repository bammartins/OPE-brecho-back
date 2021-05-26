package br.com.gerenciamento.estoque.services.impl;

import br.com.gerenciamento.estoque.domain.enums.Status;
import br.com.gerenciamento.estoque.domain.enums.TipoMovimentacao;
import br.com.gerenciamento.estoque.domain.request.MovimentacaoProdutoResquest;
import br.com.gerenciamento.estoque.domain.response.MovimentacaoProdutoResponse;
import br.com.gerenciamento.estoque.entity.MovimentacaoProduto;
import br.com.gerenciamento.estoque.repository.AcessoRepository;
import br.com.gerenciamento.estoque.repository.FornecedorRepository;
import br.com.gerenciamento.estoque.repository.MovimentacaoProdutoRepository;
import br.com.gerenciamento.estoque.repository.ProdutoRepository;
import br.com.gerenciamento.estoque.services.MovimentacaoService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class MovimentacaoServiceImpl implements MovimentacaoService {

    public static final String PRODUTO_NAO_ENCONTRADO = "Produto nao encontrado";

    public static final String FORNECEDORES_NAO_ENCONTRADO = "Fornecedor nao encontrado";

    public static final String ACESSO_NAO_ENCONTRADO = "ACESSO nao encontrado";


    @Autowired
    private MovimentacaoProdutoRepository movimentacaoProdutoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private AcessoRepository acessoRepository;

    @Override
    public void salvarMovimentacao(MovimentacaoProdutoResquest movimentacaoProduto) throws Exception {

        toEntity(movimentacaoProduto);

    }

    @Override
    public List<MovimentacaoProdutoResponse> findAll() {
        return movimentacaoProdutoRepository.findAll().stream().map(MovimentacaoProduto::toDtoMovimentacao).collect(Collectors.toList());
    }

    private MovimentacaoProduto toEntity(MovimentacaoProdutoResquest movimentacaoProduto) throws Exception {
        var movimentacao = new MovimentacaoProduto();


        if (!isNull(movimentacaoProduto.getProdutoId())) {
            var produto = produtoRepository.findById(movimentacaoProduto.getProdutoId())
                    .orElseThrow(() -> new NotFoundException(PRODUTO_NAO_ENCONTRADO));
            movimentacao.setProduto(produto);
        }
        if (!isNull(movimentacaoProduto.getFornecedorId())) {
            var fornecedor = fornecedorRepository.findById(movimentacaoProduto.getFornecedorId())
                    .orElseThrow(() -> new NotFoundException(FORNECEDORES_NAO_ENCONTRADO));
            movimentacao.setFornecedor(fornecedor);
        }
        var acesso = acessoRepository.findById(movimentacaoProduto.getIdAcesso())
                .orElseThrow(() -> new NotFoundException(ACESSO_NAO_ENCONTRADO));

        movimentacao.setIdAcesso(acesso);
        movimentacao.setData(LocalDateTime.now());
        movimentacao.setTipoMovimentacao(TipoMovimentacao.valueOf(movimentacaoProduto.getTipoMovimentacao()));
        movimentacao.setQuantidade(movimentacaoProduto.getQuantidade());

        if (movimentacao.getTipoMovimentacao().equals(TipoMovimentacao.AUMENTO)) {
            var produtoCompra = produtoRepository.findById(movimentacaoProduto.getProdutoId()).orElseThrow(() -> new NotFoundException(PRODUTO_NAO_ENCONTRADO));

            produtoCompra.setQuantidade(produtoCompra.getQuantidade() + movimentacaoProduto.getQuantidade());

            produtoRepository.save(produtoCompra);

        } else if (movimentacao.getTipoMovimentacao().equals(TipoMovimentacao.RETIRADA)) {

            var produtoCompra = produtoRepository.findById(movimentacaoProduto.getProdutoId()).orElseThrow(() -> new NotFoundException(PRODUTO_NAO_ENCONTRADO));

            produtoCompra.setQuantidade(produtoCompra.getQuantidade() - movimentacaoProduto.getQuantidade());
            if (produtoCompra.getQuantidade() < 0) {
                throw new Exception("Quantidade para retirada menor que o permitido");
            }
            if (produtoCompra.getQuantidade() <= 0) {
                produtoCompra.setStatus(Status.DESATIVO.getNome());
            }
            produtoRepository.save(produtoCompra);
        }

        return movimentacao;
    }
}
