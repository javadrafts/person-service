package telran.java2022.person.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}

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
	@Transactional
	public PersonDto removePerson(int id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

		personRepository.delete(person);

		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto updatePersonName(int id, String name) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

		person.setName(name);

		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto updatePersonAddress(int id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

		person.setAddress(addressDto);

		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByName(String name) {
		return personRepository.findAllByName(name).map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return personRepository.findAllByAddressCity(city).map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByAges(int minAge, int maxAge) {
		LocalDate dateNow = LocalDate.now();

		return personRepository.findAllByBirthDateBetween(dateNow.minus(maxAge, ChronoUnit.YEARS),
				dateNow.minus(minAge, ChronoUnit.YEARS)).map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	public Iterable<CityPopulationDto> getCityPopulations() {
		return personRepository.getCitiesPopulation();
	}
}
