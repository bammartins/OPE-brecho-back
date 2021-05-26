package br.com.gerenciamento.estoque.domain.request;

import javax.validation.constraints.NotEmpty;

public class AcessoRequest {

    @NotEmpty
    private String login;

    @NotEmpty
    private String senha;

    private Integer perfis;

    public Integer getPerfis() {
        return perfis;
    }

    public void setPerfis(Integer perfis) {
        this.perfis = perfis;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
