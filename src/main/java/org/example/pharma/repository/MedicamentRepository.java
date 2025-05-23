package org.example.pharma.repository;

import org.example.pharma.model.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
    Optional<Medicament> findByCodeBarre(String codeBarre);
    List<Medicament> findByNomContainingIgnoreCase(String nom);
    List<Medicament> findByDciContainingIgnoreCase(String dci);
    List<Medicament> findByClasseTherapeutique(String classeTherapeutique);
    List<Medicament> findByQuantiteStockLessThan(int seuil);
    List<Medicament> findByDatePeremptionBefore(LocalDate date);
    List<Medicament> findByPrescriptionRequise(boolean prescriptionRequise);
}