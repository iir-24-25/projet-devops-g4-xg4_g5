package org.example.pharma.service;

import org.example.pharma.model.Medicament;
import org.example.pharma.repository.MedicamentRepository;
import org.example.pharma.service.MedicamentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MedicamentServiceImpl implements MedicamentService {

    private static final Logger logger = LoggerFactory.getLogger(MedicamentServiceImpl.class);
    private final MedicamentRepository medicamentRepository;

    @Autowired
    public MedicamentServiceImpl(MedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }

    @Override
    public Medicament createMedicament(Medicament medicament) {
        logger.info("Tentative de création d'un nouveau médicament avec code barre: {}", medicament.getCodeBarre());

        if (medicamentRepository.findByCodeBarre(medicament.getCodeBarre()).isPresent()) {
            logger.error("Échec de la création - Un médicament avec le code barre {} existe déjà", medicament.getCodeBarre());
            throw new RuntimeException("Un médicament avec ce code barre existe déjà");
        }

        Medicament savedMedicament = medicamentRepository.save(medicament);
        logger.info("Médicament créé avec succès - ID: {}, Nom: {}", savedMedicament.getId(), savedMedicament.getNom());
        return savedMedicament;
    }

    @Override
    public Medicament updateMedicament(Long id, Medicament medicament) {
        logger.debug("Début de mise à jour du médicament ID: {}", id);

        Medicament existing = medicamentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Échec de la mise à jour - Médicament non trouvé avec l'ID: {}", id);
                    return new RuntimeException("Médicament non trouvé avec l'ID: " + id);
                });

        logger.info("Mise à jour du médicament ID: {} - Ancien nom: {}, Nouveau nom: {}",
                id, existing.getNom(), medicament.getNom());

        existing.setCodeBarre(medicament.getCodeBarre());
        existing.setNom(medicament.getNom());
        existing.setDci(medicament.getDci());
        existing.setDosage(medicament.getDosage());
        existing.setFormeGalenique(medicament.getFormeGalenique());
        existing.setClasseTherapeutique(medicament.getClasseTherapeutique());
        existing.setLaboratoire(medicament.getLaboratoire());
        existing.setPrixAchat(medicament.getPrixAchat());
        existing.setPrixVente(medicament.getPrixVente());
        existing.setTauxRemboursement(medicament.getTauxRemboursement());
        existing.setQuantiteStock(medicament.getQuantiteStock());
        existing.setSeuilAlerte(medicament.getSeuilAlerte());
        existing.setDatePeremption(medicament.getDatePeremption());
        existing.setPrescriptionRequise(medicament.isPrescriptionRequise());

        Medicament updatedMedicament = medicamentRepository.save(existing);
        logger.info("Mise à jour réussie du médicament ID: {}", id);
        return updatedMedicament;
    }

    @Override
    public void deleteMedicament(Long id) {
        logger.info("Tentative de suppression du médicament ID: {}", id);

        if (!medicamentRepository.existsById(id)) {
            logger.error("Échec de la suppression - Médicament non trouvé avec l'ID: {}", id);
            throw new RuntimeException("Médicament non trouvé avec l'ID: " + id);
        }

        medicamentRepository.deleteById(id);
        logger.info("Médicament ID: {} supprimé avec succès", id);
    }

    @Override
    public Medicament getMedicamentById(Long id) {
        logger.debug("Recherche du médicament par ID: {}", id);

        return medicamentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Médicament non trouvé avec l'ID: {}", id);
                    return new RuntimeException("Médicament non trouvé avec l'ID: " + id);
                });
    }

    @Override
    public Medicament getMedicamentByCodeBarre(String codeBarre) {
        logger.debug("Recherche du médicament par code barre: {}", codeBarre);

        return medicamentRepository.findByCodeBarre(codeBarre)
                .orElseThrow(() -> {
                    logger.error("Médicament non trouvé avec le code barre: {}", codeBarre);
                    return new RuntimeException("Médicament non trouvé avec le code barre: " + codeBarre);
                });
    }

    @Override
    public List<Medicament> getAllMedicaments() {
        logger.info("Récupération de tous les médicaments");
        List<Medicament> medicaments = medicamentRepository.findAll();
        logger.debug("Nombre de médicaments récupérés: {}", medicaments.size());
        return medicaments;
    }

    @Override
    public List<Medicament> searchMedicamentsByName(String nom) {
        logger.info("Recherche de médicaments par nom contenant: {}", nom);
        return medicamentRepository.findByNomContainingIgnoreCase(nom);
    }

    @Override
    public List<Medicament> searchMedicamentsByDci(String dci) {
        logger.info("Recherche de médicaments par DCI contenant: {}", dci);
        return medicamentRepository.findByDciContainingIgnoreCase(dci);
    }

    @Override
    public List<Medicament> getMedicamentsBelowStockThreshold() {
        logger.info("Récupération des médicaments en dessous du seuil de stock");
        List<Medicament> medicaments = medicamentRepository.findByQuantiteStockLessThan(5);
        logger.debug("Nombre de médicaments en alerte de stock: {}", medicaments.size());
        return medicaments;
    }

    @Override
    public List<Medicament> getExpiringMedicaments() {
        LocalDate thresholdDate = LocalDate.now().plusMonths(3);
        logger.info("Récupération des médicaments expirant avant: {}", thresholdDate);
        List<Medicament> medicaments = medicamentRepository.findByDatePeremptionBefore(thresholdDate);
        logger.debug("Nombre de médicaments proches de l'expiration: {}", medicaments.size());
        return medicaments;
    }

    @Override
    public List<Medicament> getMedicamentsRequiringPrescription() {
        logger.info("Récupération des médicaments nécessitant une prescription");
        List<Medicament> medicaments = medicamentRepository.findByPrescriptionRequise(true);
        logger.debug("Nombre de médicaments nécessitant une prescription: {}", medicaments.size());
        return medicaments;
    }

    @Override
    public List<Medicament> getMedicamentsByTherapeuticClass(String therapeuticClass) {
        logger.info("Recherche de médicaments par classe thérapeutique: {}", therapeuticClass);
        return medicamentRepository.findByClasseTherapeutique(therapeuticClass);
    }

    @Override
    public Medicament updateStockQuantity(Long id, int quantityChange) {
        logger.info("Mise à jour du stock pour le médicament ID: {} - Changement: {}", id, quantityChange);

        Medicament medicament = getMedicamentById(id);
        int newQuantity = medicament.getQuantiteStock() + quantityChange;

        if (newQuantity < 0) {
            logger.error("Tentative de mise à jour invalide - Stock deviendrait négatif: {}", newQuantity);
            throw new RuntimeException("La quantité en stock ne peut pas être négative");
        }

        medicament.setQuantiteStock(newQuantity);
        Medicament updatedMedicament = medicamentRepository.save(medicament);

        logger.info("Stock mis à jour pour le médicament ID: {} - Nouvelle quantité: {}",
                id, updatedMedicament.getQuantiteStock());
        return updatedMedicament;
    }
}