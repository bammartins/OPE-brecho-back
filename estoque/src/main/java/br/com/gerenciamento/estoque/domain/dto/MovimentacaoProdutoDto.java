package br.com.gerenciamento.estoque.domain.dto;

import java.time.LocalDate;

public class MovimentacaoProdutoDto {

    private Long movimentacaoId;
    private Long produtoId;
    private Long fornecedorId;
    private Long idAcesso;
    private LocalDate data;

    public Long getMovimentacaoId() {
        return movimentacaoId;
    }

    public void setMovimentacaoId(Long movimentacaoId) {
        this.movimentacaoId = movimentacaoId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public Long getIdAcesso() {
        return idAcesso;
    }

    public void setIdAcesso(Long idAcesso) {
        this.idAcesso = idAcesso;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
