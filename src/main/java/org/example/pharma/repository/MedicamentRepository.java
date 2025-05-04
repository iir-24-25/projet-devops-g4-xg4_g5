package org.example.pharma.repository;

import org.example.pharma.model.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
}