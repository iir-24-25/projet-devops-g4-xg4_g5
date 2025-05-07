package org.example.pharma.service;

import org.example.pharma.model.Fournisseur;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FournisseurService {
    Page<Fournisseur> getAllFournisseurs(int page, int size, String sortField, String sortDirection);
    Page<Fournisseur> searchFournisseurs(String searchTerm, int page, int size, String sortField, String sortDirection);
    Fournisseur getFournisseurById(Long id);
    Fournisseur saveOrUpdateFournisseur(Fournisseur fournisseur);
    void deleteFournisseur(Long id);
    List<Fournisseur> findAllFournisseurs();
    Fournisseur findFournisseurById(Long id);
}