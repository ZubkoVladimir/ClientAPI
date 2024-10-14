package org.example.clientapi.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContactDTO {
    private Long id;
    private String type;
    private String value;
    private Long clientId;
}
