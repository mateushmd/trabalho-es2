package com.clinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "atendimentos")
@Data
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    private LocalTime horario;

    @Column(columnDefinition = "TEXT")
    private String problemaTexto;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "atendimento_receitas", joinColumns = @JoinColumn(name = "atendimento_id"))
    @Column(name = "receita")
    private List<String> receitaSaude;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalDeSaude profissionalDeSaude;
}
