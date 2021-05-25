package br.com.gerenciamento.estoque.domain.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

public class AcessoResponse {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private String login;

    @NotEmpty
    private String senha;

    private Set<Integer> perfis = new HashSet<>();

    public AcessoResponse() {
    }

    public AcessoResponse(String login, String senha, Set<Integer> perfis) {
        this.login = login;
        this.senha = senha;
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

    public Set<Integer> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Integer> perfis) {
        this.perfis = perfis;
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
