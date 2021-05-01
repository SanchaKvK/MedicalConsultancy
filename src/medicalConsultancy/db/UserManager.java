package medicalConsultancy.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.*;

import db.iface.UserInterface;
import db.pojos.users.Role;
import db.pojos.users.User;

public class UserManager implements UserInterface {

	private EntityManager em;

	@Override
	public void connect() {
		em = Persistence.createEntityManagerFactory("user-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		List<Role> existingRoles = this.getAllRoles();
		if (existingRoles.size() < 2) {

			this.addRole(new Role("patient"));
			this.addRole(new Role("doctor"));
		}

	}

	@Override
	public void disconnect() {
		em.close();

	}

	@Override
	public void addRole(Role role) {
		em.getTransaction().begin();
		em.persist(role);
		em.getTransaction().commit();

	}

	@Override
	public void addUser(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();

	}

	@Override
	public Role getRole(int id) {
		Query q = em.createNativeQuery("SELECT*FROM roles WHERE id=?", Role.class);
		q.setParameter(1, id);
		return (Role) q.getSingleResult();
	}

	@Override
	public User checkPassword(String email, String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] hash = md.digest();
			Query q = em.createNativeQuery("SELECT * FROM users WHERE email = ? AND password = ?", User.class);
			q.setParameter(1, email);
			q.setParameter(2, hash);
			return (User) q.getSingleResult();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoResultException n) {
			return null;
		}
		return null;
	}

	@Override
	public List<Role> getAllRoles() {
		Query q = em.createNativeQuery("SELECT*FROM roles", Role.class);

		return (List<Role>) q.getResultList();
	}

}
