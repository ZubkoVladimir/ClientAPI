package org.example.clientapi.services;

import org.example.clientapi.Entity.Client;
import org.example.clientapi.Repository.ClientRepo;
import org.example.clientapi.convertors.ClientToDTOConverter;
import org.example.clientapi.convertors.DTOToClientConverter;
import org.example.clientapi.dto.ClientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ClientServiceTest {
    @Mock
    private ClientRepo clientRepository;

    @InjectMocks
    private ClientService clientService;
    @Mock
    private DTOToClientConverter dtoToClientConverter;

    @Mock
    private ClientToDTOConverter clientToDTOConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addClient_ShouldReturnCreatedClientDTO() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("John Doe");

        Client client = new Client();
        client.setId(1L);
        client.setName("John Doe");

        when(dtoToClientConverter.convert(clientDTO)).thenReturn(client);
        when(clientToDTOConverter.convert(client)).thenReturn(clientDTO);
        when(clientRepository.save(client)).thenReturn(client);

        ClientDTO createdClientDTO = clientService.addClient(clientDTO);

        assertNotNull(createdClientDTO);
        assertEquals("John Doe", createdClientDTO.getName());

        verify(dtoToClientConverter, times(1)).convert(clientDTO);
        verify(clientRepository, times(1)).save(client);
        verify(clientToDTOConverter, times(1)).convert(client);
    }

    @Test
    public void getAllClients_ShouldReturnListOfClientDTOs() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("John Doe");

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Jane Doe");

        ClientDTO dto1 = new ClientDTO();
        dto1.setName("John Doe");

        ClientDTO dto2 = new ClientDTO();
        dto2.setName("Jane Doe");

        List<Client> clients = Arrays.asList(client1, client2);

        when(clientRepository.findAll()).thenReturn(clients);
        when(clientToDTOConverter.convert(client1)).thenReturn(dto1);
        when(clientToDTOConverter.convert(client2)).thenReturn(dto2);

        List<ClientDTO> clientDTOs = clientService.getAllClients();

        assertNotNull(clientDTOs);
        assertEquals(2, clientDTOs.size());
        assertEquals("John Doe", clientDTOs.get(0).getName());
        assertEquals("Jane Doe", clientDTOs.get(1).getName());

        verify(clientRepository, times(1)).findAll();
        verify(clientToDTOConverter, times(1)).convert(client1);
        verify(clientToDTOConverter, times(1)).convert(client2);
    }

    @Test
    public void getClientById_ShouldReturnClientDTO() {
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        client.setName("John Doe");

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(clientId);
        clientDTO.setName("John Doe");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(clientToDTOConverter.convert(client)).thenReturn(clientDTO);

        ClientDTO result = clientService.getClientById(clientId);

        assertNotNull(result);
        assertEquals(clientId, result.getId());
        assertEquals("John Doe", result.getName());

        verify(clientRepository, times(1)).findById(clientId);
        verify(clientToDTOConverter, times(1)).convert(client);
    }
}