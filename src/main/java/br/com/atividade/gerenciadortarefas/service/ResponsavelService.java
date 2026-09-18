package br.com.atividade.gerenciadortarefas.service;

import br.com.atividade.gerenciadortarefas.domain.Responsavel;
import br.com.atividade.gerenciadortarefas.repository.ResponsavelRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ResponsavelService {

    private final ResponsavelRepository responsavelRepository;

    public ResponsavelService(ResponsavelRepository responsavelRepository) {
        this.responsavelRepository = responsavelRepository;
    }

    public Responsavel criar(Responsavel responsavel) {
        responsavel.setId(null);
        return responsavelRepository.save(responsavel);
    }

    public List<Responsavel> listar() {
        return responsavelRepository.findAll();
    }
}
