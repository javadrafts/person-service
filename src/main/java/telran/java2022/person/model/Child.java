package telran.java2022.person.model;

import java.time.LocalDate;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.java2022.person.dto.AddressDto;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Child extends Person {
	private static final long serialVersionUID = 5301092897206101592L;

	String kindergarten;

	public Child(Integer id, String name, LocalDate birthDate, AddressDto address, String kindergarten) {
		super(id, name, birthDate, address);
		this.kindergarten = kindergarten;
	}
}
