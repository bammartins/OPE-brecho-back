package br.com.gerenciamento.estoque.repository;

import br.com.gerenciamento.estoque.entity.Acesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcessoRepository extends JpaRepository<Acesso, Long> {

    Acesso findByLogin(String login);
}
