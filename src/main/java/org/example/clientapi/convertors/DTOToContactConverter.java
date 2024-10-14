package org.example.clientapi.convertors;

import lombok.RequiredArgsConstructor;
import org.example.clientapi.Entity.Contact;
import org.example.clientapi.dto.ContactDTO;
import org.example.clientapi.services.ClientService;
import org.springframework.stereotype.Component;

/**
 * Конвертер DTO в entity Contact
 */
@RequiredArgsConstructor
@Component
public class DTOToContactConverter {
    private final ClientService clientService;
    private final DTOToClientConverter dtoToClientConverter;

    public Contact convert(ContactDTO dto) {
        Contact contact = new Contact();
        contact.setId(dto.getId());
        contact.setType(dto.getType());
        contact.setValue(dto.getValue());
        contact.setClient(dtoToClientConverter.convert(
                clientService.getClientById(dto.getClientId())
        ));
        return contact;
    }
}
