package com.clinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "profissionais_saude")
@Data
public class ProfissionalDeSaude {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 20)
    private String telefone;

    @Column(length = 200)
    private String endereco;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profissional_categorias", joinColumns = @JoinColumn(name = "profissional_id"))
    @Column(name = "categoria")
    private List<String> categoria;
}
