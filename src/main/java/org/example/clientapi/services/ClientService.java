package org.example.clientapi.services;

import lombok.RequiredArgsConstructor;
import org.example.clientapi.Entity.Client;
import org.example.clientapi.Repository.ClientRepo;
import org.example.clientapi.convertors.ClientToDTOConverter;
import org.example.clientapi.convertors.DTOToClientConverter;
import org.example.clientapi.dto.ClientDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с таблицей Client
 */
@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepo clientRepo;
    private final ClientToDTOConverter clientToDTOConverter;
    private final DTOToClientConverter dtoToClientConverter;

    /**
     * Добавление клиента в БД
     *
     * @param dto - DTO клиента
     * @return клиента в формате DTO
     */
    public ClientDTO addClient(ClientDTO dto) {
        Client client = dtoToClientConverter.convert(dto);
        return clientToDTOConverter.convert(clientRepo.save(client));
    }

    /**
     * Получение всех клиентов
     *
     * @return список со всеми клиентами
     */
    public List<ClientDTO> getAllClients() {
        Iterable<Client> clients = clientRepo.findAll();
        List<ClientDTO> clientDTOs = new ArrayList<>();
        clients.forEach(client -> clientDTOs.add(clientToDTOConverter.convert(client)));
        return clientDTOs;
    }

    /**
     * Получение клиента по ID
     *
     * @param clientId Id клиента
     * @return клиента в формате DTO
     */
    public ClientDTO getClientById(Long clientId) {
        return clientToDTOConverter.convert(clientRepo.findById(clientId).orElseThrow(
                () -> new RuntimeException("Client not found")
        ));
    }


}
