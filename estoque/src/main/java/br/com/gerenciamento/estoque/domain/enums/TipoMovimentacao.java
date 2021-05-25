package br.com.gerenciamento.estoque.domain.enums;

public enum TipoMovimentacao {
    AUMENTO(1, "Compra"),
    RETIRADA(2, "Venda"),
    ALTERACAO(3, "Alterado"),
    DELETADO(4, "Deletado");

    private Integer id;
    private String nome;

    TipoMovimentacao(Integer id, String nome) {
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
