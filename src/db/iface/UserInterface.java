package db.iface;

import java.util.List;

import db.pojos.users.Role;
import db.pojos.users.User;

public interface UserInterface {

	public void connect();

	public void disconnect();

	public void addRole(Role role);

	public void addUser(User user);

	public Role getRole(int id);

	public User checkPassword(String email, String password);

	public List<Role> getAllRoles();

}
