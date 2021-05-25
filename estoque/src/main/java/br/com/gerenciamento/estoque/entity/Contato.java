package br.com.gerenciamento.estoque.entity;

import br.com.gerenciamento.estoque.domain.response.ContatoResponse;
import br.com.gerenciamento.estoque.domain.response.FornecedoresResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Contato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer telefone;

    private String email;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedores fornecedores;

    private String status;

    public Contato() {
    }

    public Contato(Long id, Integer telefone, String email, Fornecedores fornecedores, String status) {
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

    public Fornecedores getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(Fornecedores fornecedores) {
        this.fornecedores = fornecedores;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ContatoResponse toDtoContato() {
        return new ContatoResponse(id, telefone, email, fornecedores.getNomeFantasia(), status);
    }

    public static ContatoResponse toDtoContatoEntity(Contato contato) {
        return new ContatoResponse(contato.id, contato.telefone, contato.email,
                contato.fornecedores.getNomeFantasia(), contato.status);
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
