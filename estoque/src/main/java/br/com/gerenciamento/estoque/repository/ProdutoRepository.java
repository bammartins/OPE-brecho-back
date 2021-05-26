package br.com.gerenciamento.estoque.repository;

import br.com.gerenciamento.estoque.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Produto findByFornecedorIdAndDescricao (Long fornecedorId, String descricao);

}