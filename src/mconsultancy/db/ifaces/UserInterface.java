package mconsultancy.db.ifaces;

import java.util.List;

import db.pojos.Prescription;
import db.pojos.Video_consultation;
import db.pojos.users.User;

public interface UserInterface {

	//connects with the JPA "database"
	public void connect();

	//close the connection with the JPA "database"
	public void disconnect();


	//add a new user to the database
	public void addUser(User user);



	//return an user that has a specific email and password
	public User checkPassword(String email, String password);


	//return a list of users  with the role_name=d and the specialization=Medical emergencies
	public List<User> allEmergencyUsers();
	
	//updates a previous videoconsultation. Updates the duration, the prescription and the notes.
	
	public void addInfoVideo(Video_consultation vd, String notes, int duration, Prescription p);

	//add a prescription
	public void addPrescription(Prescription p);
	
	//delete an user
	public void deleteUser(User user);
	
	//checks if an email has already been created in the database. Returns true if it has already been created or false if not. 
	public boolean checkEmail(String email);
	
}
