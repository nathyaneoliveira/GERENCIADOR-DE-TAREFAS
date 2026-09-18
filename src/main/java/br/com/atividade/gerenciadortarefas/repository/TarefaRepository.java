package br.com.atividade.gerenciadortarefas.repository;

import br.com.atividade.gerenciadortarefas.domain.Status;
import br.com.atividade.gerenciadortarefas.domain.Tarefa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByStatus(Status status);

    List<Tarefa> findByProjeto_Id(Long projetoId);

    List<Tarefa> findByStatusAndProjeto_Id(Status status, Long projetoId);
}
