package com.gym.management.gymmanager.repository;

import com.gym.management.gymmanager.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    // Здесь можно добавить дополнительные запросы, если необходимо
}
