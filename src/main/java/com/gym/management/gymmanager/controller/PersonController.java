package com.gym.management.gymmanager.controller;

import java.net.URI;
import com.gym.management.gymmanager.model.Person;
import com.gym.management.gymmanager.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired // ✅ Добавляем аннотацию, чтобы SonarCloud не ругался
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Optional<Person> person = personService.getPersonById(id);
        return person.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
        Person savedPerson = personService.savePerson(person);
        return ResponseEntity.created(URI.create("/api/persons/" + savedPerson.getId()))
                .body(savedPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @Valid @RequestBody Person person) {
        if (!personService.getPersonById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        person.setId(id);
        Person updatedPerson = personService.savePerson(person);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        if (!personService.getPersonById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Произошла ошибка: " + e.getMessage());
    }
}
