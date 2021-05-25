package br.com.gerenciamento.estoque.domain.enums;

public enum Status {
    ATIVO(1, "ATIVO"),
    DESATIVO(2, "DESATIVO");

    private Integer id;
    private String nome;

    Status(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCode(TipoMovimentacao tipo) {
        return tipo.getId();
    }
}
