package com.gym.management.gymmanager.controller;

import com.gym.management.gymmanager.cache.PersonCache;
import com.gym.management.gymmanager.model.Person;
import com.gym.management.gymmanager.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonCache personCache;

    // ✅ Добавление нового человека
    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        Person savedPerson = personService.savePerson(person);
        return ResponseEntity.ok(savedPerson);
    }

    // ✅ Получение информации о человеке по ID с кэшированием
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = personService.getPersonById(id);
        if (person == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(person);
    }

    // ✅ Получение списка всех людей
    @GetMapping
    public ResponseEntity<List<Person>> getAllPeople() {
        return ResponseEntity.ok(personService.getAllPeople());
    }

    // ✅ Удаление человека по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        boolean deleted = personService.deletePerson(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ Обновление данных человека
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        Person updatedPerson = personService.updatePerson(id, personDetails);
        if (updatedPerson != null) {
            return ResponseEntity.ok(updatedPerson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔗 Привязка тренера к существующему человеку
    @PutMapping("/{personId}/trainer/{trainerId}")
    public ResponseEntity<Person> assignTrainer(@PathVariable Long personId, @PathVariable Long trainerId) {
        Person updated = personService.assignTrainerToPerson(personId, trainerId);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // 🔗 Привязка зала к существующему человеку
    @PutMapping("/{personId}/gym/{gymId}")
    public ResponseEntity<Person> assignGym(@PathVariable Long personId, @PathVariable Long gymId) {
        Person updated = personService.assignGymToPerson(personId, gymId);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // 🔍 Получение людей по типу зала через JPQL запрос
    @GetMapping("/by-gym-type")
    public ResponseEntity<List<Person>> getPeopleByGymType(@RequestParam String gymType) {
        List<Person> people = personService.getPersonsByGymType(gymType);
        return ResponseEntity.ok(people);
    }

    // 🔍 Получение людей по типу зала через native SQL запрос
    @GetMapping("/by-gym-type-native")
    public ResponseEntity<List<Person>> getPeopleByGymTypeNative(@RequestParam String gymType) {
        List<Person> people = personService.getPersonsByGymTypeNative(gymType);
        return ResponseEntity.ok(people);
    }

    // 🧠 Получение текущего содержимого кэша
    @GetMapping("/cache")
    public ResponseEntity<Map<Long, Person>> getPersonCache() {
        return ResponseEntity.ok(personCache.getAll());
    }

    // 🔄 Очистка кэша вручную (по желанию)
    @DeleteMapping("/cache/clear")
    public ResponseEntity<String> clearCache() {
        personCache.clear();
        return ResponseEntity.ok("Person cache cleared");
    }
}
