package com.ribeiro.poc.service;

import com.ribeiro.poc.domain.AcaoControle;
import com.ribeiro.poc.domain.OrdemServico;
import com.ribeiro.poc.repository.OrdemServicoRepository;
import org.springframework.stereotype.Service;

@Service
public class OrdemServicoService {

    private final OrdemServicoRepository repo;
    private final AcaoControleService acService;

    public OrdemServicoService(OrdemServicoRepository repo, AcaoControleService acService) {
        this.repo = repo;
        this.acService = acService;
    }

    public OrdemServico criar(Long idAcao, String nomeTemplate, String descricao, String responsavel){
        AcaoControle ac = acService.buscar(idAcao);
        var os = new OrdemServico();
        os.setAcaoControle(ac);
        os.setNomeTemplate(nomeTemplate);
        os.setDescricao(descricao);
        os.setResponsavel(responsavel);
        return repo.save(os);
    }

    public OrdemServico buscar(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("OS não encontrada"));
    }

}
