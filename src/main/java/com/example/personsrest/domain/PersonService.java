package com.example.personsrest.domain;

import com.example.personsrest.remote.GroupRemote;
import com.example.personsrest.remote.GroupRemoteImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class PersonService {

    PersonRepository personRepository = new PersonRepositoryImpl();
    GroupRemote groupRemote = new GroupRemoteImpl();


    public List<Person> all() {
        return personRepository.findAll();
    }

    public Person get(String id) throws PersonNotFoundException{
        return personRepository.findById(id)
                .orElseThrow(()-> new PersonNotFoundException(id));
    }

    public Person createPerson(CreatePerson createPerson) {
        return personRepository.save(new PersonImpl(
                createPerson.getName(),
                createPerson.getCity(),
                createPerson.getAge(),
                new ArrayList<>()));
    }

    public Person updatePerson(String id, String name, int age, String city) throws PersonNotFoundException {
        Person updatePerson = personRepository.findById(id)
                .orElseThrow(()-> new PersonNotFoundException(id));
        updatePerson.setName(name);
        updatePerson.setAge(age);
        updatePerson.setCity(city);
        return personRepository.save(updatePerson);
    }

    public void deletePerson(String id) {
        personRepository.delete(id);
    }

    public Person addGroup(String id, String groupName) throws PersonNotFoundException{
        Person person = personRepository.findById(id)
                .orElseThrow(()-> new PersonNotFoundException(id));
        String name = groupRemote.createGroup(groupName);
        System.out.println(name);
        System.out.println(groupName + " from Service");
        person.addGroup(name);
        return personRepository.save(person);
    }

    public String getGroupName(String groupId) {
        String groupName;
        try {
            groupName = groupRemote.getNameById(groupId);
        }catch (Exception e){
            e.toString();
            return "Invalid group Id";
        }
        return groupName;
    }

    public Person removeGroup(String personId, String groupId)throws PersonNotFoundException{
        Person person = personRepository.findById(personId)
                .orElseThrow(()-> new PersonNotFoundException(groupId));
        person.removeGroup(groupId);
        return personRepository.save(person);
    }

    public Page<Person> findByNameOrCityContaining(String search, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);

        return personRepository.findAllByNameContainingOrCityContaining(search, search, page);
    }
}



