package com.ribeiro.poc.service;

import com.ribeiro.poc.domain.Assinatura;
import com.ribeiro.poc.domain.OrdemServico;
import com.ribeiro.poc.repository.AssinaturaRepository;
import com.ribeiro.poc.util.HashUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final OrdemServicoService ordemServicoService;
    private final DocumentoVisualizacaoService visualizacaoService;

    public AssinaturaService(AssinaturaRepository assinaturaRepository, OrdemServicoService ordemServicoService, DocumentoVisualizacaoService visualizacaoService) {
        this.assinaturaRepository = assinaturaRepository;
        this.ordemServicoService = ordemServicoService;
        this.visualizacaoService = visualizacaoService;
    }

    /** Gera PDF do template da OS, calcula SHA-256 e salva na TB_ASSINATURA. */
    @Transactional
    public Long gerarESalvarPorOrdemServico(Long idOrdemServico) {
        // 1) Carrega OS
        OrdemServico os = ordemServicoService.buscar(idOrdemServico);

        // 2) Gera HTML com os mesmos dados da visualização
        var modelo = Map.<String, Object>of("os", os, "acao", os.getAcaoControle());
        String html = visualizacaoService.gerarHtml(os.getNomeTemplate(), modelo);

        // 3) Converte HTML -> PDF
        byte[] pdf = visualizacaoService.htmlParaPdf(html);

        // 4) Hash
        String hash = HashUtils.sha256Hex(pdf);

        // 5) Nome do arquivo
        String nomeArquivo = "ordem-servico-" + os.getId() + ".pdf";

        // Política: 1 assinatura corrente por OS (substitui)
        assinaturaRepository.findByOrdemServico_Id(idOrdemServico)
                .ifPresent(assinaturaRepository::delete);

        Assinatura entity = new Assinatura();
        entity.setOrdemServico(os);
        entity.setNomeArquivo(nomeArquivo);
        entity.setHashSha256(hash);
        entity.setConteudo(pdf);

        return assinaturaRepository.save(entity).getId();
    }

    @Transactional(readOnly = true)
    public Assinatura obterPorOrdemServico(Long idOrdemServico) {
        return assinaturaRepository.findByOrdemServico_Id(idOrdemServico)
                .orElseThrow(() -> new IllegalArgumentException("Documento não encontrado para OS " + idOrdemServico));
    }

    public MediaType contentType() {
        return MediaType.APPLICATION_PDF;
    }
}
