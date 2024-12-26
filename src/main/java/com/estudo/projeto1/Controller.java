package com.estudo.projeto1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entidades")
public class Controller {

    private final EntidadeService entidadeService;

    @Autowired
    public Controller(EntidadeService entidadeService) {
        this.entidadeService = entidadeService;
    }

    @PostMapping
    public Modelo salvar(@RequestBody Modelo entidade) {
        return entidadeService.salvar(entidade);
    }

    @GetMapping
    public List<Modelo> buscarTodas() {
        return entidadeService.buscarTodas();
    }

    @GetMapping("/{id}")
    public Optional<Modelo> buscarPorId(@PathVariable Long id) {
        return entidadeService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        entidadeService.excluir(id);
    }

    @GetMapping("/buscar")
    public List<Modelo> buscarPorNome(@RequestParam String nome) {
        return entidadeService.buscarPorNome(nome);
    }
}
