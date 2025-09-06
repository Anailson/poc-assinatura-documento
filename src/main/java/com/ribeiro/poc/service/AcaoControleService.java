package com.ribeiro.poc.service;

import com.ribeiro.poc.domain.AcaoControle;
import com.ribeiro.poc.repository.AcaoControleRepository;
import org.springframework.stereotype.Service;

@Service
public class AcaoControleService {

    private final AcaoControleRepository repo;

    public AcaoControleService(AcaoControleRepository repo) {
        this.repo = repo;
    }

    public AcaoControle criar(String titulo){
        var ac = new AcaoControle();
        ac.setTitulo(titulo);
        return repo.save(ac);
    }

    public AcaoControle buscar(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ação de controle não encontrada"));
    }
}
