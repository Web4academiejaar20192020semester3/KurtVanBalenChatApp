package domain;

import db.MessageRepoMemory;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {

	private String userId;
	private String password;
	private String salt;
	private String firstName;
	private String lastName;
	private Role role;
	private List<Person> friendList;
	private String status = "Offline";
	private MessageRepoMemory messages;
	private String age;
	private String gender;

	public Person(String userId, String password, String firstName,
			String lastName,Role role) {
		setUserId(userId);
		setHashedPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
		setRole(role);
		friendList = new ArrayList<Person>();
		messages = new MessageRepoMemory();
	}

	public Person(String userId, String password, String salt,
			String firstName, String lastName,Role role) {
		setUserId(userId);
		setPassword(password);
		setSalt(salt);
		setFirstName(firstName);
		setLastName(lastName);
		setRole(role);
	}

	public Person() {
		friendList = new ArrayList<Person>();
		messages = new MessageRepoMemory();
	}

	public String getName()
	{
		return this.getFirstName() + " " + this.getLastName();
	}

	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public void addFriend(Person friend) throws DomainException
	{
		if(this.equals(friend))
		{
			throw new DomainException("You cannot add yourself!");
		}
		else if(this.friendList.contains(friend))
		{
			throw new DomainException("You are already friends with this person!");
		}
		else
		{
			this.friendList.add(friend);
			friend.friendList.add(this);
		}
	}
	public List<Person> getFriends()
	{
		return friendList;
	}
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role=role;
	}
	

	public void setUserId(String userId) {
		if (userId.isEmpty()) {
			throw new IllegalArgumentException("No id given");
		}
		String USERID_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern p = Pattern.compile(USERID_PATTERN);
		Matcher m = p.matcher(userId);
		if (!m.matches()) {
			throw new IllegalArgumentException("Email not valid");
		}
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public boolean isCorrectPassword(String password) {
		if (password.isEmpty()) {
			throw new IllegalArgumentException("No password given");
		}
		return getPassword().equals(hashPassword(password, getSalt()));
	}

	public void setPassword(String password) {
		if (password.isEmpty()) {
			throw new IllegalArgumentException("No password given");
		}
		this.password = password;
	}

	public void setHashedPassword(String password) {
		if (password.isEmpty()) {
			throw new IllegalArgumentException("No password given");
		}
		this.password = hashPassword(password);
	}

	private String hashPassword(String password) {
		SecureRandom random = new SecureRandom();
		byte[] seed = random.generateSeed(20);

		String salt = new BigInteger(1, seed).toString(16);
		this.setSalt(salt);

		return hashPassword(password, salt);
	}

	private String hashPassword(String password, String seed) {
		String hashedPassword = null;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(salt.getBytes("UTF-8"));
			crypt.update(password.getBytes("UTF-8"));
			hashedPassword = new BigInteger(1, crypt.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			throw new DomainException(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			throw new DomainException(e.getMessage(), e);
		}
		return hashedPassword;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSalt() {
		return salt;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName.isEmpty()) {
			throw new IllegalArgumentException("No firstname given");
		}
		this.firstName = firstName;// firstName;

	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (lastName.isEmpty()) {
			throw new IllegalArgumentException("No last name given");
		}
		this.lastName = lastName;
	}

	public String getNewMessagesFriendJSON(Person friend1, Person me) {
		return messages.getNewMessagesPartnerAsJSON(friend1,me);
	}

	public String getOldMessagesFriendAsJSON(Person friend)
	{
		return this.messages.getMessagesFriendAsJSON(friend);
	}

	public void addOldMessage(Message message) {
		messages.addOldMessage(message);
	}

	public void addNewMessage(Message message) {
		messages.addNewMessage(message);
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAge(){ return age; }

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}
}
