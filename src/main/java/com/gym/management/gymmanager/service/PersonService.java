package com.gym.management.gymmanager.service;

import com.gym.management.gymmanager.model.Person;
import com.gym.management.gymmanager.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    // Получить все записи
    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    // Получить одного человека по ID
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    // Сохранить новый или обновить существующий
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    // Удалить человека по ID
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
