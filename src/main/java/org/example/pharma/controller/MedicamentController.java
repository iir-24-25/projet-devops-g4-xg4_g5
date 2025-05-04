package org.example.pharma.controller;

import org.example.pharma.model.Medicament;
import org.example.pharma.model.dto.MedicamentDTO;
import org.example.pharma.service.MedicamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/medicaments")
@CrossOrigin(origins = "http://localhost:8080") // Ajustez selon votre configuration
public class MedicamentController {

    private final MedicamentService medicamentService;

    public MedicamentController(MedicamentService medicamentService) {
        this.medicamentService = medicamentService;
    }

    @GetMapping
    public ResponseEntity<List<Medicament>> getAllMedicaments() {
        return ResponseEntity.ok(medicamentService.getAllMedicaments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicament> getMedicamentById(@PathVariable Long id) {
        return ResponseEntity.ok(medicamentService.getMedicamentById(id));
    }

    @PostMapping
    public ResponseEntity<Medicament> createMedicament(@RequestBody MedicamentDTO medicamentDTO) {
        return ResponseEntity.ok(medicamentService.createMedicament(medicamentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicament> updateMedicament(
            @PathVariable Long id,
            @RequestBody MedicamentDTO medicamentDTO) {
        return ResponseEntity.ok(medicamentService.updateMedicament(id, medicamentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicament(@PathVariable Long id) {
        medicamentService.deleteMedicament(id);
        return ResponseEntity.noContent().build();
    }
}