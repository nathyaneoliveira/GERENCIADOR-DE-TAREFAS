package br.com.atividade.gerenciadortarefas.service;

import br.com.atividade.gerenciadortarefas.domain.Projeto;
import br.com.atividade.gerenciadortarefas.exception.RecursoNaoEncontradoException;
import br.com.atividade.gerenciadortarefas.repository.ProjetoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public Projeto criar(Projeto projeto) {
        projeto.setId(null);
        return projetoRepository.save(projeto);
    }

    public List<Projeto> listar() {
        return projetoRepository.findAll();
    }

    public Projeto buscarPorId(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto nao encontrado: " + id));
    }
}
