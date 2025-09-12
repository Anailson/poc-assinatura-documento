package com.ribeiro.poc.repository;

import com.ribeiro.poc.domain.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    Optional<Assinatura> findByOrdemServico_Id(Long idOrdemServico);
    boolean existsByOrdemServico_Id(Long idOrdemServico);
}
