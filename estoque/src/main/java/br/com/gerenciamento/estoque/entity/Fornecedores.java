package br.com.gerenciamento.estoque.entity;

import br.com.gerenciamento.estoque.domain.response.FornecedoresResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Fornecedores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeFantasia;

    private String razaoSocial;

    private Long cnpj;

    private String responsavel;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cep;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @OneToMany(mappedBy = "fornecedores", cascade = CascadeType.ALL)
    private List<Contato> contatos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<MovimentacaoProduto> movimentacaoProdutos = new ArrayList<>();

    private String status;

    public Fornecedores() {
    }

    public Fornecedores(Long id, String nomeFantasia, String razaoSocial, Long cnpj, String responsavel, String logradouro, String numero, String complemento, String bairro, String cep, Cidade cidade, List<Contato> contatos, List<MovimentacaoProduto> movimentacaoProdutos, String status) {
        this.id = id;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.responsavel = responsavel;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.contatos = contatos;
        this.movimentacaoProdutos = movimentacaoProdutos;
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nome) {
        this.nomeFantasia = nome;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<MovimentacaoProduto> getMovimentacaoProdutos() {
        return movimentacaoProdutos;
    }

    public void setMovimentacaoProdutos(List<MovimentacaoProduto> movimentacaoProdutos) {
        this.movimentacaoProdutos = movimentacaoProdutos;
    }

    public FornecedoresResponse toDtoFornecedores() {
        return new FornecedoresResponse(id, nomeFantasia, razaoSocial, cnpj, responsavel, logradouro, numero,
                complemento, bairro, cep, cidade.getNome(), cidade.getEstado().getNome(), contatos.size(), movimentacaoProdutos.size());
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
