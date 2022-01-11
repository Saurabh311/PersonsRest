package com.example.personsrest.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

public class PersonRepositoryImpl implements PersonRepository {

    Map<String, Person> persons = new HashMap<>();

    @Override
    public Optional<Person> findById(String id) {
        return Optional.ofNullable(persons.get(id));
    }

    @Override
    public List<Person> findAll() {
        return persons.values().stream().collect(Collectors.toList());
        /*return List.of(new PersonImpl(UUID.randomUUID().toString(), "Saurabh Chauhan", 38, "Delhi", List.of()),
                new PersonImpl(UUID.randomUUID().toString(), "Mayank", 31, "New Delhi", List.of()),
                new PersonImpl(UUID.randomUUID().toString(), "Robin", 28, "Malmö", List.of()){
                }); */
    }

    @Override
    public Page<Person> findAllByNameContainingOrCityContaining(String name, String city, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteAll() {
        persons.clear();
    }

    @Override
    public Person save(Person person) {
        return persons.put(person.getId(), person);
    }

    @Override
    public void delete(String id) {
        persons.remove(id);
    }
}
