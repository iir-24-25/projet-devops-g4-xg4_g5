package org.example.pharma.controller;

import org.example.pharma.model.Achat;
import org.example.pharma.service.AchatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achats")
@CrossOrigin(origins = "*") // Autoriser les requêtes depuis n'importe quelle origine
public class AchatController {

    private final AchatService achatService;

    public AchatController(AchatService achatService) {
        this.achatService = achatService;
    }

    @GetMapping
    public List<Achat> getAllAchats() {
        return achatService.getAllAchats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Achat> getAchatById(@PathVariable Long id) {
        return ResponseEntity.ok(achatService.getAchatById(id));
    }

    @PostMapping
    public ResponseEntity<Achat> createAchat(@RequestBody Achat achat) {
        return ResponseEntity.ok(achatService.createAchat(achat));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Achat> updateAchat(@PathVariable Long id, @RequestBody Achat achat) {
        return ResponseEntity.ok(achatService.updateAchat(id, achat));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchat(@PathVariable Long id) {
        achatService.deleteAchat(id);
        return ResponseEntity.noContent().build();
    }
}