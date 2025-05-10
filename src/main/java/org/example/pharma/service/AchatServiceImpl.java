package org.example.pharma.service;

import org.example.pharma.model.Achat;
import org.example.pharma.repository.AchatRepository;
import org.example.pharma.service.AchatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchatServiceImpl implements AchatService {

    private final AchatRepository achatRepository;

    public AchatServiceImpl(AchatRepository achatRepository) {
        this.achatRepository = achatRepository;
    }

    @Override
    public List<Achat> getAllAchats() {
        return achatRepository.findAll();
    }

    @Override
    public Achat getAchatById(Long id) {
        return achatRepository.findById(id).orElse(null);
    }

    @Override
    public Achat createAchat(Achat achat) {
        return achatRepository.save(achat);
    }

    @Override
    public Achat updateAchat(Long id, Achat achat) {
        if (achatRepository.existsById(id)) {
            achat.setId(id);
            return achatRepository.save(achat);
        }
        return null;
    }

    @Override
    public void deleteAchat(Long id) {
        achatRepository.deleteById(id);
    }

    @Override
    public List<Achat> getAchatsByFournisseur(Long fournisseurId) {
        return achatRepository.findByFournisseurId(fournisseurId);
    }

    @Override
    public List<Achat> getAchatsByStatut(String statut) {
        return achatRepository.findByStatut(Achat.StatutAchat.valueOf(statut));
    }
}