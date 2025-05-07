package org.example.pharma.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "achats")
public class Achat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fournisseur_id", nullable = false)
    private Fournisseur fournisseur;

    @Column(name = "date_achat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateAchat;

    @Column(name = "montant_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_paiement", nullable = false)
    private ModePaiement modePaiement;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", columnDefinition = "ENUM('En attente', 'Payé', 'Annulé') DEFAULT 'En attente'")
    private StatutAchat statut = StatutAchat.EN_ATTENTE;

    @Column(name = "notes")
    private String notes;

    // Getters & Setters
    public Long getId() { return id; }
    public Fournisseur getFournisseur() { return fournisseur; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }
    public Date getDateAchat() { return dateAchat; }
    public void setDateAchat(Date dateAchat) { this.dateAchat = dateAchat; }
    public BigDecimal getMontantTotal() { return montantTotal; }
    public void setMontantTotal(BigDecimal montantTotal) { this.montantTotal = montantTotal; }
    public ModePaiement getModePaiement() { return modePaiement; }
    public void setModePaiement(ModePaiement modePaiement) { this.modePaiement = modePaiement; }
    public StatutAchat getStatut() { return statut; }
    public void setStatut(StatutAchat statut) { this.statut = statut; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // Enums
    public enum ModePaiement { ESPECES, CHEQUE, VIREMENT, CARTE }
    public enum StatutAchat { EN_ATTENTE, PAYE, ANNULE }
}