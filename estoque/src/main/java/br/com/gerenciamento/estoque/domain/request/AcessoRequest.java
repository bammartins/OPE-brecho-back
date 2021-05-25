package br.com.gerenciamento.estoque.domain.request;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

public class AcessoRequest {

    private String nomeDoLogin;

    private String login;

    @NotEmpty
    private String senha;

    private Set<Integer> perfis = new HashSet<>();

    public String getNomeDoLogin() {
        return nomeDoLogin;
    }

    public void setNomeDoLogin(String nomeDoLogin) {
        this.nomeDoLogin = nomeDoLogin;
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

    public Set<Integer> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Integer> perfis) {
        this.perfis = perfis;
    }
}
