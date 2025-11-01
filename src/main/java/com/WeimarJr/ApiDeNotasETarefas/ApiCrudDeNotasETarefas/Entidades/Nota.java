package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Lista_De_Notas")

@NoArgsConstructor
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter  private Long id;

    @Column(nullable = false, name = "titulo_da_nota")
    @Getter @Setter private String tituloNota;
    @Column(nullable = false, name = "texto_da_nota")
    @Getter @Setter private String nota;
    @Column(nullable = false, name = "tag_da_nota")
    @Getter @Setter private String tag;


    @OneToMany(mappedBy = "nota", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<Tarefa> tarefasRelacionadas = new ArrayList<>();

    public void setTarefa(Tarefa tarefa)
    {
        this.tarefasRelacionadas.add(tarefa);
    }

    public void deletarTarefa(Tarefa tarefa)
    {
        this.tarefasRelacionadas.remove(tarefa);
    }
}
