package org.example.pharma.service;

import org.example.pharma.model.*;
import org.example.pharma.repository.VenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VenteServiceImplTest {

    @Mock
    private VenteRepository venteRepository;

    @InjectMocks
    private VenteServiceImpl venteService;

    private Vente vente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Client client = new Client();
        client.setId(1L);

        vente = new Vente();
        vente.setId(1L);
        vente.setClient(client);
        vente.setMontantTotal(new BigDecimal("100.00"));
        vente.setModePaiement(ModePaiement.ESPECES);
        vente.setStatut(StatutVente.EN_ATTENTE);
        vente.setReference("REF123");
        vente.setNotes("Test note");
    }

    @Test
    void testCreateVente() {
        when(venteRepository.save(any(Vente.class))).thenReturn(vente);

        Vente saved = venteService.createVente(vente);

        assertNotNull(saved);
        assertEquals("REF123", saved.getReference());
        verify(venteRepository, times(1)).save(vente);
    }

    @Test
    void testGetAllVentes() {
        when(venteRepository.findAll()).thenReturn(List.of(vente));

        List<Vente> ventes = venteService.getAllVentes();

        assertEquals(1, ventes.size());
        verify(venteRepository).findAll();
    }

    @Test
    void testGetVenteById() {
        when(venteRepository.findById(1L)).thenReturn(Optional.of(vente));

        Vente found = venteService.getVenteById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
    }

    @Test
    void testUpdateVente() {
        Vente updatedVente = new Vente();
        updatedVente.setClient(vente.getClient());
        updatedVente.setMontantTotal(new BigDecimal("200.00"));
        updatedVente.setModePaiement(ModePaiement.CARTE);

        when(venteRepository.findById(1L)).thenReturn(Optional.of(vente));
        when(venteRepository.save(any(Vente.class))).thenReturn(updatedVente);

        Vente result = venteService.updateVente(1L, updatedVente);

        assertEquals(new BigDecimal("200.00"), result.getMontantTotal());
        assertEquals(ModePaiement.CARTE, result.getModePaiement());
        verify(venteRepository).save(updatedVente);
    }

    @Test
    void testDeleteVente() {
        when(venteRepository.findById(1L)).thenReturn(Optional.of(vente));
        doNothing().when(venteRepository).delete(vente);

        venteService.deleteVente(1L);

        verify(venteRepository).delete(vente);
    }

    @Test
    void testGetVentesByClient() {
        when(venteRepository.findByClientId(1L)).thenReturn(List.of(vente));

        List<Vente> ventes = venteService.getVentesByClient(1L);

        assertFalse(ventes.isEmpty());
        verify(venteRepository).findByClientId(1L);
    }

    @Test
    void testGetVentesByStatut() {
        when(venteRepository.findByStatut(StatutVente.EN_ATTENTE)).thenReturn(List.of(vente));

        List<Vente> ventes = venteService.getVentesByStatut(StatutVente.EN_ATTENTE);

        assertEquals(1, ventes.size());
        verify(venteRepository).findByStatut(StatutVente.EN_ATTENTE);
    }

    @Test
    void testGetVentesByModePaiement() {
        when(venteRepository.findByModePaiement(ModePaiement.ESPECES)).thenReturn(List.of(vente));

        List<Vente> ventes = venteService.getVentesByModePaiement(ModePaiement.ESPECES);

        assertEquals(1, ventes.size());
        verify(venteRepository).findByModePaiement(ModePaiement.ESPECES);
    }

    @Test
    void testGetVenteById_NotFound() {
        when(venteRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            venteService.getVenteById(99L);
        });

        assertTrue(exception.getMessage().contains("Vente non trouvée"));
    }
}
