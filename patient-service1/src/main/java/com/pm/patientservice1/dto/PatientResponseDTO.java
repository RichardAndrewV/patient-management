package com.pm.patientservice1.dto;

import java.io.Serializable;
import java.util.UUID;

public class PatientResponseDTO implements Serializable {
    //here the members are in string datatype in entity it had different datatype so while returning to frontend ie in JSON format in the body as per HTTP Request, while converting from Entity the typecasting becomes much more difficult so we are using DTO class where we have primitive Datatypes like String
    private UUID id;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
