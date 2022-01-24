package com.example.personsrest.exception;

public class PersonNotFoundException extends Exception {
    public PersonNotFoundException(String id){
        super(id);
    }
}
