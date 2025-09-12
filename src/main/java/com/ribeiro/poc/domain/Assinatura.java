package com.ribeiro.poc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "TB_ASSINATURA",
        uniqueConstraints = @UniqueConstraint(name="UQ_ASSINATURA_OS_UNICA", columnNames = "ID_ORDEM_SERVICO"),
        indexes = @Index(name="IX_ASSINATURA_OS", columnList = "ID_ORDEM_SERVICO"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assinatura {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ASSINATURA")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ORDEM_SERVICO", nullable = false)
    private OrdemServico ordemServico;

    @Column(name = "NOME_ARQUIVO", nullable = false, length = 255)
    private String nomeArquivo;

    @Column(name = "HASH_SHA256", nullable = false, length = 64)
    private String hashSha256;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "CONTEUDO", nullable = false)
    private byte[] conteudo;

    @Column(name = "GERADO_EM", nullable = false)
    private LocalDateTime geradoEm = LocalDateTime.now();

    @Column(name = "ASSINADO_EM")
    private LocalDateTime assinadoEm;

    @Column(name = "ASSINADO_POR", length = 150)
    private String assinadoPor;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getHashSha256() {
        return hashSha256;
    }

    public void setHashSha256(String hashSha256) {
        this.hashSha256 = hashSha256;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getGeradoEm() {
        return geradoEm;
    }

    public void setGeradoEm(LocalDateTime geradoEm) {
        this.geradoEm = geradoEm;
    }

    public LocalDateTime getAssinadoEm() {
        return assinadoEm;
    }

    public void setAssinadoEm(LocalDateTime assinadoEm) {
        this.assinadoEm = assinadoEm;
    }

    public String getAssinadoPor() {
        return assinadoPor;
    }

    public void setAssinadoPor(String assinadoPor) {
        this.assinadoPor = assinadoPor;
    }
}
