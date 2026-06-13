package com.clinica.controller;

import com.clinica.model.Atendimento;
import com.clinica.repository.AtendimentoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atendimentos")
@CrossOrigin(origins = "*")
public class AtendimentoController {

    private final AtendimentoRepository repository;

    public AtendimentoController(AtendimentoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Atendimento> criar(@Valid @RequestBody Atendimento atendimento) {
        Atendimento salvo = repository.save(atendimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Atendimento>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<List<Atendimento>> consultarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(repository.findByProfissionalDeSaude_NomeContainingIgnoreCase(nome));
    }

    @GetMapping("/buscar/categoria/{categoria}")
    public ResponseEntity<List<Atendimento>> consultarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(repository.findByProfissionalDeSaude_CategoriaContainingIgnoreCase(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Atendimento dados) {
        return repository.findById(id)
                .map(atendimento -> {
                    atendimento.setData(dados.getData());
                    atendimento.setHorario(dados.getHorario());
                    atendimento.setProblemaTexto(dados.getProblemaTexto());
                    atendimento.setReceitaSaude(dados.getReceitaSaude());
                    atendimento.setProfissionalDeSaude(dados.getProfissionalDeSaude());
                    return ResponseEntity.ok(repository.save(atendimento));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return repository.findById(id)
                .map(atendimento -> {
                    repository.delete(atendimento);
                    return ResponseEntity.ok(Map.of("mensagem", "Atendimento removido"));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
