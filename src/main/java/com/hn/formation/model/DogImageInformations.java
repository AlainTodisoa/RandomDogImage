package com.hn.formation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
public class DogImageInformations {
    private String message;
    private String status;

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public DogImageInformations(final String message, final String status) {
        this.message = message;
        this.status = status;
    }

    public DogImageInformations() {
    }
}
