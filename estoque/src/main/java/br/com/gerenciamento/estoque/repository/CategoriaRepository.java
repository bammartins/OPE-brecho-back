package br.com.gerenciamento.estoque.repository;

import br.com.gerenciamento.estoque.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
