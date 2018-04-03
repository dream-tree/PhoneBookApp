package application;

	/**
	 * @author dream-tree
	 * Class compose person ... !!!
	 * ver. 1.0
	 */

public class Person implements Comparable<Person> {

	String firstName; 
	String lastName;
	int number;
	
	/**
	 * @param firstName first name of requested person
	 * @param lastName last name of requested person
	 * @param nick nickname of requested person
	 * @param number phone number of requested person
	 */
	public Person(String firstName, String lastName, int number) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.number = number;
	}
	
	public Person(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//	if no entry found in PhoneBase, then output has to be different
		if(this.lastName.equals("")) {
			return String.format("%s", firstName);
		} else {
			// %,d: Requires the output to include the locale-specific group separators (Poland: 1 000 000).
			return String.format("Phone number:  %,d  of %s %s", number, firstName, lastName);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + number;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (number != other.number)
			return false;
		return true;
	}
	
	// Tree<Person> enforces implementing Comparable interface  
	@Override
	public int compareTo(Person p) {	
		 return this.number-p.number;  			
	}	
}
