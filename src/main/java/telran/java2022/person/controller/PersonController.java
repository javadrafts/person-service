package telran.java2022.person.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java2022.person.dto.AddressDto;
import telran.java2022.person.dto.CityPopulationDto;
import telran.java2022.person.dto.PersonDto;
import telran.java2022.person.service.PersonService;

@RestController
@RequestMapping("person")
@RequiredArgsConstructor
public class PersonController {
	final PersonService personService;

	@PostMapping
	public boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}

	@GetMapping("{id}")
	public PersonDto findPerson(@PathVariable int id) {
		return personService.findPerson(id);
	}

	@DeleteMapping("{id}")
	public PersonDto removePerson(@PathVariable int id) {
		return personService.removePerson(id);
	}

	@PutMapping("{id}/name/{name}")
	public PersonDto updatePersonName(@PathVariable int id, @PathVariable String name) {
		return personService.updatePersonName(id, name);
	}

	@PutMapping("{id}/address")
	public PersonDto updatePersonAddress(@PathVariable int id, @RequestBody AddressDto address) {
		return personService.updatePersonAddress(id, address);
	}

	@GetMapping("name/{name}")
	public Iterable<PersonDto> findPersonsByName(@PathVariable String name) {
		return personService.findPersonsByName(name);
	}

	@GetMapping("city/{city}")
	public Iterable<PersonDto> findPersonsByCity(@PathVariable String city) {
		return personService.findPersonsByCity(city);
	}

	@GetMapping("ages/{minAge}/{maxAge}")
	public Iterable<PersonDto> findPersonsByAges(@PathVariable int minAge, @PathVariable int maxAge) {
		return personService.findPersonsByAges(minAge, maxAge);
	}

	@GetMapping("population/city")
	public Iterable<CityPopulationDto> getCityPopulations() {
		return personService.getCityPopulations();
	}
}
