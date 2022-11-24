package telran.java2022.person.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDto {
	int id;
	String name;
	LocalDate birthDate;
	AddressDto address;
}
