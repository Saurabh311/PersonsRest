package com.example.personsrest.domain;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {

    PersonService personService;

    @GetMapping
    public List<Person> all(){
        return personService.all()
                .collect(Collectors.toList());
    }

    /*private static Person toDTO(Person person){
        Person person1 = new PersonImpl(person.getId(), person.getName(), person.getAge(), person.getCity(), person.getGroups());
        return person1;
    }*/

    @GetMapping("/{id}")
    public Person get(@PathVariable String id){
        personService.get(id);
    }

    @PostMapping
    public Person createPerson (@RequestBody CreatePerson createPerson){
        return personService.createPerson(createPerson.getName(), createPerson.getAge(), createPerson.getCity());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable String id, @RequestBody UpdatePerson updatePerson){
        return personService.updatePerson(id, updatePerson.getName(), updatePerson.getAge(), updatePerson.getCity());

    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable String id){
        personService.deletePerson(id);
    }


}
