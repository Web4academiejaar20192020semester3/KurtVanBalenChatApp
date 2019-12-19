package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Message;
import domain.Person;
import domain.Role;

public class PersonRepositoryStub implements PersonRepository {
	private Map<String, Person> persons = new HashMap<String, Person>();
	
	public PersonRepositoryStub () {
		Person administrator = new Person("bib@ucll.be", "t", "Bib", "Liothekaris", Role.BIB);
		add(administrator);
		Person an = new Person("an@ucll.be", "t", "An", "Cornelissen", Role.LID);
		add(an);
		Person jan = new Person("jan@ucll.be", "t", "Jan", "Janssens", Role.LID);
		add(jan);
		jan.addFriend(an);
		/*jan.addOldMessage(new Message("Old test message", jan, an));
		jan.addOldMessage(new Message("Old test message 2", jan, an));
		an.addOldMessage(new Message("Old test message", an, jan));
		an.addOldMessage(new Message("Old test message 2", an, jan));
		jan.addNewMessage(new Message("New message", jan, an));*/
	}
	
	public Person get(String personId){
		if(personId == null){
			throw new IllegalArgumentException("No id given");
		}
		if(persons.containsKey(personId) == false)
		{
			throw new IllegalArgumentException("Id invalid");
		}
		return persons.get(personId);
	}
	
	public List<Person> getAll(){
		return new ArrayList<Person>(persons.values());	
	}

	public void add(Person person){
		if(person == null){
			throw new IllegalArgumentException("No person given");
		}
		if (persons.containsKey(person.getUserId())) {
			throw new IllegalArgumentException("User already exists");
		}
		persons.put(person.getUserId(), person);
		System.out.println(person.getPassword());
		System.out.println(person.isCorrectPassword("t"));
	}
	
	public void update(Person person){
		if(person == null){
			throw new IllegalArgumentException("No person given");
		}
		persons.put(person.getUserId(), person);
	}

	@Override
	public Person getPersonFullName(String fullName) {
		Person output = null;
		for (Person person : persons.values()) {
			if (person.getName().equals(fullName)){
				output = person;
			}
		}
		return output;
	}

	public void delete(String personId){
		if(personId == null){
			throw new IllegalArgumentException("No id given");
		}
		persons.remove(personId);
	}
	
	public Person getAuthenticatedUser(String email, String password) {
		Person person = get(email);
		
		if (person != null && person.isCorrectPassword(password)) {
			return person;
		}
		else {
			return null;
		}
	}
}
