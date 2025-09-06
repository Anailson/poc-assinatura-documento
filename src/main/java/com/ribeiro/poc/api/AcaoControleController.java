package com.ribeiro.poc.api;

import com.ribeiro.poc.service.AcaoControleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/acao-controle")
public class AcaoControleController {

    private final AcaoControleService service;
    public AcaoControleController(AcaoControleService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Map<String,Object>> criar(@RequestParam String titulo){
        var ac = service.criar(titulo);
        return ResponseEntity.ok(Map.of("idAcaoControle", ac.getId()));
    }

}
