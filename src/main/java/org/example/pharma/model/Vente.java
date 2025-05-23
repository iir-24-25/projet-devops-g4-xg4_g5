package org.example.pharma.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ventes")
@Data
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "date_vente", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateVente = new Date();

    @Column(name = "montant_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", length = 20)
    private StatutVente statut = StatutVente.EN_ATTENTE;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_paiement", length = 20)
    private org.example.pharma.model.ModePaiement  modePaiement;

    @Column(name = "reference", length = 50)
    private String reference;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();



    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();
}