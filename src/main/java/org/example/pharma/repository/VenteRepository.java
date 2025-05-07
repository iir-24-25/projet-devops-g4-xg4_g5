package org.example.pharma.repository;

import org.example.pharma.model.ModePaiement;
import org.example.pharma.model.Vente;
import org.example.pharma.model.StatutVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VenteRepository extends JpaRepository<Vente, Long> {

    @Query("SELECT v FROM Vente v WHERE v.client.id = :clientId")
    List<Vente> findByClientId(@Param("clientId") Long clientId);

    @Query("SELECT v FROM Vente v WHERE v.statut = :statut")
    List<Vente> findByStatut(@Param("statut") StatutVente statut);

    List<Vente> findByModePaiement(ModePaiement modePaiement);
}