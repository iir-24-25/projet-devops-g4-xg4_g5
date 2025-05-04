package org.example.pharma.service;

import org.example.pharma.model.Medicament;
import org.example.pharma.model.dto.MedicamentDTO;

import java.util.List;




public interface MedicamentService {
    List<Medicament> getAllMedicaments();
    Medicament getMedicamentById(Long id);
    Medicament createMedicament(MedicamentDTO medicamentDTO);
    Medicament updateMedicament(Long id, MedicamentDTO medicamentDTO);
    void deleteMedicament(Long id);
}