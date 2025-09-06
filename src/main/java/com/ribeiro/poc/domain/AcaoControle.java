package com.ribeiro.poc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TB_ACAO_CONTROLE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcaoControle {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID_ACAO_CONTROLE")
    private Long id;

    @Column(name="TITULO", nullable=false, length=200)
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
