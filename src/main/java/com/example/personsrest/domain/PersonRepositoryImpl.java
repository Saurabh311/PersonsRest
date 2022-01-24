package com.example.personsrest.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

public class PersonRepositoryImpl implements PersonRepository {

    Map<String, Person> persons = new HashMap<>();

    public PersonRepositoryImpl() {
        Person person = new PersonImpl(UUID.randomUUID().toString(), "Arne Anka", "Malmö", 19, new ArrayList<>());
        persons.put(person.getId(), person);
    }

    @Override
    public Optional<Person> findById(String id) {
        return Optional.ofNullable(persons.get(id));
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(persons.values());
    }

    @Override
    public Page<Person> findAllByNameContainingOrCityContaining(String name, String city, Pageable pageable) {
        return new PageImpl<>(
                persons.values().stream()
                        .filter(person -> person.getName().equalsIgnoreCase(name))
                        .collect(Collectors.toList()));
    }

    @Override
    public void deleteAll() {
        persons.clear();
    }

    @Override
    public Person save(Person person) {
        persons.put(person.getId(), person);
        return person;
    }

    @Override
    public void delete(String id) {
        persons.remove(id);
    }
}
