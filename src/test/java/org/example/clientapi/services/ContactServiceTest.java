package org.example.clientapi.services;

import org.example.clientapi.Entity.Client;
import org.example.clientapi.Entity.Contact;
import org.example.clientapi.Repository.ClientRepo;
import org.example.clientapi.Repository.ContactRepo;
import org.example.clientapi.convertors.ContactToDTOConverter;
import org.example.clientapi.dto.ContactDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ContactServiceTest {

    @Mock
    private ContactRepo contactRepository;

    @Mock
    private ClientRepo clientRepository;
    @Mock
    private ContactToDTOConverter contactToDTOConverter;


    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addContact() {
        Client client = new Client();
        client.setId(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setType("email");
        contactDTO.setValue("email@mail.com");
        Contact contact = new Contact();
        contact.setType("email");
        contact.setValue("email@mail.com");

        when(contactRepository.save(any())).thenReturn(contact);
        when(contactToDTOConverter.convert(any())).thenReturn(contactDTO);


        ContactDTO createdContact = contactService.addContact(1L, contactDTO);

        assertNotNull(createdContact);
        assertEquals("email", createdContact.getType());
        assertEquals("email@mail.com", createdContact.getValue());
    }

    @Test
    public void getContactsByClientId() {
        Client client = new Client();
        client.setId(1L);

        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setType("email");
        contact1.setValue("email@mail.com");
        contact1.setClient(client);

        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setType("phone");
        contact2.setValue("123456789");
        contact2.setClient(client);

        when(contactRepository.findByClientId(1L)).thenReturn(Arrays.asList(contact1, contact2));

        when(contactToDTOConverter.convert(contact1)).thenAnswer(invocation -> {
            ContactDTO dto = new ContactDTO();
            dto.setType(contact1.getType());
            dto.setValue(contact1.getValue());
            return dto;
        });
        when(contactToDTOConverter.convert(contact2)).thenAnswer(invocation -> {
            ContactDTO dto = new ContactDTO();
            dto.setType(contact2.getType());
            dto.setValue(contact2.getValue());
            return dto;
        });

        List<ContactDTO> contacts = contactService.getContactsByClientId(1L);

        assertEquals(2, contacts.size());
        assertEquals("email", contacts.get(0).getType());
        assertEquals("phone", contacts.get(1).getType());
    }
}
