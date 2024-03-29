package medicalConsultancy.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import mconsultancy.db.ifaces.UserInterface;
import db.pojos.Pathology;
import db.pojos.Patient;
import db.pojos.Prescription;
import db.pojos.Video_consultation;
import db.pojos.users.User;

public class JPAUserManager implements UserInterface {

	private EntityManager em;

	@Override
	public void connect() {
		em = Persistence.createEntityManagerFactory("user-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();

	}

	@Override
	public void disconnect() {
		em.close();

	}

	@Override
	public void addUser(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();

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
	public List<User> allEmergencyUsers() {
		Query q = em.createNativeQuery("SELECT * FROM users WHERE role_name=? and specialization=?", User.class);
		q.setParameter(1, "d");
		q.setParameter(2, "emergency");
		return (List<User>) q.getSingleResult();

	}

	@Override
	public void deleteUser(User user) {

		em.getTransaction().begin();
		em.remove(user);
		em.getTransaction().commit();

	}

	@Override
	public boolean checkEmail(String email) {

		Query q = em.createNativeQuery("SELECT * FROM users WHERE email=?", User.class);
		q.setParameter(1, email);
		try {
			Object user = q.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
		return true;
	}

	@Override
	public void diagnose(Patient p, Pathology path) {

		em.getTransaction().begin();
		p.addPathology(path);
		path.addPatient(p);
		em.getTransaction().commit();
	}

	@Override
	public Patient getPatient(int id_patient) {

		Object patient;
		Query q = em.createNativeQuery("SELECT * FROM users WHERE id=?", Patient.class);
		q.setParameter(1, id_patient);
		try {
			patient = q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

		return (Patient) patient;
	}

	@Override
	public Pathology getPathology(int id_pathology) {
		Object path;
		Query q = em.createNativeQuery("SELECT * FROM pathology WHERE id_pathology=?", Pathology.class);
		q.setParameter(1, id_pathology);
		try {
			path = q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

		return (Pathology) path;

	}

	@Override
	public Boolean checkPathologyTable() {
		List<Pathology> path = new ArrayList<Pathology>();
		Query q = em.createNativeQuery("SELECT * FROM pathology", Pathology.class);

		path = (List<Pathology>) q.getResultList();
		if (path.isEmpty())
			return false;
		else
			return true;

	}

	@Override
	public void addPathology(Pathology p) {
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();

	}

}
