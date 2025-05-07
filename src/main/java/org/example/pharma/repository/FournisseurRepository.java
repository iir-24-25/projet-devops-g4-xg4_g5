package org.example.pharma.repository;



import org.example.pharma.model.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

    @Query("SELECT f FROM Fournisseur f WHERE " +
            "LOWER(f.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(f.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(f.telephone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(f.adresse) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(f.produitsFournis) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Fournisseur> search(@Param("searchTerm") String searchTerm, Pageable pageable);
    List<Fournisseur> findByNomContainingIgnoreCase(String nom);

}