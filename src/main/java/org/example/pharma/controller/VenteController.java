package org.example.pharma.controller;

import org.example.pharma.model.Vente;
import org.example.pharma.model.StatutVente;
import org.example.pharma.model.ModePaiement;
import org.example.pharma.service.VenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ventes")
public class VenteController {

    private final VenteService venteService;

    public VenteController(VenteService venteService) {
        this.venteService = venteService;
    }

    @GetMapping
    public ResponseEntity<List<Vente>> getAllVentes() {
        return ResponseEntity.ok(venteService.getAllVentes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vente> getVenteById(@PathVariable Long id) {
        return ResponseEntity.ok(venteService.getVenteById(id));
    }

    @PostMapping
    public ResponseEntity<Vente> createVente(@RequestBody Vente vente) {
        return new ResponseEntity<>(venteService.createVente(vente), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vente> updateVente(@PathVariable Long id, @RequestBody Vente vente) {
        return ResponseEntity.ok(venteService.updateVente(id, vente));
    }
    @PostMapping("/import")
    public ResponseEntity<List<Vente>> importVentes(@RequestBody List<Vente> ventes) {
        List<Vente> savedVentes = ventes.stream()
                .map(venteService::createVente)
                .collect(Collectors.toList());
        return ResponseEntity.ok(savedVentes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVente(@PathVariable Long id) {
        venteService.deleteVente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Vente>> getVentesByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(venteService.getVentesByClient(clientId));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Vente>> getVentesByStatut(@PathVariable StatutVente statut) {
        return ResponseEntity.ok(venteService.getVentesByStatut(statut));
    }

    @GetMapping("/paiement/{modePaiement}")
    public ResponseEntity<List<Vente>> getVentesByModePaiement(@PathVariable ModePaiement modePaiement) {
        return ResponseEntity.ok(venteService.getVentesByModePaiement(modePaiement));
    }
}