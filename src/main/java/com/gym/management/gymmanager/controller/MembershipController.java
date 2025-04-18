package com.gym.management.gymmanager.controller;

import com.gym.management.gymmanager.model.Membership;
import com.gym.management.gymmanager.model.Person;
import com.gym.management.gymmanager.service.MembershipService;
import com.gym.management.gymmanager.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {
    private final MembershipService membershipService;
    private final PersonService personService;

    @Autowired
    public MembershipController(MembershipService membershipService, PersonService personService) {
        this.membershipService = membershipService;
        this.personService = personService;
    }

    @PostMapping("/person/{personId}")
    @Transactional
    public ResponseEntity<Membership> assignMembershipToPerson(@PathVariable Long personId,
                                                      @RequestBody Membership membership) {
        Person person = personService.getPersonById(personId);
        if (person == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Человек с ID " + personId + " не найден.");
        }

        membership.setPerson(person);
        Membership savedMembership = membershipService.saveMembership(membership);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMembership);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Membership> getMembershipById(@PathVariable Long id) {
        Membership membership = membershipService.getMembershipById(id);
        return membership != null ? ResponseEntity.ok(membership)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Абонемент не найден");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMembership(@PathVariable Long id) {
        boolean deleted = membershipService.deleteMembership(id);
        return deleted ? ResponseEntity.ok("Абонемент удалён")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Абонемент не найден");
    }
}
