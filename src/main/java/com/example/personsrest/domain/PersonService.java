package com.example.personsrest.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class PersonService {

    PersonRepository personRepository = new PersonRepositoryImpl();


    public Stream<Person> all() {
        //List<Person> allPerson = personRepository.findAll();
        return Stream.of(new PersonImpl(UUID.randomUUID().toString(), "Saurabh Chauhan", 38, "Delhi", List.of()),
                new PersonImpl(UUID.randomUUID().toString(), "Mayank", 31, "New Delhi", List.of()),
                new PersonImpl(UUID.randomUUID().toString(), "Robin", 28, "Malmö", List.of()){
        });
    }

    public Person get(String id) {
        return null;
    }

    public Person createPerson(String name, int age, String city) {
        Person person = new PersonImpl(UUID.randomUUID().toString(), name, age, city, List.of());
        return person;
        //return personRepository.save(person);
    }



    public void deletePerson(String id) {
        personRepository.delete(id);
    }
}
