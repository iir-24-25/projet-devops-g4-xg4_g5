package org.example.pharma.model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "medicaments")
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeBarre;
    private String nom;
    private String dci;
    private String dosage;
    private String formeGalenique;
    private String classeTherapeutique;
    private String laboratoire;
    private double prixAchat;
    private double prixVente;
    private double tauxRemboursement;
    private int quantiteStock;
    private int seuilAlerte;
    private LocalDate datePeremption;
    private boolean prescriptionRequise;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDci() {
        return dci;
    }

    public void setDci(String dci) {
        this.dci = dci;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFormeGalenique() {
        return formeGalenique;
    }

    public void setFormeGalenique(String formeGalenique) {
        this.formeGalenique = formeGalenique;
    }

    public String getClasseTherapeutique() {
        return classeTherapeutique;
    }

    public void setClasseTherapeutique(String classeTherapeutique) {
        this.classeTherapeutique = classeTherapeutique;
    }

    public String getLaboratoire() {
        return laboratoire;
    }

    public void setLaboratoire(String laboratoire) {
        this.laboratoire = laboratoire;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public double getTauxRemboursement() {
        return tauxRemboursement;
    }

    public void setTauxRemboursement(double tauxRemboursement) {
        this.tauxRemboursement = tauxRemboursement;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public int getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(int seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public LocalDate getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(LocalDate datePeremption) {
        this.datePeremption = datePeremption;
    }

    public boolean isPrescriptionRequise() {
        return prescriptionRequise;
    }

    public void setPrescriptionRequise(boolean prescriptionRequise) {
        this.prescriptionRequise = prescriptionRequise;
    }
}