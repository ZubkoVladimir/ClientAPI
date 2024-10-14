package org.example.clientapi.convertors;

import org.example.clientapi.Entity.Client;
import org.example.clientapi.dto.ClientDTO;
import org.springframework.stereotype.Component;

/**
 * Конвертер DTO в entity Client
 */
@Component
public class DTOToClientConverter {
    public Client convert(ClientDTO dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        return client;
    }
}
