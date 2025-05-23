package org.example.pharma.service;

import org.example.pharma.model.Client;
import org.example.pharma.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    private ClientRepository clientRepository;
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        clientService = new ClientServiceImpl(clientRepository);
    }

    @Test
    void testGetAllClients() {
        List<Client> clients = List.of(new Client(), new Client());
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        assertEquals(2, result.size());
        verify(clientRepository).findAll();
    }

    @Test
    void testGetClientById_Success() {
        Client client = new Client();
        client.setId(1L);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client result = clientService.getClientById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(clientRepository).findById(1L);
    }

    @Test
    void testGetClientById_NotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clientService.getClientById(1L);
        });

        assertEquals("Client non trouvé avec l'id: 1", exception.getMessage());
    }

    @Test
    void testCreateClient() {
        Client client = new Client();
        client.setNom("Dupont");

        when(clientRepository.save(client)).thenReturn(client);

        Client result = clientService.createClient(client);

        assertEquals("Dupont", result.getNom());
        verify(clientRepository).save(client);
    }

    @Test
    void testUpdateClient() {
        Client existing = new Client();
        existing.setId(1L);
        existing.setNom("Ancien");

        Client details = new Client();
        details.setNom("Nouveau");
        details.setPrenom("Jean");
        details.setTelephone("0612345678");
        details.setEmail("jean@example.com");
//        details.setTypeClient("Particulier");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(clientRepository.save(existing)).thenReturn(existing);

        Client result = clientService.updateClient(1L, details);

        assertEquals("Nouveau", result.getNom());
        assertEquals("Jean", result.getPrenom());
        assertEquals("0612345678", result.getTelephone());
        verify(clientRepository).save(existing);
    }

    @Test
    void testDeleteClient() {
        Client client = new Client();
        client.setId(1L);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).delete(client);

        clientService.deleteClient(1L);

        verify(clientRepository).delete(client);
    }
}
