package org.example.pharma.repository;

import org.example.pharma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrderByNameAsc();
    boolean existsByEmail(String email);
    Optional<User> findById(Long id); // Modifié pour retourner Optional<User>
}