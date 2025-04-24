package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Lista_De_Notas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "titulo_da_nota")
    private String tituloNota;
    @Column(nullable = false, name = "texto_da_nota")
    private String nota;

    @OneToMany(mappedBy = "nota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarefa> tarefasRelacionadas = new ArrayList<>();

}
