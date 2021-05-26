package br.com.gerenciamento.estoque.entity;

import br.com.gerenciamento.estoque.domain.response.ProdutoResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produtoId;

    private String descricao;

    private BigDecimal preco;

    private Integer quantidade;

    private String tamanho;

    private String marca;

    private String cor;

    private String modelo;

    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;

    private String status;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    public Produto() {
    }

    public Produto(Long produtoId, String descricao, BigDecimal preco, Integer quantidade, String tamanho, String marca, String cor, String modelo, Categoria categoria, String status, Fornecedor fornecedor) {
        this.produtoId = produtoId;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.tamanho = tamanho;
        this.marca = marca;
        this.cor = cor;
        this.modelo = modelo;
        this.categoria = categoria;
        this.status = status;
        this.fornecedor = fornecedor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal valor) {
        this.preco = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public ProdutoResponse toDtoProdutos() {
        return new ProdutoResponse(produtoId, descricao, preco, quantidade, tamanho, cor, modelo, categoria.getNome(), status);
    }

    public ProdutoResponse toDto() {
        return new ProdutoResponse();
    }
}
