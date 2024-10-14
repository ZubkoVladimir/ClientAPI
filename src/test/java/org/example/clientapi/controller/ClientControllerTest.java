package org.example.clientapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.clientapi.dto.ClientDTO;
import org.example.clientapi.services.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    public void addClient() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("John Doe");

        when(clientService.addClient(any(ClientDTO.class))).thenReturn(clientDTO);

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void getAllClients() throws Exception {
        ClientDTO client1 = new ClientDTO();
        client1.setId(1L);
        client1.setName("John Doe");

        ClientDTO client2 = new ClientDTO();
        client2.setId(2L);
        client2.setName("Jane Doe");

        when(clientService.getAllClients()).thenReturn(Arrays.asList(client1, client2));

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }
}