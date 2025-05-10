package org.example.pharma.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.pharma.model.Fournisseur;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
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
    @Column(nullable = false)
    private ModePaiement modePaiement;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('En attente', 'Payé', 'Annulé') DEFAULT 'En attente'")
    private StatutAchat statut;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public enum ModePaiement {
        Espèces, Chèque, Virement, Carte
    }

    public enum StatutAchat {
        En_attente("En attente"), Payé, Annulé;

        private String displayName;

        StatutAchat() {}

        StatutAchat(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName != null ? displayName : name();
        }
    }
}