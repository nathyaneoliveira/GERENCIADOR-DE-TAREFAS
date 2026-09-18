package br.com.atividade.gerenciadortarefas.repository;

import br.com.atividade.gerenciadortarefas.domain.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {
}
