package org.example.pharma.service;

import org.example.pharma.model.Vente;
import org.example.pharma.model.StatutVente;
import org.example.pharma.model.ModePaiement;
import org.example.pharma.repository.VenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class VenteServiceImpl implements VenteService {

    private final VenteRepository venteRepository;

    @Autowired
    public VenteServiceImpl(VenteRepository venteRepository) {
        this.venteRepository = venteRepository;
    }

    @Override
    public List<Vente> getAllVentes() {
        return venteRepository.findAll();
    }

    @Override
    public Vente getVenteById(Long id) {
        return venteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vente non trouvée avec l'ID: " + id));
    }

    @Override
    public Vente createVente(Vente vente) {
        vente.setCreatedAt(new Date());
        return venteRepository.save(vente);
    }

    @Override
    public Vente updateVente(Long id, Vente vente) {
        Vente existingVente = getVenteById(id);
        vente.setId(id);
        vente.setCreatedAt(existingVente.getCreatedAt());
        vente.setUpdatedAt(new Date());
        return venteRepository.save(vente);
    }

    @Override
    public void deleteVente(Long id) {
        Vente vente = getVenteById(id);
        venteRepository.delete(vente);
    }

    @Override
    public List<Vente> getVentesByClient(Long clientId) {
        return venteRepository.findByClientId(clientId);
    }

    @Override
    public List<Vente> getVentesByStatut(StatutVente statut) {
        return venteRepository.findByStatut(statut);
    }

    @Override
    public List<Vente> getVentesByModePaiement(ModePaiement modePaiement) {
        return venteRepository.findByModePaiement(modePaiement);
    }
}