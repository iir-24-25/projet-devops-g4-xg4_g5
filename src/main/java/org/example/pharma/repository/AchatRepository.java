package org.example.pharma.repository;

import org.example.pharma.model.Achat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface AchatRepository extends JpaRepository<Achat, Long> {
    List<Achat> findByFournisseurId(Long fournisseurId);
    List<Achat> findByStatut(Achat.StatutAchat statut);
}
