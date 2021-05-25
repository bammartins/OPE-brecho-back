package br.com.gerenciamento.estoque.domain.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;

public class MovimentacaoProdutoResponse {

    private Long movimentacaoId;

    private String produto;

    private String fornecedor;

    private String acesso;

    private LocalDateTime data;

    private String tipoMovimentacao;

    public MovimentacaoProdutoResponse(Long movimentacaoId, String produtoId, String fornecedorId, String idAcesso, LocalDateTime data, String tipoMovimentacao) {
        this.movimentacaoId = movimentacaoId;
        this.produto = produtoId;
        this.fornecedor = fornecedorId;
        this.acesso = idAcesso;
        this.data = data;
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public Long getMovimentacaoId() {
        return movimentacaoId;
    }

    public void setMovimentacaoId(Long movimentacaoId) {
        this.movimentacaoId = movimentacaoId;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getAcesso() {
        return acesso;
    }

    public void setAcesso(String acesso) {
        this.acesso = acesso;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(String tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
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
