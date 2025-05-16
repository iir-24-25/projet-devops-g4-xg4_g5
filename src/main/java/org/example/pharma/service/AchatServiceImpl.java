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
        return achatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Achat non trouvé avec l'ID: " + id));
    }

    @Override
    public Achat createAchat(Achat achat) {
        return achatRepository.save(achat);
    }

    @Override
    public Achat updateAchat(Long id, Achat achatDetails) {
        Achat achat = getAchatById(id);
        achat.setNomFournisseur(achatDetails.getNomFournisseur());
        achat.setDateAchat(achatDetails.getDateAchat());
        achat.setMontantTotal(achatDetails.getMontantTotal());
        achat.setModePaiement(achatDetails.getModePaiement());
        achat.setStatut(achatDetails.getStatut());
        achat.setNotes(achatDetails.getNotes());
        return achatRepository.save(achat);
    }

    @Override
    public void deleteAchat(Long id) {
        Achat achat = getAchatById(id);
        achatRepository.delete(achat);
    }
}