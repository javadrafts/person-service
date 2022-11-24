package telran.java2022.person.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java2022.person.dao.PersonRepository;
import telran.java2022.person.dto.AddressDto;
import telran.java2022.person.dto.CityPopulationDto;
import telran.java2022.person.dto.PersonDto;
import telran.java2022.person.dto.exceptions.PersonNotFoundException;
import telran.java2022.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Override
	public boolean addPerson(PersonDto personDto) {
		Person person = modelMapper.map(personDto, Person.class);

		personRepository.save(person);

		return true;
	}

	@Override
	public PersonDto findPerson(int id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto removePerson(int id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

		personRepository.delete(person);

		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto updatePersonName(int id, String name) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

		person.setName(name);

		person = personRepository.save(person);

		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto updatePersonAddress(int id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

		person.setAddress(addressDto);

		person = personRepository.save(person);

		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public Iterable<PersonDto> findPersonsByName(String name) {
		return StreamSupport.stream(personRepository.findAllByName(name).spliterator(), false)
				.map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return StreamSupport.stream(personRepository.findAllByAddressCity(city).spliterator(), false)
				.map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	public Iterable<PersonDto> findPersonsByAges(int minAge, int maxAge) {
		LocalDate dateNow = LocalDate.now();

		return StreamSupport
				.stream(personRepository.findAllByBirthDateBetween(dateNow.minus(maxAge, ChronoUnit.YEARS),
						dateNow.minus(minAge, ChronoUnit.YEARS)).spliterator(), false)
				.map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	public Iterable<CityPopulationDto> getCityPopulations() {
		Map<String, Integer> cityPopulations = new HashMap<>();

		for (var person : personRepository.findAll()) {
			cityPopulations.computeIfPresent(person.getAddress().getCity(), (k, v) -> v + 1);
			cityPopulations.putIfAbsent(person.getAddress().getCity(), 1);

		}

		return cityPopulations.entrySet().stream().map(e -> new CityPopulationDto(e.getKey(), e.getValue())).toList();
	}
}
