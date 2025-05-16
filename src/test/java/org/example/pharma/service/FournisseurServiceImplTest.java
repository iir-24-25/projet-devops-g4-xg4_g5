package org.example.pharma.service;

import org.example.pharma.model.Fournisseur;
import org.example.pharma.repository.FournisseurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FournisseurServiceImplTest {

    @Mock
    private FournisseurRepository fournisseurRepository;

    @InjectMocks
    private FournisseurServiceImpl fournisseurService;

    private Fournisseur fournisseur1;
    private Fournisseur fournisseur2;
    private Page<Fournisseur> fournisseurPage;

    @BeforeEach
    void setUp() {
        // Initialisation avec les setters
        fournisseur1 = new Fournisseur();
        fournisseur1.setId(1L);
        fournisseur1.setNom("PharmaPlus");
        fournisseur1.setEmail("contact@pharmaplus.com");
        fournisseur1.setAdresse("Paris");
        fournisseur1.setTelephone("0123456789");
        fournisseur1.setCreatedAt(new Date());
        fournisseur1.setUpdatedAt(new Date());

        fournisseur2 = new Fournisseur();
        fournisseur2.setId(2L);
        fournisseur2.setNom("MediCorp");
        fournisseur2.setEmail("contact@medicorp.com");
        fournisseur2.setAdresse("Lyon");
        fournisseur2.setTelephone("0987654321");
        fournisseur2.setCreatedAt(new Date());
        fournisseur2.setUpdatedAt(new Date());

        List<Fournisseur> fournisseurs = Arrays.asList(fournisseur1, fournisseur2);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nom").ascending());
        fournisseurPage = new PageImpl<>(fournisseurs, pageable, fournisseurs.size());
    }

    @Test
    void getAllFournisseurs_ShouldReturnPageOfFournisseurs() {
        when(fournisseurRepository.findAll(any(Pageable.class))).thenReturn(fournisseurPage);

        Page<Fournisseur> result = fournisseurService.getAllFournisseurs(1, 10, "nom", "ASC");

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("PharmaPlus", result.getContent().get(0).getNom());
        verify(fournisseurRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void searchFournisseurs_ShouldReturnFilteredResults() {
        when(fournisseurRepository.search(eq("Pharma"), any(Pageable.class))).thenReturn(fournisseurPage);

        Page<Fournisseur> result = fournisseurService.searchFournisseurs("Pharma", 1, 10, "nom", "ASC");

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(fournisseurRepository, times(1)).search(eq("Pharma"), any(Pageable.class));
    }

    @Test
    void getFournisseurById_ShouldReturnFournisseur_WhenExists() {
        when(fournisseurRepository.findById(1L)).thenReturn(Optional.of(fournisseur1));

        Fournisseur result = fournisseurService.getFournisseurById(1L);

        assertNotNull(result);
        assertEquals("PharmaPlus", result.getNom());
        verify(fournisseurRepository, times(1)).findById(1L);
    }

    @Test
    void getFournisseurById_ShouldReturnNull_WhenNotExists() {
        when(fournisseurRepository.findById(99L)).thenReturn(Optional.empty());

        Fournisseur result = fournisseurService.getFournisseurById(99L);

        assertNull(result);
        verify(fournisseurRepository, times(1)).findById(99L);
    }

    @Test
    void saveOrUpdateFournisseur_ShouldSaveNewFournisseur() {
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fournisseur1);

        Fournisseur result = fournisseurService.saveOrUpdateFournisseur(fournisseur1);

        assertNotNull(result);
        assertEquals("PharmaPlus", result.getNom());
        verify(fournisseurRepository, times(1)).save(fournisseur1);
    }

    @Test
    void deleteFournisseur_ShouldCallDeleteMethod() {
        doNothing().when(fournisseurRepository).deleteById(1L);

        fournisseurService.deleteFournisseur(1L);

        verify(fournisseurRepository, times(1)).deleteById(1L);
    }

    @Test
    void findAllFournisseurs_ShouldReturnAllFournisseurs() {
        when(fournisseurRepository.findAll()).thenReturn(Arrays.asList(fournisseur1, fournisseur2));

        List<Fournisseur> result = fournisseurService.findAllFournisseurs();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(fournisseurRepository, times(1)).findAll();
    }

    @Test
    void findFournisseurById_ShouldReturnFournisseur_WhenExists() {
        when(fournisseurRepository.findById(1L)).thenReturn(Optional.of(fournisseur1));

        Fournisseur result = fournisseurService.findFournisseurById(1L);

        assertNotNull(result);
        assertEquals("PharmaPlus", result.getNom());
        verify(fournisseurRepository, times(1)).findById(1L);
    }

    @Test
    void findFournisseurById_ShouldThrowException_WhenNotExists() {
        when(fournisseurRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            fournisseurService.findFournisseurById(99L);
        });
        verify(fournisseurRepository, times(1)).findById(99L);
    }
}