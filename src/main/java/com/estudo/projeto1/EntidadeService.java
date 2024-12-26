package com.estudo.projeto1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntidadeService {

    private final EntidadeRepository entidadeRepository;

    @Autowired
    public EntidadeService(EntidadeRepository entidadeRepository) {
        this.entidadeRepository = entidadeRepository;
    }

    public Modelo salvar(Modelo entidade) {
        return entidadeRepository.save(entidade);
    }

    public List<Modelo> buscarTodas() {
        return entidadeRepository.findAll();
    }

    public Optional<Modelo> buscarPorId(Long id) {
        return entidadeRepository.findById(id);
    }

    public void excluir(Long id) {
        entidadeRepository.deleteById(id);
    }

    public List<Modelo> buscarPorNome(String nome) {
        return entidadeRepository.findByNomeContaining(nome);
    }
}
