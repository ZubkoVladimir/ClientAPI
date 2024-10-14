package org.example.clientapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.clientapi.dto.ContactDTO;
import org.example.clientapi.services.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с endpoint-ами, связанными с Contact
 */
@RestController
@RequestMapping("/api/clients/{clientId}/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    /**
     * Добавляет новый контакт к найденному клиенту из БД
     *
     * @param clientId Идентификационный номер клиента
     * @param contact  Контактная информация в формате DTO
     * @return ResponseEntity с объектом ContactDTO, представляющим созданный контакт, и статусом 200 (OK).
     */
    @PostMapping()
    public ResponseEntity<ContactDTO> addContact(@PathVariable Long clientId, @RequestBody ContactDTO contact) {
        return ResponseEntity.ok(contactService.addContact(clientId, contact));
    }

    /**
     * Получает все контакты у выбранного клиента
     *
     * @param clientId ID клиента, у которого требуется найти список контактов
     * @return ResponseEntity со списком объектов ContactDTO у клиента
     */
    @GetMapping()
    public ResponseEntity<List<ContactDTO>> getContactsByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(contactService.getContactsByClientId(clientId));
    }

    /**
     * Получает все контакты определенного типа у выбранного клиента
     *
     * @param clientId ID клиента, у которого требуется найти список контактов
     * @param type     тип контакта (телефонный номер или электронная почта)
     * @return ResponseEntity со списком объектов ContactDTO у клиента
     */
    @GetMapping("/{type}")
    public ResponseEntity<List<ContactDTO>> getContactsByClientIdAndType(@PathVariable Long clientId, @PathVariable String type) {
        return ResponseEntity.ok(contactService.getContactsByClientIdAndType(clientId, type));
    }
}
