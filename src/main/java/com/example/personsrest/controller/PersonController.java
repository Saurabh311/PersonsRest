package com.example.personsrest.controller;

import com.example.personsrest.exception.PersonNotFoundException;
import com.example.personsrest.service.PersonService;
import com.example.personsrest.domain.CreatePerson;
import com.example.personsrest.domain.Person;
import com.example.personsrest.domain.PersonDTO;
import com.example.personsrest.domain.UpdatePerson;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persons")
@AllArgsConstructor
public class PersonController {

    PersonService personService;

    @GetMapping
    public List<PersonDTO> getAllPersons(@RequestParam(required = false) String search) {
        if (search == null) {
            return personService.all().stream().map(this::toDTO).collect(Collectors.toList());
        } else {
            return personService.findByNameOrCityContaining(search, 0, 10)
                    .map(this::toDTO).stream().collect(Collectors.toList());
        }
    }

    @GetMapping("/{id}")
    public PersonDTO get(@PathVariable String id) {
        return toDTO(personService.get(id));

    }

    @PostMapping
    public PersonDTO createPerson(@RequestBody CreatePerson createPerson) {
        return toDTO(personService.createPerson(createPerson));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable String id, @RequestBody UpdatePerson updatePerson) {
        try {
            return ResponseEntity.ok(toDTO(personService
                    .updatePerson(id, updatePerson.getName(), updatePerson.getAge(), updatePerson.getCity())));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable String id) {
        personService.deletePerson(id);
    }

    @PutMapping("/{id}/addGroup/{groupName}")
    public ResponseEntity<PersonDTO> addGroup(@PathVariable String id, @PathVariable String groupName) {
        try {
            return ResponseEntity.ok(toDTO(personService.addGroup(id, groupName)));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/removeGroup/{groupId}")
    public ResponseEntity<PersonDTO> removeGroup(@PathVariable String id, @PathVariable String groupId) {
        try {
            return ResponseEntity.ok(toDTO(personService.removeGroup(id, groupId)));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private PersonDTO toDTO(Person person) {
        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getCity(),
                person.getAge(),
                person.getGroups().stream()
                        .map(groupId -> personService.getGroupName(groupId))
                        .collect(Collectors.toList())
        );
    }
}
