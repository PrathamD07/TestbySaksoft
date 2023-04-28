package com.springboot.web.Controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.web.Entity.Person;
import com.springboot.web.Service.PersonService;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
	 @Autowired
	    private PersonService personService;

	    @GetMapping
	    public List<Person> getAllPersons() {
	        return personService.getAllPersons();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
	        Optional<Person> person = personService.getPersonById(id);
	        return person.map(ResponseEntity::ok)
	                     .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping
	    public ResponseEntity<Person> savePerson(@RequestBody Person person) {
	        Person savedPerson = personService.savePerson(person);
	        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
	                                                  .path("/{id}")
	                                                  .buildAndExpand(savedPerson.getId())
	                                                  .toUri();
	        return ResponseEntity.created(location).body(savedPerson);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
	        Optional<Person> existingPerson = personService.getPersonById(id);
	        if (existingPerson.isPresent()) {
	            Person updatedPerson = personService.savePerson(person);
	            return ResponseEntity.ok(updatedPerson);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deletePersonById(@PathVariable Long id) {
	        personService.deletePersonById(id);
	        return ResponseEntity.noContent().build();
	    }
}
