package br.com.atividade.gerenciadortarefas.service;

import br.com.atividade.gerenciadortarefas.domain.Projeto;
import br.com.atividade.gerenciadortarefas.domain.Prioridade;
import br.com.atividade.gerenciadortarefas.domain.Responsavel;
import br.com.atividade.gerenciadortarefas.domain.Status;
import br.com.atividade.gerenciadortarefas.domain.Tarefa;
import br.com.atividade.gerenciadortarefas.exception.RecursoNaoEncontradoException;
import br.com.atividade.gerenciadortarefas.repository.ProjetoRepository;
import br.com.atividade.gerenciadortarefas.repository.ResponsavelRepository;
import br.com.atividade.gerenciadortarefas.repository.TarefaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final ProjetoRepository projetoRepository;
    private final ResponsavelRepository responsavelRepository;

    public TarefaService(
            TarefaRepository tarefaRepository,
            ProjetoRepository projetoRepository,
            ResponsavelRepository responsavelRepository) {
        this.tarefaRepository = tarefaRepository;
        this.projetoRepository = projetoRepository;
        this.responsavelRepository = responsavelRepository;
    }

    @Transactional
    public Tarefa criar(Tarefa tarefa) {
        tarefa.setId(null);
        tarefa.setProjeto(buscarProjeto(tarefa.getProjeto()));
        tarefa.setResponsavel(buscarResponsavel(tarefa.getResponsavel()));
        if (tarefa.getStatus() == null) {
            tarefa.setStatus(Status.NOVA);
        }
        tarefa.setCriadaEm(LocalDateTime.now());
        tarefa.setConcluidaEm(null);
        if (tarefa.getPrioridade() == null) {
            tarefa.setPrioridade(Prioridade.MEDIA);
        }
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listar(Status status, Long projetoId) {
        if (status != null && projetoId != null) {
            return tarefaRepository.findByStatusAndProjeto_Id(status, projetoId);
        }
        if (status != null) {
            return tarefaRepository.findByStatus(status);
        }
        if (projetoId != null) {
            return tarefaRepository.findByProjeto_Id(projetoId);
        }
        return tarefaRepository.findAll();
    }

    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa nao encontrada: " + id));
    }

    @Transactional
    public Tarefa atualizar(Long id, Tarefa dados) {
        Tarefa tarefa = buscarPorId(id);
        tarefa.setTitulo(dados.getTitulo());
        tarefa.setDescricao(dados.getDescricao());
        tarefa.setPrioridade(dados.getPrioridade());
        tarefa.setPrazo(dados.getPrazo());
        tarefa.setProjeto(buscarProjeto(dados.getProjeto()));
        tarefa.setResponsavel(buscarResponsavel(dados.getResponsavel()));
        atualizarStatus(tarefa, dados.getStatus());
        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public void remover(Long id) {
        Tarefa tarefa = buscarPorId(id);
        tarefaRepository.delete(tarefa);
    }

    private Projeto buscarProjeto(Projeto projeto) {
        if (projeto == null || projeto.getId() == null) {
            throw new RecursoNaoEncontradoException("Projeto obrigatorio");
        }
        return projetoRepository.findById(projeto.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Projeto nao encontrado: " + projeto.getId()));
    }

    private Responsavel buscarResponsavel(Responsavel responsavel) {
        if (responsavel == null || responsavel.getId() == null) {
            return null;
        }
        return responsavelRepository.findById(responsavel.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Responsavel nao encontrado: " + responsavel.getId()));
    }

    private void atualizarStatus(Tarefa tarefa, Status novoStatus) {
        if (novoStatus == null) {
            throw new IllegalArgumentException("Status obrigatorio");
        }
        tarefa.setStatus(novoStatus);
        if (novoStatus == Status.CONCLUIDA) {
            if (tarefa.getConcluidaEm() == null) {
                tarefa.setConcluidaEm(LocalDateTime.now());
            }
        } else {
            tarefa.setConcluidaEm(null);
        }
    }
}
