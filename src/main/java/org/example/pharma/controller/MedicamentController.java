package org.example.pharma.controller;

import jakarta.validation.Valid;
import org.example.pharma.model.Medicament;
import org.example.pharma.service.MedicamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicaments")
public class MedicamentController {

    private final MedicamentService medicamentService;

    public MedicamentController(MedicamentService medicamentService) {
        this.medicamentService = medicamentService;
    }

    @PostMapping
    public ResponseEntity<Medicament> create(@Valid @RequestBody Medicament medicament) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(medicamentService.createMedicament(medicament));
    }

    @GetMapping
    public ResponseEntity<List<Medicament>> getAll() {
        return ResponseEntity.ok(medicamentService.getAllMedicaments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicament> getById(@PathVariable Long id) {
        return ResponseEntity.ok(medicamentService.getMedicamentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicament> update(@PathVariable Long id,
                                             @Valid @RequestBody Medicament medicament) {
        return ResponseEntity.ok(medicamentService.updateMedicament(id, medicament));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicamentService.deleteMedicament(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/codebarre/{codeBarre}")
    public ResponseEntity<Medicament> getByCodeBarre(@PathVariable String codeBarre) {
        return ResponseEntity.ok(medicamentService.getMedicamentByCodeBarre(codeBarre));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Medicament>> search(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String dci) {
        if (nom != null) {
            return ResponseEntity.ok(medicamentService.searchMedicamentsByName(nom));
        }
        if (dci != null) {
            return ResponseEntity.ok(medicamentService.searchMedicamentsByDci(dci));
        }
        return ResponseEntity.ok(medicamentService.getAllMedicaments());
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Medicament> updateStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        return ResponseEntity.ok(medicamentService.updateStockQuantity(id, quantity));
    }

    @GetMapping("/alert/stock")
    public ResponseEntity<List<Medicament>> getLowStock() {
        return ResponseEntity.ok(medicamentService.getMedicamentsBelowStockThreshold());
    }

    @GetMapping("/alert/expiration")
    public ResponseEntity<List<Medicament>> getExpiringSoon() {
        return ResponseEntity.ok(medicamentService.getExpiringMedicaments());
    }
}