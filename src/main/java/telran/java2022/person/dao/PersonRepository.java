package telran.java2022.person.dao;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import telran.java2022.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
	Iterable<Person> findAllByName(String name);

	Iterable<Person> findAllByAddressCity(String city);

	Iterable<Person> findAllByBirthDateBetween(LocalDate from, LocalDate to);
}
