package br.com.gerenciamento.estoque.domain.dto;

import br.com.gerenciamento.estoque.entity.Estado;

import java.io.Serializable;

public class EstadoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public EstadoDto() {
    }

    public EstadoDto(Estado obj) {
        id = obj.getId();
        nome = obj.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
