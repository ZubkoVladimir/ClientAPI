package org.example.clientapi.Repository;

import org.example.clientapi.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepo extends JpaRepository<Contact, Long> {
    List<Contact> findByClientId(Long clientId);

    List<Contact> findByClientIdAndType(Long clientId, String type);
}
