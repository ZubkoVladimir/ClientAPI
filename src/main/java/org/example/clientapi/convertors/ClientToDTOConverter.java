package org.example.clientapi.convertors;

import org.example.clientapi.Entity.Client;
import org.example.clientapi.dto.ClientDTO;
import org.springframework.stereotype.Component;

/**
 * Конвертер entity Client в DTO
 */
@Component
public class ClientToDTOConverter {

    public ClientDTO convert(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        return dto;
    }
}
