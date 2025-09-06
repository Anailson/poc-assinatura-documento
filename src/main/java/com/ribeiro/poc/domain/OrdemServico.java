package com.ribeiro.poc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TB_ORDEM_SERVICO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdemServico {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID_ORDEM_SERVICO")
    private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="ID_ACAO_CONTROLE")
    private AcaoControle acaoControle;

    @Column(name="NOME_TEMPLATE", nullable=false, length=120)
    private String nomeTemplate; // ex.: "os/documento"

    @Column(name="DESCRICAO")
    private String descricao;

    @Column(name="RESPONSAVEL", length=120)
    private String responsavel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AcaoControle getAcaoControle() {
        return acaoControle;
    }

    public void setAcaoControle(AcaoControle acaoControle) {
        this.acaoControle = acaoControle;
    }

    public String getNomeTemplate() {
        return nomeTemplate;
    }

    public void setNomeTemplate(String nomeTemplate) {
        this.nomeTemplate = nomeTemplate;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}
