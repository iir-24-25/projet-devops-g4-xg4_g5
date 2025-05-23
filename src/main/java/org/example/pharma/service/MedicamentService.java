package org.example.pharma.service;

import org.example.pharma.model.Medicament;

import java.util.List;

public interface MedicamentService {
    Medicament createMedicament(Medicament medicament);
    Medicament updateMedicament(Long id, Medicament medicament);
    void deleteMedicament(Long id);
    Medicament getMedicamentById(Long id);
    Medicament getMedicamentByCodeBarre(String codeBarre);
    List<Medicament> getAllMedicaments();
    List<Medicament> searchMedicamentsByName(String nom);
    List<Medicament> searchMedicamentsByDci(String dci);
    List<Medicament> getMedicamentsBelowStockThreshold();
    List<Medicament> getExpiringMedicaments();
    List<Medicament> getMedicamentsRequiringPrescription();
    List<Medicament> getMedicamentsByTherapeuticClass(String therapeuticClass);
    Medicament updateStockQuantity(Long id, int quantityChange);
}