package org.example.clientapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.clientapi.dto.ContactDTO;
import org.example.clientapi.services.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;


    @Test
    public void addContact() throws Exception {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setType("email");
        contactDTO.setValue("email@mail.com");

        when(contactService.addContact(eq(1L), any(ContactDTO.class))).thenReturn(contactDTO);

        mockMvc.perform(post("/api/clients/1/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(contactDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("email"))
                .andExpect(jsonPath("$.value").value("email@mail.com"));
    }

    @Test
    public void getContactsByClientId() throws Exception {
        ContactDTO contact1 = new ContactDTO();
        contact1.setId(1L);
        contact1.setType("email");
        contact1.setValue("email@mail.com");

        ContactDTO contact2 = new ContactDTO();
        contact2.setId(2L);
        contact2.setType("phone");
        contact2.setValue("123456789");

        when(contactService.getContactsByClientId(1L)).thenReturn(Arrays.asList(contact1, contact2));

        mockMvc.perform(get("/api/clients/1/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("email"))
                .andExpect(jsonPath("$[1].type").value("phone"));
    }
}