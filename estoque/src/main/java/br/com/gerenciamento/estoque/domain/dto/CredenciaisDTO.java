package br.com.gerenciamento.estoque.domain.dto;

import java.io.Serializable;

public class CredenciaisDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

}
