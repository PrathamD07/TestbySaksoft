package com.springboot.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.web.Entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
