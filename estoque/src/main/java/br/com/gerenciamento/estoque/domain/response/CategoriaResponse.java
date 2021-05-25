package br.com.gerenciamento.estoque.domain.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public final class CategoriaResponse {

    private final Long id;
    private final String nome;
    private final List<ProdutoResponse> produtos;

    public CategoriaResponse(Long id, String nome, List<ProdutoResponse> produtos) {
        this.id = id;
        this.nome = nome;
        this.produtos = produtos;
    }


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<ProdutoResponse> getProdutos() {
        return produtos;
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
