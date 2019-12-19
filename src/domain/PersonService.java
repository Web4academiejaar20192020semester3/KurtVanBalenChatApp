package domain;

import java.util.Iterator;
import java.util.List;

import db.PersonRepository;
import db.PersonRepositoryStub;

public class PersonService {
	private PersonRepository personRepository = new PersonRepositoryStub();

	public PersonService(){
	}
	public String getFriendsJSON(String personId)
	{
		Person person = getPersonRepository().get(personId);

		StringBuffer stringBuffer = new StringBuffer();
		List<Person> friends = this.getPerson(personId).getFriends();
		Iterator iterator = friends.iterator();
		stringBuffer.append("{ \"Person\":[");
		while (iterator.hasNext()) {
			Person friend = (Person) iterator.next();

			stringBuffer.append("{");
			stringBuffer.append("\"name\": \"" + friend.getName() + "\",");
			stringBuffer.append("\"status\": \"" + friend.getStatus() + "\"" );
			stringBuffer.append("}");
			if (iterator.hasNext()){
				stringBuffer.append(",");
			}
		}
		stringBuffer.append("]");
		stringBuffer.append("}");
		return stringBuffer.toString();
	}
	public Person getPerson(String personId)  {
		return getPersonRepository().get(personId);
	}

	public List<Person> getPersons() {
		return getPersonRepository().getAll();
	}

	public void addPerson(Person person) {
		getPersonRepository().add(person);
	}

	public void updatePersons(Person person) {
		getPersonRepository().update(person);
	}

	public void deletePerson(String id) {
		getPersonRepository().delete(id);
	}
	
	public Person getAuthenticatedUser(String email, String password) {
		return getPersonRepository().getAuthenticatedUser(email, password);
	}

	private PersonRepository getPersonRepository() {
		return personRepository;
	}

	public String getStatus(String userId) {
		return getPersonRepository().get(userId).getStatus();
	}

	public Person getPersonFullName(String user) {
		return getPersonRepository().getPersonFullName(user);
	}
}
