package org.example.clientapi.services;

import lombok.RequiredArgsConstructor;
import org.example.clientapi.Entity.Client;
import org.example.clientapi.Entity.Contact;
import org.example.clientapi.Repository.ClientRepo;
import org.example.clientapi.Repository.ContactRepo;
import org.example.clientapi.convertors.ContactToDTOConverter;
import org.example.clientapi.dto.ContactDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с таблицей Contact
 */
@Service
@RequiredArgsConstructor
public class ContactService {
    private final ClientRepo clientRepo;
    private final ContactRepo contactRepo;
    private final ContactToDTOConverter contactToDTOConverter;

    /**
     * Добавление контакта к клиенту
     *
     * @param clientId ID клиента, к которому требуется добавить контакт
     * @param dto      Контакт в формате DTO
     * @return Контакт в формате DTO
     */
    public ContactDTO addContact(Long clientId, ContactDTO dto) {
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new NullPointerException("Client not found"));

        Contact contact = new Contact();
        contact.setType(dto.getType());
        contact.setValue(dto.getValue());
        contact.setClient(client);
        Contact savedContact = contactRepo.save(contact);
        return contactToDTOConverter.convert(savedContact);
    }

    /**
     * Получение списка контактов у клиента (по ID)
     *
     * @param clientId ID клиента
     * @return список контактов в формате DTO
     */
    public List<ContactDTO> getContactsByClientId(Long clientId) {
        List<Contact> contacts = contactRepo.findByClientId(clientId);
        return contacts.stream()
                .map(contactToDTOConverter::convert)
                .collect(Collectors.toList());
    }

    /**
     * Получение списка контактов у клиента (по ID и типу контакта)
     *
     * @param clientId ID клиента
     * @param type     тип контакта (номер телефона, электронная почта)
     * @return список контактов в формате DTO
     */
    public List<ContactDTO> getContactsByClientIdAndType(Long clientId, String type) {
        List<Contact> contacts = contactRepo.findByClientIdAndType(clientId, type);
        return contacts.stream()
                .map(contactToDTOConverter::convert)
                .collect(Collectors.toList());
    }
}
