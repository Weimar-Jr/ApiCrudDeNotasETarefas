package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lista_de_tarefas")
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(nullable = false, name = "nome_da_tarefa")
    @Getter @Setter private String nomeTarefa;
    @Column(nullable = true, name = "descricao_da_tarefa")
    @Getter @Setter private String descricaoTarefa;
    @Column(nullable = false, name = "prioridade_da_tarefa")
    @Getter @Setter private int prioridade;
    @Column(nullable = false, name = "se_a_tarefa_foi_concluida")
    @Getter @Setter private  Boolean concluida = false;

    @ManyToOne
    @JoinColumn(name = "nota_id")
    @Getter
    @Setter
    private Nota nota;


}
