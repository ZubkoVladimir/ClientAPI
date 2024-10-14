package org.example.clientapi.Repository;

import org.example.clientapi.Entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepo extends CrudRepository<Client, Long> {

}
