package medicalConsultancy.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.*;

import mconsultancy.db.ifaces.UserInterface;
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
	public List<User> allEmergencyUsers(){
		Query q = em.createNativeQuery("SELECT * FROM users WHERE role_name=? and specialization=?", User.class);
		q.setParameter(1, "d");
		q.setParameter(2, "emergency");
		return (List<User>) q.getSingleResult();

		
		
	}

	@Override
	public void addInfoVideo(int id_video, String notes, int duration, Prescription p) {
		
		Query q=em.createNativeQuery("SELECT * FROM videoconsultation WHERE id_video=?",Video_consultation.class);
		q.setParameter(1, id_video);
		Video_consultation vd= (Video_consultation) q.getSingleResult();
		
		em.getTransaction().begin();
		vd.setDuration(duration);
		vd.setNotes(notes);
		vd.addPrescription(p);
		p.setVd(vd);
		
		em.getTransaction().commit();
		
	}

	@Override
	public void addPrescription(Prescription p) {
		
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
		
	}

	@Override
	public void deleteUser(User user) {
		
		em.getTransaction().begin();
		em.remove(user);
		em.getTransaction().commit();
		
	}

	

	

}
