package br.com.gerenciamento.estoque.repository;

import br.com.gerenciamento.estoque.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
}
