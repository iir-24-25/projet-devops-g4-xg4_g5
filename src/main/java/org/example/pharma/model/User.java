//package org.example.pharma.model;
//
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "users")
//@Data
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "nom_complet", nullable = false)
//    private String nomComplet;
//
//    @Column(nullable = false, unique = true)
//    private String email;
//
//    @Column(nullable = false, unique = true)
//    private String username;
//
//    @Column(name = "password_hash", nullable = false)
//    private String password;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;
//
//    @CreationTimestamp
//    @Column(name = "date_creation", updatable = false)
//    private LocalDateTime dateCreation;
//
//    @UpdateTimestamp
//    @Column(name = "date_modification")
//    private LocalDateTime dateModification;
//
//    public enum Role {
//        ADMIN, PHARMACIEN
//    }
//}