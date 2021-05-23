
package medicalConsultancy.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import db.pojos.Doctor;
import db.pojos.Pathology;
import db.pojos.Patient;
import db.pojos.Prescription;
import db.pojos.Rating;
import db.pojos.Video_consultation;
import mconsultancy.db.ifaces.DBinterface;

public class DBManager implements DBinterface {

	private Connection c;
	private static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

	@Override
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/med.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened");
			this.createTables();

		} catch (SQLException sqlE) {
			System.out.println("There was a database exception");

		} catch (Exception e) {
			System.out.println("There was a general exception");
			e.printStackTrace();
		}
	}

	private void createTables() {
		Statement stmt1;
		try {
			stmt1 = c.createStatement();

			String sql1 = "CREATE TABLE users " + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "role_name TEXT NOT NULL, " + "gender TEXT, " + "birth DATE, " + "DNI TEXT UNIQUE, "
					+ "phone_number TEXT UNIQUE, " + "postcode TEXT, " + "specialization TEXT , "
					+ "name TEXT NOT NULL, " + "hospital TEXT, " + "email TEXT NOT NULL, " + "password BLOB NOT NULL)";

			stmt1.executeUpdate(sql1);

			sql1 = "CREATE TABLE videoconsultation " + "(id_video INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "consultation_date DATE NOT NULL, " + "consultation_time TIME NOT NULL, " + "duration INTEGER, "
					+ "type_of_call TEXT, " + "notes TEXT, "
					+ "id_doctor INTEGER REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL, "
					+ "id_patient INTEGER REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL)";

			stmt1.executeUpdate(sql1);

			sql1 = "CREATE TABLE prescription " + "(id_prescription INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "doses INTEGER NOT NULL, " + "notes TEXT, " + "duration INTEGER NOT NULL, "
					+ "name TEXT NOT NULL, "
					+ "id_video INTEGER REFERENCES videoconsultation(id_video) ON UPDATE CASCADE ON DELETE SET NULL)";

			stmt1.executeUpdate(sql1);

			sql1 = "CREATE TABLE pathology " + "(id_pathology INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "name TEXT NOT NULL, " + "type TEXT)";

			stmt1.executeUpdate(sql1);

			sql1 = "CREATE TABLE patient_pathology " + "(id_patient INTEGER REFERENCES users(id) ON DELETE SET NULL, "
					+ "id_pathology INTEGER REFERENCES pathology(id_pathology) ON UPDATE CASCADE ON DELETE SET NULL, "
					+ "PRIMARY KEY(id_patient,id_pathology))";

			stmt1.executeUpdate(sql1);

			sql1 = "CREATE TABLE rating "
					+ "(id_patient INTEGER REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL, "
					+ "id_doctor INTEGER REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL, "
					+ "score INTEGER, " + "review TEXT, " + "PRIMARY KEY(id_patient,id_doctor))";

			stmt1.executeUpdate(sql1);
			stmt1.close();

		} catch (SQLException e) {
			if (!e.getMessage().contains("already exists")) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void disconnect() {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println("There was a problem whilst closing the database connection");
			e.printStackTrace();
		}
	}

	@Override
	public void addPathology(Pathology p) {

		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO pathology (name, type) VALUES ('" + p.getName() + "', '" + p.getType() + "')";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addRating(Rating r) {
		System.out.println(r.getDoc().getId());
		try {
			String sql = "INSERT INTO rating (id_doctor,id_patient,score,review) VALUES(?,?,?,?)";
			PreparedStatement ps = c.prepareStatement(sql);

			ps.setInt(1, r.getDoc().getId());
			ps.setInt(2, r.getPat().getId());
			ps.setInt(3, r.getScore());
			ps.setString(4, r.getReview());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addVideo_consultation(Video_consultation v) {

		try {

			String sql = "INSERT INTO videoconsultation(consultation_date, consultation_time,type_of_call, id_doctor, id_patient) VALUES(?,?,?,?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setDate(1, v.getConsultation_date());
			ps.setTime(2, v.getConsultatiton_time());
			ps.setString(3, v.getType());
			ps.setInt(4, v.getDoc().getId());
			ps.setInt(5, v.getPat().getId());
			ps.executeUpdate();
			ps.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addPrescription(Prescription p) {

		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO prescription(doses, notes, duration, name, id_video) VALUES (" + p.getDoses()
					+ ",'" + p.getNotes() + "'," + p.getDuration() + ",'" + p.getName() + "'," + p.getVd().getId_video()
					+ ")";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void diagnosePathology(int patient_id, int pathology_id) {
		try {

			String sql = "INSERT INTO patient_pathology(id_patient,id_pathology) VALUES(?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, patient_id);
			ps.setInt(2, pathology_id);
			ps.executeUpdate();
			ps.close();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Doctor> searchDoctorByName(String name) {

		List<Doctor> doctors = new ArrayList<>();

		try {
			String sql = "SELECT * FROM users WHERE name LIKE ? AND role_name=?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, "%" + name + "%");
			stmt.setString(2, "d");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				Doctor doctor = new Doctor(rs.getInt("id"), rs.getString("specialization"), rs.getString("name"),
						rs.getString("hospital"));
				doctor.setRatings(this.getRatingOfDoctor(doctor.getId()));
				doctors.add(doctor);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doctors;
	}

	@Override
	public List<Rating> getRatingOfDoctor(int id_doctor) {
		List<Rating> ratings = new ArrayList<Rating>();
		try {
			String sql = "SELECT*FROM rating WHERE id_doctor=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id_doctor);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				ratings.add(new Rating(rs.getInt("score"), rs.getString("review")));
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return ratings;

	}

	@Override
	public List<Patient> searchPatientByName(String name) {
		List<Patient> patients = new ArrayList<Patient>();

		try {
			String sql = "SELECT * FROM users WHERE name LIKE ? AND role_name=?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, "%" + name + "%");
			stmt.setString(2, "p");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				Patient patient = new Patient(rs.getInt("id"), rs.getString("name"), rs.getString("gender"),
						rs.getDate("birth"), rs.getString("DNI"), rs.getString("phone_number"),
						rs.getString("postcode"));
				patient.setPathologies(getPathologiesOfPatient(patient.getId()));
				patients.add(patient);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patients;
	}

	@Override
	public List<Pathology> getPathologiesOfPatient(int id_patient) {

		List<Pathology> pathologies = new ArrayList<Pathology>();

		try {
			String sql = "SELECT p.id_pathology,p.name,p.type FROM patient_pathology AS pp JOIN pathology AS p ON pp.id_pathology=p.id_pathology WHERE pp.id_patient=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id_patient);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				int id_pathology = rs.getInt("id_pathology");
				String name = rs.getString("name");
				String type = rs.getString("type");
				pathologies.add(new Pathology(id_pathology, name, type));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pathologies;
	}

	@Override
	public List<Pathology> searchPathologyByName(String name) {
		List<Pathology> p = new ArrayList<Pathology>();
		try {
			String sql = "SELECT*FROM pathology WHERE name LIKE ?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, "%" + name + "%");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Pathology py = new Pathology(rs.getInt("id_pathology"), rs.getString("name"), rs.getString("type"));
				p.add(py);

			}
			rs.close();
			stmt.close();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	// Patient method will return a Patient with
	// id,name,gender,birth,id,phone,postcode and pathologies.

	@Override
	public Patient getPatient(int id_patient) {

		try {

			String sql = "SELECT * FROM users WHERE id=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id_patient);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				Patient p = new Patient(id_patient, rs.getString("name"), rs.getString("gender"), rs.getDate("birth"),
						rs.getString("DNI"), rs.getString("phone_number"), rs.getString("postcode"));
				p.setPathologies(getPathologiesOfPatient(p.getId()));
				return p;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	// Doctor method will return a Doctor with id,specialization,name and hospital.

	@Override
	public Doctor getDoctor(int id_doctor) {

		try {

			String sql = "SELECT * FROM users WHERE id=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id_doctor);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				return new Doctor(id_doctor, rs.getString("specialization"), rs.getString("name"),
						rs.getString("hospital"));

			}

			rs.close();
			ps.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Video_consultation getVideo(int id_video) {

		try {
			String sql = "SELECT*FROM videoconsultation WHERE id_video=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id_video);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				Video_consultation v = new Video_consultation(rs.getInt("id_video"), rs.getDate("consultation_date"),
						rs.getTime("consultation_time"), rs.getInt("duration"), rs.getString("type_of_call"),
						rs.getString("notes"), this.getDoctor(rs.getInt("id_doctor")),
						this.getPatient(rs.getInt("id_patient")));
				v.setPrescription(this.getPrescriptionOfVideos(v.getId_video()));
				return v;

			}

			rs.close();
			ps.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Prescription> getPrescriptionOfVideos(int id_video) {

		List<Prescription> prescriptions = new ArrayList<Prescription>();
		try {
			String sql = "SELECT*FROM prescription WHERE id_video=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id_video);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				prescriptions.add(new Prescription(rs.getInt("id_prescription"), rs.getString("name"),
						rs.getInt("doses"), rs.getInt("duration"), rs.getString("notes")));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return prescriptions;
	}

	@Override
	public List<Video_consultation> getVideosOfPatient(int id_patient) {
		List<Video_consultation> videos = new ArrayList<Video_consultation>();
		try {

			String sql = "SELECT * FROM videoconsultation WHERE id_patient=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id_patient);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				videos.add(this.getVideo(rs.getInt("id_video")));

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return videos;
	}

	@Override
	public List<Video_consultation> getPatientFutureVideos(int id_patient) {
		Date d = Date.valueOf(LocalDate.now());

		List<Video_consultation> vd = new ArrayList<Video_consultation>();

		try {
			String sql = "SELECT id_video FROM videoconsultation WHERE consultation_date>? AND id_patient=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setDate(1, d);
			ps.setInt(2, id_patient);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				vd.add(this.getVideo(rs.getInt("id_video")));

			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vd;

	}

	@Override
	public List<Video_consultation> getPatientPreviousVideos(int id_patient) {
		Date d = Date.valueOf(LocalDate.now());

		List<Video_consultation> vd = new ArrayList<Video_consultation>();

		try {
			String sql = "SELECT id_video FROM videoconsultation WHERE consultation_date<? AND id_patient=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setDate(1, d);
			ps.setInt(2, id_patient);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				vd.add(this.getVideo(rs.getInt("id_video")));

			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vd;

	}

	@Override
	public List<Video_consultation> getVideosOfDoctor(int id_doctor) {
		List<Video_consultation> videos = new ArrayList<Video_consultation>();
		try {

			String sql = "SELECT * FROM videoconsultation WHERE id_doctor=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id_doctor);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				videos.add(this.getVideo(rs.getInt("id_video")));

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return videos;
	}

	@Override
	public List<Video_consultation> getDoctorFutureVideos(int id_doctor) {
		Date d = Date.valueOf(LocalDate.now());

		List<Video_consultation> vd = new ArrayList<Video_consultation>();

		try {
			String sql = "SELECT id_video FROM videoconsultation WHERE consultation_date>? AND id_doctor=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setDate(1, d);
			ps.setInt(2, id_doctor);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				vd.add(this.getVideo(rs.getInt("id_video")));

			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vd;

	}

	@Override
	public List<Video_consultation> getDoctorPreviousVideos(int id_doctor) {
		Date d = Date.valueOf(LocalDate.now());

		List<Video_consultation> vd = new ArrayList<Video_consultation>();

		try {
			String sql = "SELECT id_video FROM videoconsultation WHERE consultation_date<? AND id_doctor=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setDate(1, d);
			ps.setInt(2, id_doctor);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				vd.add(this.getVideo(rs.getInt("id_video")));

			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vd;

	}

	@Override
	public void fireDoctor(int id) {
		try {
			String sql = "DELETE FROM doctor WHERE id_doctor = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletePatient(int id) {
		try {
			String sql = "DELETE FROM patient WHERE id_patient = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAppointment(int id) {
		try {
			String sql = "DELETE FROM videoconsultation WHERE id_video = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void changeAppointmentDate(Date d, int id) {
		try {
			String sql = "UPDATE videoconsultation SET consultation_date=? WHERE id_video=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setDate(1, d);
			prep.setInt(2, id);
			prep.executeUpdate();
			prep.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void changeAppointmentTime(Time t, int id) {
		try {
			String sql = "UPDATE videoconsultation SET consultation_time=? WHERE id_video=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setTime(1, t);
			prep.setInt(2, id);
			prep.executeUpdate();
			prep.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Time> availableHours(int id_doctor, Date consultation_date) {
		List<Time> hours = new ArrayList<Time>();
		LocalTime time = LocalTime.parse("09:00", formatterTime);

		for (int i = 0; i < 11; i++) {
			hours.add(Time.valueOf(time.plusHours(i)));
		}
		try {
			String sql = "SELECT consultation_time FROM videoconsultation WHERE id_doctor=? AND consultation_date=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id_doctor);
			ps.setDate(2, consultation_date);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hours.remove(rs.getTime("consultation_time"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hours;
	}

	public void changeVideoconsultationNotes(String notes, int id) {

		try {
			String sql = "UPDATE videoconsultation SET notes=? WHERE id_video=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, notes);
			prep.setInt(2, id);
			prep.executeUpdate();
			prep.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void changeVideoconsultationDuration(int duration, int id) {

		try {
			String sql = "UPDATE videoconsultation SET duration=? WHERE id_video=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, duration);
			prep.setInt(2, id);
			prep.executeUpdate();
			prep.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Doctor> searchDoctorType(String name) {
		List<Doctor> doctors = new ArrayList<Doctor>();
		try {
			String sql = "SELECT * FROM user WHERE role_name=? AND specialist=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "d");
			ps.setString(2, name);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				doctors.add(this.getDoctor(rs.getInt("id_video")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return doctors;

	}

	@Override
	public Pathology getPathology(int id_pathology) {
		Pathology p = null;
		try {

			String sql = "SELECT*FROM pathology WHERE id_pathology=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id_pathology);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				p = new Pathology(id_pathology, rs.getString("name"), rs.getString("type"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}

}
