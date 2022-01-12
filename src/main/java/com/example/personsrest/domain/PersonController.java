package com.example.personsrest.domain;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persons/")
@AllArgsConstructor
public class PersonController {

    PersonService personService;

    @GetMapping
    public List<Person> all(){
        return personService.all();
    }

    private static Person toDTO(Person person){
        Person person1 = new PersonImpl(person.getId(), person.getName(), person.getAge(), person.getCity(), person.getGroups());
        return person1;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> get(@PathVariable String id){
        try {
            return ResponseEntity.ok(personService.get(id));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Person createPerson (@RequestBody CreatePerson createPerson){
        return personService.createPerson(createPerson.getName(), createPerson.getAge(), createPerson.getCity());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable String id, @RequestBody UpdatePerson updatePerson){
        try {
            return ResponseEntity.ok(personService
                    .updatePerson(id, updatePerson.getName(), updatePerson.getAge(), updatePerson.getCity()));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable String id){
        personService.deletePerson(id);
    }

    @GetMapping("/{id}/link/{remoteId}")
    public ResponseEntity<Person> link(@PathVariable String id, @PathVariable String remoteId){
        try {
            return ResponseEntity.ok(personService.link(id, remoteId));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
