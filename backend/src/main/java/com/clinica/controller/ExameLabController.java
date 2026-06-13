package com.clinica.controller;

import com.clinica.model.ExameLab;
import com.clinica.repository.ExameLabRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exames")
@CrossOrigin(origins = "*")
public class ExameLabController {

    private final ExameLabRepository repository;

    public ExameLabController(ExameLabRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<ExameLab> criar(@Valid @RequestBody ExameLab exame) {
        ExameLab salvo = repository.save(exame);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<ExameLab>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<List<ExameLab>> consultarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(repository.findByAtendimento_ProfissionalDeSaude_NomeContainingIgnoreCase(nome));
    }

    @GetMapping("/buscar/categoria/{categoria}")
    public ResponseEntity<List<ExameLab>> consultarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(repository.findByAtendimento_ProfissionalDeSaude_CategoriaContainingIgnoreCase(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody ExameLab dados) {
        return repository.findById(id)
                .map(exame -> {
                    exame.setDescricao(dados.getDescricao());
                    exame.setAtendimento(dados.getAtendimento());
                    return ResponseEntity.ok(repository.save(exame));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return repository.findById(id)
                .map(exame -> {
                    repository.delete(exame);
                    return ResponseEntity.ok(Map.of("mensagem", "Exame removido"));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
