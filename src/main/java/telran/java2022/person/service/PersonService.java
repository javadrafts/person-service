package telran.java2022.person.service;

import telran.java2022.person.dto.AddressDto;
import telran.java2022.person.dto.CityPopulationDto;
import telran.java2022.person.dto.PersonDto;

public interface PersonService {
	boolean addPerson(PersonDto personDto);

	PersonDto findPerson(int id);

	PersonDto removePerson(int id);

	PersonDto updatePersonName(int id, String name);

	PersonDto updatePersonAddress(int id, AddressDto addressDto);

	Iterable<PersonDto> findPersonsByName(String name);

	Iterable<PersonDto> findPersonsByCity(String city);

	Iterable<PersonDto> findPersonsByAges(int minAge, int maxAge);

	Iterable<CityPopulationDto> getCityPopulations();
}
