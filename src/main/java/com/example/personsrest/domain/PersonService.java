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
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class PersonService {

    PersonRepository personRepository = new PersonRepositoryImpl();
    GroupRemote groupRemote = new GroupRemoteImpl();


    public List<Person> all() {
        return personRepository.findAll();
    }

    public Person get(String id) {
        return personRepository.findById(id)
                .orElse(new PersonImpl());
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

    public Person removeGroup(String personId, String groupName)throws PersonNotFoundException{
        return personRepository.findById(personId).map(person -> {
            if (isUUID(groupName)) person.removeGroup(groupName);
            else person.getGroups().removeIf(group -> groupRemote.getNameById(group).equalsIgnoreCase(groupName));
            return personRepository.save(person);
        }).orElse(null);
    }

    private boolean isUUID(String groupName) {
        String uuidRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
        return Pattern.compile(uuidRegex).matcher(groupName).matches();
    }


    public Page<Person> findByNameOrCityContaining(String search, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);

        return personRepository.findAllByNameContainingOrCityContaining(search, search, page);
    }
}



