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
@Getter
@Setter
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "nome_da_tarefa")
    private String nomeTarefa;
    @Column(nullable = true, name = "descricao_da_tarefa")
    private String descricaoTarefa;
    @Column(nullable = false, name = "prioriadade_da_tarefa")
    private String prioridadeTarefa;
    @Column(nullable = false, name = "se_a_tarefa_foi_concluida")
    private  Boolean concluida = false;

    @ManyToOne
    @JoinColumn(name = "nota_id")
    private Nota nota;

}
