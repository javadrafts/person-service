package telran.java2022.person.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4935085577496828119L;

	public PersonNotFoundException(int id) {
		super("Person " + id + " not found.");
	}
}
