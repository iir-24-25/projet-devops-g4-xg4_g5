package org.example.pharma.service;

import org.example.pharma.model.Achat;

import java.util.List;

public interface AchatService {
    List<Achat> getAllAchats();
    Achat getAchatById(Long id);
    Achat createAchat(Achat achat);
    Achat updateAchat(Long id, Achat achat);
    void deleteAchat(Long id);
    List<Achat> getAchatsByFournisseur(Long fournisseurId);
    List<Achat> getAchatsByStatut(String statut);
}