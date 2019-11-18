package jumpstart.web.pages.examples.tables;

import java.util.List;

import javax.ejb.EJB;

import jumpstart.business.domain.person.Person;
import jumpstart.business.domain.person.iface.IPersonFinderServiceLocal;

import org.apache.tapestry5.annotations.Property;

public class AddAndRemoveColumns1 {
	static private final int MAX_RESULTS = 30;

	// Screen fields

	@Property
	private List<Person> persons;
	
	@Property
	private Person person;
	
	// Generally useful bits and pieces

	@EJB
	private IPersonFinderServiceLocal personFinderService;

	// The code
	
	void setupRender() {
		// Get all persons - ask business service to find them (from the database)
		persons = personFinderService.findPersons(MAX_RESULTS);
	}
}
