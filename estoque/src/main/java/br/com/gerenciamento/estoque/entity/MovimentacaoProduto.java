package br.com.gerenciamento.estoque.entity;

import br.com.gerenciamento.estoque.domain.enums.TipoMovimentacao;
import br.com.gerenciamento.estoque.domain.response.MovimentacaoProdutoResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class MovimentacaoProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movimentacaoId;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Fornecedor fornecedor;

    @ManyToOne
    private Acesso idAcesso;

    private TipoMovimentacao tipoMovimentacao;

    private Integer quantidade;

    private LocalDateTime data;

    public MovimentacaoProduto() {
    }

    public MovimentacaoProduto(Long movimentacaoId, Produto produto, Fornecedor fornecedor, Acesso idAcesso, TipoMovimentacao tipoMovimentacao, Integer quantidade, LocalDateTime data) {
        this.movimentacaoId = movimentacaoId;
        this.produto = produto;
        this.fornecedor = fornecedor;
        this.idAcesso = idAcesso;
        this.tipoMovimentacao = tipoMovimentacao;
        this.quantidade = quantidade;
        this.data = data;
    }

    public Long getMovimentacaoId() {
        return movimentacaoId;
    }

    public void setMovimentacaoId(Long movimentacaoId) {
        this.movimentacaoId = movimentacaoId;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produtoId) {
        this.produto = produtoId;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedorId) {
        this.fornecedor = fornecedorId;
    }

    public Acesso getIdAcesso() {
        return idAcesso;
    }

    public void setIdAcesso(Acesso idAcesso) {
        this.idAcesso = idAcesso;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public MovimentacaoProdutoResponse toDtoMovimentacao() {
        if (!fornecedor.getNomeFantasia().isEmpty())
            return new MovimentacaoProdutoResponse(movimentacaoId, produto.getDescricao(), fornecedor.getNomeFantasia(), idAcesso.getLogin(), data, tipoMovimentacao.getNome());
        else {
            return new MovimentacaoProdutoResponse(movimentacaoId, produto.getDescricao(), null, idAcesso.getLogin(), data, tipoMovimentacao.getNome());
        }
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
