package org.example.clientapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.clientapi.dto.ClientDTO;
import org.example.clientapi.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с endpoint-ами связанными с Client
 */
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    /**
     * Добавляет нового клиента в БД
     *
     * @param dto объект ClientDTO, содержащий информацию о новом клиенте
     * @return ResponseEntity с объектом ClientDTO, представляющим созданного клиента, и статусом 200 (OK).
     */
    @PostMapping()
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO dto) {
        ClientDTO createdDTO = clientService.addClient(dto);
        return ResponseEntity.ok(createdDTO);
    }

    /**
     * Получает список всех клиентов из БД
     *
     * @return ResponseEntity со списком объектов ClientDTO, представляющим всех клиентов.
     */
    @GetMapping()
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    /**
     * Получает клиента из БД по его ID
     *
     * @param id Идентификационный номер клиента в БД
     * @return ResponseEntity с объектом ClientDTO, представляющим найденного клиента, и статусом 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

}
