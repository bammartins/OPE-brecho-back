package br.com.gerenciamento.estoque.domain.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ContatoResponse {

    private Long id;

    private Integer telefone;

    private String email;

    private String fornecedores;

    private String status;


    public ContatoResponse() {
    }

    public ContatoResponse(Long id, Integer telefone, String email, String fornecedores, String status) {
        this.id = id;
        this.telefone = telefone;
        this.email = email;
        this.fornecedores = fornecedores;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(String fornecedores) {
        this.fornecedores = fornecedores;
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
