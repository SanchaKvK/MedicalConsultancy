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


	public List<User> allEmergencyUsers();
	
	public void addInfoVideo(int id_video, String notes, int duration, Prescription p);

	public void addPrescription(Prescription p);
	
	public void deleteUser(User user);

	
}
