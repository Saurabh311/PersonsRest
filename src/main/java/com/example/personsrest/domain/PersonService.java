package com.example.personsrest.domain;

import com.example.personsrest.remote.GroupRemote;
import com.example.personsrest.remote.GroupRemoteImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class PersonService {

    PersonRepository personRepository = new PersonRepositoryImpl();
    GroupRemote groupRemote = new GroupRemoteImpl();


    public List<Person> all() {
        List<Person> personList = personRepository.findAll();
        return personList;

    }

    public Person get(String id) throws PersonNotFoundException{
        return personRepository.findById(id)
                .orElseThrow(()-> new PersonNotFoundException(id));
    }

    public Person createPerson(String name, int age, String city) {
        Person person = new PersonImpl(UUID.randomUUID().toString(), name, age, city, new ArrayList<>());
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

    public Person link(String id, String remoteId) throws PersonNotFoundException{
        Person person = personRepository.findById(id)
                .orElseThrow(()-> new PersonNotFoundException(id));
        String groupName = groupRemote.getNameById(remoteId);
        System.out.println(groupName + " from Service");
        person.addGroup(groupName);
        return personRepository.save(person);
    }
}
