package com.example.personsrest.domain;

import lombok.Value;

@Value
public class UpdatePerson {
    String id;
    String name;
    int age;
    String city;
}
