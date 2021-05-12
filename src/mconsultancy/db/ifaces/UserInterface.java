package mconsultancy.db.ifaces;

import java.util.List;


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



}
