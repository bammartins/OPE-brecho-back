package br.com.gerenciamento.estoque.repository;

import br.com.gerenciamento.estoque.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

   // Fornecedor findFornecedorByProduto(Long id);
}
