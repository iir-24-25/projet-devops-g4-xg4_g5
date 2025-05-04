package org.example.pharma.service;

import org.example.pharma.model.Medicament;
import org.example.pharma.model.dto.MedicamentDTO;
import org.example.pharma.repository.MedicamentRepository;
import org.example.pharma.service.MedicamentService;
import org.springframework.stereotype.Service;



import java.util.List;


@Service
public class MedicamentServiceImpl implements MedicamentService {

    private final MedicamentRepository medicamentRepository;

    public MedicamentServiceImpl(MedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }

    @Override
    public List<Medicament> getAllMedicaments() {
        return medicamentRepository.findAll();
    }

    @Override
    public Medicament getMedicamentById(Long id) {
        return medicamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médicament non trouvé avec l'id: " + id));
    }

    @Override
    public Medicament createMedicament(MedicamentDTO medicamentDTO) {
        Medicament medicament = new Medicament();
        mapDtoToEntity(medicamentDTO, medicament);
        return medicamentRepository.save(medicament);
    }

    @Override
    public Medicament updateMedicament(Long id, MedicamentDTO medicamentDTO) {
        Medicament medicament = medicamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médicament non trouvé avec l'id: " + id));
        mapDtoToEntity(medicamentDTO, medicament);
        return medicamentRepository.save(medicament);
    }

    @Override
    public void deleteMedicament(Long id) {
        Medicament medicament = medicamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médicament non trouvé avec l'id: " + id));
        medicamentRepository.delete(medicament);
    }

    private void mapDtoToEntity(MedicamentDTO dto, Medicament entity) {
        entity.setCodeBarre(dto.getCodeBarre());
        entity.setNom(dto.getNom());
        entity.setDci(dto.getDci());
        entity.setDosage(dto.getDosage());
        entity.setFormeGalenique(dto.getFormeGalenique());
        entity.setClasseTherapeutique(dto.getClasseTherapeutique());
        entity.setLaboratoire(dto.getLaboratoire());
        entity.setPrixAchat(dto.getPrixAchat());
        entity.setPrixVente(dto.getPrixVente());
        entity.setTauxRemboursement(dto.getTauxRemboursement());
        entity.setQuantiteStock(dto.getQuantiteStock());
        entity.setSeuilAlerte(dto.getSeuilAlerte());
        entity.setDatePeremption(dto.getDatePeremption());
        entity.setPrescriptionRequise(dto.isPrescriptionRequise());
    }
}