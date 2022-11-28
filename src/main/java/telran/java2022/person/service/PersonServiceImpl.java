package telran.java2022.person.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.java2022.helpers.DtoMapper;
import telran.java2022.person.dao.PersonRepository;
import telran.java2022.person.dto.AddressDto;
import telran.java2022.person.dto.ChildDto;
import telran.java2022.person.dto.CityPopulationDto;
import telran.java2022.person.dto.EmployeeDto;
import telran.java2022.person.dto.PersonDto;
import telran.java2022.person.dto.exceptions.PersonNotFoundException;
import telran.java2022.person.model.Child;
import telran.java2022.person.model.Employee;
import telran.java2022.person.model.Person;

@Service
public class PersonServiceImpl implements PersonService {
	final PersonRepository personRepository;
	final DtoMapper dtoMapper;

	public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper) {
		this.personRepository = personRepository;

		dtoMapper = DtoMapper.of(modelMapper)
				.withEntry(Person.class, PersonDto.class)
				.withEntry(Employee.class, EmployeeDto.class)
				.withEntry(Child.class, ChildDto.class)
				.build();
	}

	@Override
	@Transactional
	public boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}

		Person person = dtoMapper.map(personDto, Person.class);

		personRepository.save(person);

		return true;
	}

	@Override
	public PersonDto findPerson(int id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

		return dtoMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto removePerson(int id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

		personRepository.delete(person);

		return dtoMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto updatePersonName(int id, String name) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

		person.setName(name);

		return dtoMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto updatePersonAddress(int id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

		person.setAddress(addressDto);

		return dtoMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByName(String name) {
		return personRepository.findAllByName(name)
				.map(p -> dtoMapper.map(p, PersonDto.class))
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return personRepository.findAllByAddressCity(city)
				.map(p -> dtoMapper.map(p, PersonDto.class))
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByAges(int minAge, int maxAge) {
		LocalDate dateNow = LocalDate.now();
		LocalDate dateFrom = dateNow.minus(maxAge, ChronoUnit.YEARS);
		LocalDate dateTo = dateNow.minus(minAge, ChronoUnit.YEARS);

		return personRepository.findAllByBirthDateBetween(dateFrom, dateTo)
				.map(p -> dtoMapper.map(p, PersonDto.class))
				.toList();
	}

	@Override
	public Iterable<CityPopulationDto> getCityPopulations() {
		return personRepository.getCitiesPopulation();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findEmployeesBySalary(int min, int max) {
		return personRepository.findEmployeesBySalaryBetween(min, max)
				.map(p -> (PersonDto) dtoMapper.map(p, EmployeeDto.class))
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> getChildren() {
		return personRepository.findAllChildren()
				.map(p -> (PersonDto) dtoMapper.map(p, ChildDto.class))
				.toList();
	}
}
