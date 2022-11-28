package telran.java2022.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java2022.person.dto.CityPopulationDto;
import telran.java2022.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
	Stream<Person> findAllByName(String name);

	Stream<Person> findAllByAddressCity(String city);

	Stream<Person> findAllByBirthDateBetween(LocalDate from, LocalDate to);

	@Query("select e from Employee e where e.salary between ?1 and ?2")
	Stream<Person> findEmployeesBySalaryBetween(Integer min, Integer max);

	@Query("select c from Child c")
	Stream<Person> findAllChildren();

	@Query("select new telran.java2022.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
	List<CityPopulationDto> getCitiesPopulation();
}
