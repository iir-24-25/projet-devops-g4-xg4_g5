package org.example.pharma.controller;

import org.example.pharma.model.Fournisseur;
import org.example.pharma.service.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fournisseurs")
@CrossOrigin(origins = "http://localhost:8080")
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    @GetMapping
    public Page<Fournisseur> getAllFournisseurs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return fournisseurService.getAllFournisseurs(page, size, sortField, sortDirection);
    }

    @GetMapping("/search")
    public Page<Fournisseur> searchFournisseurs(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return fournisseurService.searchFournisseurs(searchTerm, page, size, sortField, sortDirection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable Long id) {
        Fournisseur fournisseur = fournisseurService.getFournisseurById(id);
        return ResponseEntity.ok(fournisseur);
    }

    @PostMapping
    public Fournisseur createFournisseur(@RequestBody Fournisseur fournisseur) {
        return fournisseurService.saveOrUpdateFournisseur(fournisseur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable Long id, @RequestBody Fournisseur fournisseurDetails) {
        Fournisseur fournisseur = fournisseurService.getFournisseurById(id);
        if (fournisseur == null) {
            return ResponseEntity.notFound().build();
        }

        fournisseur.setNom(fournisseurDetails.getNom());
        fournisseur.setAdresse(fournisseurDetails.getAdresse());
        fournisseur.setTelephone(fournisseurDetails.getTelephone());
        fournisseur.setEmail(fournisseurDetails.getEmail());
        fournisseur.setProduitsFournis(fournisseurDetails.getProduitsFournis());

        Fournisseur updatedFournisseur = fournisseurService.saveOrUpdateFournisseur(fournisseur);
        return ResponseEntity.ok(updatedFournisseur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFournisseur(@PathVariable Long id) {
        fournisseurService.deleteFournisseur(id);
        return ResponseEntity.ok().build();
    }
}