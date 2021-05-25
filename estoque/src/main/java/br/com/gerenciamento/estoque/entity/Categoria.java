package br.com.gerenciamento.estoque.entity;

import br.com.gerenciamento.estoque.domain.response.CategoriaResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "CATEGORIA")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORIA_UID")
    private Long id;

    @Column(name = "NOME_CATEGORIA")
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Produto> produtos = new ArrayList<>();

    private String status;

    public Categoria() {
    }

    public Categoria(Long id, String nome, List<Produto> produtos, String status) {
        this.id = id;
        this.nome = nome;
        this.produtos = produtos;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }



    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public CategoriaResponse toDtoCategoria() {
        return new CategoriaResponse(id, nome, produtos.stream().map(Produto::toDto).collect(Collectors.toList()));
    }
    public CategoriaResponse toDtoVazio(){
        return new CategoriaResponse(id, nome, List.of());
    }

}
