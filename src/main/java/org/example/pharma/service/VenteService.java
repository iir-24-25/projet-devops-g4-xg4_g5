package org.example.pharma.service;

import org.example.pharma.model.Vente;
import org.example.pharma.model.StatutVente;
import org.example.pharma.model.ModePaiement;
import java.util.List;

public interface VenteService {
    List<Vente> getAllVentes();
    Vente getVenteById(Long id);
    Vente createVente(Vente vente);
    Vente updateVente(Long id, Vente vente);
    void deleteVente(Long id);
    List<Vente> getVentesByClient(Long clientId);
    List<Vente> getVentesByStatut(StatutVente statut);
    List<Vente> getVentesByModePaiement(ModePaiement modePaiement);
}