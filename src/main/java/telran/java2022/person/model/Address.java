package telran.java2022.person.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
// @Embeddable
public class Address {
	String city;
	String street;
	int building;
}
