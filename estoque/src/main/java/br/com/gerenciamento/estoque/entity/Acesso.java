package br.com.gerenciamento.estoque.entity;

import br.com.gerenciamento.estoque.domain.enums.Perfil;
import br.com.gerenciamento.estoque.domain.response.AcessoResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Acesso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    @JsonIgnore
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    @OneToMany(mappedBy = "idAcesso")
    private List<MovimentacaoProduto> movimentacaoProduto;

    private String status;

    public Acesso() {
        addPerfil(Perfil.FUNCIO);
    }

    public Acesso(Long id, String login, String senha, String status) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.status = status;
        addPerfil(Perfil.FUNCIO);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<MovimentacaoProduto> getMovimentacaoProduto() {
        return movimentacaoProduto;
    }

    public void setMovimentacaoProduto(List<MovimentacaoProduto> movimentacaoProduto) {
        this.movimentacaoProduto = movimentacaoProduto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCod());
    }

    public AcessoResponse toDtoAcesso(){
        return new AcessoResponse(login, senha, perfis);
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
