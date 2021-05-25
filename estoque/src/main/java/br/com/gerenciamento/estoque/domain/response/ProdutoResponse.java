package br.com.gerenciamento.estoque.domain.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

public final class ProdutoResponse {

    private Long produtoId;
    private String nome;
    private BigDecimal preco;
    private Integer quantidade;
    private String tamanho;
    private String cor;
    private String modelo;
    private String categoria;
    private String status;

    public ProdutoResponse() {
    }

    public ProdutoResponse(Long produtoId, String nome, BigDecimal preco, Integer quantidade,
                           String tamanho, String cor, String modelo, String status, String categoria) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.tamanho = tamanho;
        this.cor = cor;
        this.modelo = modelo;
        this.status = status;
        this.categoria = categoria;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
