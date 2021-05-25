package br.com.gerenciamento.estoque.domain.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ContatoRequest {

    private Integer telefone;

    private String email;

    private Long fornecedores;

    public ContatoRequest() {
    }

    public ContatoRequest(Integer telefone, String email, Long fornecedores) {
        this.telefone = telefone;
        this.email = email;
        this.fornecedores = fornecedores;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(Long fornecedores) {
        this.fornecedores = fornecedores;
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
