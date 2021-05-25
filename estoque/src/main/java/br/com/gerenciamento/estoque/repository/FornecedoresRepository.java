package br.com.gerenciamento.estoque.repository;

import br.com.gerenciamento.estoque.entity.Fornecedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedoresRepository extends JpaRepository<Fornecedores, Long> {
}
