package org.example.pharma.repository;

import org.example.pharma.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;




public interface ClientRepository extends JpaRepository<Client, Long> {
}