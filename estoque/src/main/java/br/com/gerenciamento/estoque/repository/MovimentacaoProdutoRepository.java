package br.com.gerenciamento.estoque.repository;

import br.com.gerenciamento.estoque.entity.MovimentacaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MovimentacaoProdutoRepository extends JpaRepository<MovimentacaoProduto, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT p from MovimentacaoProduto p where p.produto.produtoId = :idProduto")
    List<MovimentacaoProduto> findbyProdutoProdutoId(@Param("idProduto") Long idProduto);

    @Transactional(readOnly = true)
    @Query("SELECT p from MovimentacaoProduto p where p.fornecedor.id = :idFornecedor")
    MovimentacaoProduto findbyFornecedorId(@Param("idFornecedor") Long idFornecedor);
}
