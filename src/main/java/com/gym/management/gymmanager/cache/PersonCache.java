package com.gym.management.gymmanager.cache;

import com.gym.management.gymmanager.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PersonCache {

    private static final Logger logger = LoggerFactory.getLogger(PersonCache.class);

    private final Map<Long, Person> personByIdCache = new HashMap<>();
    private final Map<String, List<Person>> peopleByGymTypeCache = new HashMap<>();

    public void putToIdCache(Long id, Person person) {
        personByIdCache.put(id, person);
        logger.info("Put person into ID cache: {}", person);
    }

    public Person getPersonByIdCache(Long id) {
        logger.info("Getting person from ID cache with ID: {}", id);
        return personByIdCache.get(id);
    }

    public List<Person> getPersonsByGymTypeCache(String gymType) {
        logger.info("Getting people from GymType cache with type: {}", gymType);
        return peopleByGymTypeCache.getOrDefault(gymType, null);
    }

    public void putToGymTypeCache(String gymType, List<Person> people) {
        peopleByGymTypeCache.put(gymType, people);
        logger.info("Put people into GymType cache: {} -> {}", gymType, people);
    }

    public void clearIdCache() {
        logger.info("Clearing ID cache");
        personByIdCache.clear();
    }

    public void clearGymTypeCache() {
        logger.info("Clearing GymType cache");
        peopleByGymTypeCache.clear();
    }

    public void clear() {
        logger.info("Clearing all caches");
        personByIdCache.clear();
        peopleByGymTypeCache.clear();
    }

    public void removeFromIdCache(Long id) {
        logger.info("Removing person with ID {} from ID cache", id);
        personByIdCache.remove(id);
    }

    public void removeFromGymTypeCache(String gymType) {
        logger.info("Removing people from GymType cache with type: {}", gymType);
        peopleByGymTypeCache.remove(gymType);
    }

    // Метод для вывода всей карты ID-кэша в консоль
    public void printIdCache() {
        logger.info("=== Current ID Cache Content ===");
        if (personByIdCache.isEmpty()) {
            logger.info("ID Cache is empty.");
        } else {
            personByIdCache.forEach((id, person) -> logger.info("ID: {}, Person: {}", id, person));
        }
    }

    // Метод для вывода кэша по типу зала
    public void printGymTypeCache() {
        logger.info("=== Current Gym Type Cache Content ===");
        if (peopleByGymTypeCache.isEmpty()) {
            logger.info("GymType Cache is empty.");
        } else {
            peopleByGymTypeCache.forEach((type, people) -> logger.info("Type: {}, People: {}", type, people));
        }
    }

    // Добавляем метод для получения всех людей по ID из кэша
    public Map<Long, Person> getAll() {
        logger.info("Getting all people from ID cache.");
        return new HashMap<>(personByIdCache); // Возвращаем копию карты, чтобы избежать изменений извне
    }
}
