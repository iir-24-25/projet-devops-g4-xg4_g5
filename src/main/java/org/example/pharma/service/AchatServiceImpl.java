package org.example.pharma.service;

import org.example.pharma.model.Achat;
import org.example.pharma.repository.AchatRepository;
import org.example.pharma.service.AchatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AchatServiceImpl implements AchatService {

    private final AchatRepository achatRepository;

    @Autowired
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
    public Achat updateAchat(Long id, Achat updatedAchat) {
        return achatRepository.findById(id)
                .map(existingAchat -> {
                    existingAchat.setFournisseur(updatedAchat.getFournisseur());
                    existingAchat.setDateAchat(updatedAchat.getDateAchat());
                    existingAchat.setMontantTotal(updatedAchat.getMontantTotal());
                    existingAchat.setModePaiement(updatedAchat.getModePaiement());
                    existingAchat.setStatut(updatedAchat.getStatut());
                    existingAchat.setNotes(updatedAchat.getNotes());
                    return achatRepository.save(existingAchat);
                })
                .orElse(null);
    }

    @Override
    public void deleteAchat(Long id) {
        achatRepository.deleteById(id);
    }
}