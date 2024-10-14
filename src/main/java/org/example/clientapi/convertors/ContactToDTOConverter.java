package org.example.clientapi.convertors;

import org.example.clientapi.Entity.Contact;
import org.example.clientapi.dto.ContactDTO;
import org.springframework.stereotype.Component;

/**
 * Конвертер сущности Contact в DTO
 */
@Component
public class ContactToDTOConverter {
    public ContactDTO convert(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setType(contact.getType());
        dto.setValue(contact.getValue());
        dto.setClientId(contact.getClient().getId());
        return dto;
    }
}
