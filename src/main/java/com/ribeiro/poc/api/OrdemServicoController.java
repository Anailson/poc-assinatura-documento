package com.ribeiro.poc.api;

import com.ribeiro.poc.service.DocumentoVisualizacaoService;
import com.ribeiro.poc.service.OrdemServicoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/os-acao-controle")
public class OrdemServicoController {

    private final OrdemServicoService osService;
    private final DocumentoVisualizacaoService docService;

    public OrdemServicoController(OrdemServicoService osService, DocumentoVisualizacaoService docService) {
        this.osService = osService;
        this.docService = docService;
    }

    @PostMapping("/salvar-os/{idAcaoControle}")
    public ResponseEntity<Map<String,Object>> salvarOS(@PathVariable Long idAcaoControle,
                                                       @RequestParam(defaultValue = "os/documento") String nomeTemplate,
                                                       @RequestParam(required = false) String descricao,
                                                       @RequestParam(required = false) String responsavel){
        var os = osService.criar(idAcaoControle, nomeTemplate, descricao, responsavel);
        return ResponseEntity.ok(Map.of("idOrdemServico", os.getId(), "template", os.getNomeTemplate()));
    }
    @GetMapping(value="/visualizar/{idOrdemServico}", produces= MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> visualizarPdf(@PathVariable Long idOrdemServico){
        var os = osService.buscar(idOrdemServico);
        Map<String,Object> modelo = new HashMap<>();
        modelo.put("os", os);
        modelo.put("acao", os.getAcaoControle());
        String html = docService.gerarHtml(os.getNomeTemplate(), modelo);
        byte[] pdf = docService.htmlParaPdf(html);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"ordem-servico-" + os.getId() + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    // útil para debugar o HTML
    @GetMapping(value="/visualizar-html/{idOrdemServico}", produces=MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> visualizarHtml(@PathVariable Long idOrdemServico){
        var os = osService.buscar(idOrdemServico);
        var html = docService.gerarHtml(os.getNomeTemplate(), Map.of("os", os, "acao", os.getAcaoControle()));
        return ResponseEntity.ok(html);
    }
}
