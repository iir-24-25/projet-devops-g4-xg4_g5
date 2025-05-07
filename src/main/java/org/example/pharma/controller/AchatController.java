package org.example.pharma.controller;

import org.example.pharma.model.Achat;
import org.example.pharma.service.AchatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achats")
public class AchatController {

    private final AchatService achatService;

    @Autowired
    public AchatController(AchatService achatService) {
        this.achatService = achatService;
    }

    @GetMapping
    public List<Achat> getAllAchats() {
        return achatService.getAllAchats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Achat> getAchatById(@PathVariable Long id) {
        Achat achat = achatService.getAchatById(id);
        return achat != null ? ResponseEntity.ok(achat) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Achat createAchat(@RequestBody Achat achat) {
        return achatService.createAchat(achat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Achat> updateAchat(@PathVariable Long id, @RequestBody Achat achat) {
        Achat updatedAchat = achatService.updateAchat(id, achat);
        return updatedAchat != null ? ResponseEntity.ok(updatedAchat) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchat(@PathVariable Long id) {
        achatService.deleteAchat(id);
        return ResponseEntity.noContent().build();
    }
}