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


    public List<Person> all() {
        List<Person> personList = personRepository.findAll();
        return personList;

    }

    public Person get(String id) throws PersonNotFoundException{
        return personRepository.findById(id)
                .orElseThrow(()-> new PersonNotFoundException(id));
    }

    public Person createPerson(String name, int age, String city) {
        Person person = new PersonImpl(UUID.randomUUID().toString(), name, age, city, List.of());
        return personRepository.save(person);
        //return person;
    }

    public Person updatePerson(String id, String name, int age, String city) throws PersonNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(()-> new PersonNotFoundException(id));
        person.setName(name);
        person.setAge(age);
        person.setCity(city);
        return person;
    }

    public void deletePerson(String id) {
        personRepository.delete(id);
    }
}
