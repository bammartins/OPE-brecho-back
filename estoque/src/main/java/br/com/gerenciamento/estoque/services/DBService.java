package br.com.gerenciamento.estoque.services;

import br.com.gerenciamento.estoque.domain.enums.Perfil;
import br.com.gerenciamento.estoque.domain.enums.Status;
import br.com.gerenciamento.estoque.domain.enums.TipoMovimentacao;
import br.com.gerenciamento.estoque.entity.Acesso;
import br.com.gerenciamento.estoque.entity.Categoria;
import br.com.gerenciamento.estoque.entity.Cidade;
import br.com.gerenciamento.estoque.entity.Estado;
import br.com.gerenciamento.estoque.entity.Fornecedores;
import br.com.gerenciamento.estoque.entity.MovimentacaoProduto;
import br.com.gerenciamento.estoque.entity.Produto;
import br.com.gerenciamento.estoque.repository.AcessoRepository;
import br.com.gerenciamento.estoque.repository.CategoriaRepository;
import br.com.gerenciamento.estoque.repository.CidadeRepository;
import br.com.gerenciamento.estoque.repository.ContatoRepository;
import br.com.gerenciamento.estoque.repository.EstadoRepository;
import br.com.gerenciamento.estoque.repository.FornecedoresRepository;
import br.com.gerenciamento.estoque.repository.MovimentacaoProdutoRepository;
import br.com.gerenciamento.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private FornecedoresRepository fornecedoresRepository;

    @Autowired
    private MovimentacaoProdutoRepository movimentacaoProdutoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;


    public void instantiateTestDatabase() throws ParseException {

    }
}
