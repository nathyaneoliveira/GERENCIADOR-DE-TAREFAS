package br.com.atividade.gerenciadortarefas.repository;

import br.com.atividade.gerenciadortarefas.domain.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
