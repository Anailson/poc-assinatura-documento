package com.ribeiro.poc.api;

import com.ribeiro.poc.domain.Assinatura;
import com.ribeiro.poc.service.AssinaturaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/assinaturas")
public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    public AssinaturaController(AssinaturaService assinaturaService) {
        this.assinaturaService = assinaturaService;
    }

    /** Gera o PDF da OS, calcula hash e persiste. */
    @PostMapping("/ordem-servico/{idOrdemServico}")
    public ResponseEntity<Map<String,Object>> gerarESalvar(@PathVariable Long idOrdemServico) {
        Long idAssinatura = assinaturaService.gerarESalvarPorOrdemServico(idOrdemServico);
        return ResponseEntity.ok(Map.of("idAssinatura", idAssinatura));
    }

    /** Download do PDF persistido. */
    @GetMapping(value="/ordem-servico/{idOrdemServico}/download", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> download(@PathVariable Long idOrdemServico) {
        Assinatura entity = assinaturaService.obterPorOrdemServico(idOrdemServico);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + entity.getNomeArquivo() + "\"")
                // cabeçalhos úteis:
                .header("X-Document-Hash-SHA256", entity.getHashSha256())
                .eTag("\"" + entity.getHashSha256() + "\"") // ETag precisa de aspas
                .contentType(assinaturaService.contentType())
                .body(entity.getConteudo());
    }

    /** Consulta somente o hash salvo. */
    @GetMapping("/ordem-servico/{idOrdemServico}/hash")
    public ResponseEntity<Map<String,String>> obterHash(@PathVariable Long idOrdemServico) {
        var entity = assinaturaService.obterPorOrdemServico(idOrdemServico);
        return ResponseEntity.ok(Map.of(
                "idOrdemServico", String.valueOf(idOrdemServico),
                "hashSha256", entity.getHashSha256()
        ));
    }

    //Adicione um endpoint para retornar hash + infos, e coloque o link dele no PDF:
    @GetMapping("/ordem-servico/{id}/metadata")
    public ResponseEntity<Map<String,Object>> metadata(@PathVariable Long id) {
        var e = assinaturaService.obterPorOrdemServico(id);
        return ResponseEntity.ok(Map.of(
                "idOrdemServico", id,
                "nomeArquivo", e.getNomeArquivo(),
                "tamanhoBytes", e.getConteudo().length,
                "hashSha256", e.getHashSha256(),
                "geradoEm", e.getGeradoEm()
        ));
    }

}
