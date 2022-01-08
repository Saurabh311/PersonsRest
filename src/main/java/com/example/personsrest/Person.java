package com.example.personsrest;

import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@Value
public class Person {
    String id;
    String name;
    int age;
    String city;
    List<String> group;
}
