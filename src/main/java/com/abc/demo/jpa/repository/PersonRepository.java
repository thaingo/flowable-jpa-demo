package com.abc.demo.jpa.repository;

import com.abc.demo.jpa.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
  Person findByUserName(String userName);
}

