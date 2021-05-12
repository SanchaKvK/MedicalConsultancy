package medicalConsultancy.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import mconsultancy.db.ifaces.UserInterface;
import db.pojos.Doctor;
import db.pojos.Pathology;
import db.pojos.Patient;
import db.pojos.Prescription;
import db.pojos.Rating;
import db.pojos.Video_consultation;

import db.pojos.users.User;
import mconsultancy.db.ifaces.DBinterface;
import medicalConsultancy.db.DBManager;
import medicalConsultancy.db.UserManager;

public class Menu {

	private static User user;
	private static UserInterface usman = new UserManager();
	private static DBinterface dbman = new DBManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

	public static void main(String[] args) throws Exception {
		dbman.connect();

		usman.connect();
		do {
			System.out.println("Choose an option:");
			System.out.println("1. Register");
			System.out.println("2. Login");

			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				register();
				break;
			case 2:
				login();
				break;
			case 0:
				dbman.disconnect();
				System.exit(0);
				break;
			default:
				break;
			}
		} while (true);

	}

	public static void register() throws Exception {

		System.out.println("Are you a patient? Press 1");
		System.out.println("Are you a doctor? Press 2");
		int option = Integer.parseInt(reader.readLine());

		if (option == 1) {

			Patient p = askPatient();
			usman.addUser(p);

		} else if (option == 2) {
			usman.addUser(askDoctor());
		}

	}

	public static void login() throws Exception {

		System.out.println("Introduce email: ");
		String email = reader.readLine();
		System.out.println("Introduce password");
		String password = reader.readLine();
		User us = usman.checkPassword(email, password);
		if (us == null) {
			System.out.println("Wrong password or email");
			return;
		}

		else if (us.getRole_name().equals("d")) {
			user = us;
			System.out.println(us);
			doctorMenu();
		} else if (us.getRole_name().equals("p")) {
			user = us;
			patientMenu();
		}
	}

	private static void patientMenu() throws Exception {

		while (true) {
			System.out.println("Introduce option: ");
			System.out.println("1. Show my profile");
			System.out.println("2. Make appointment");
			System.out.println("3. Change appointment");
			System.out.println("4. View future appointments");
			System.out.println("5. View previous appointments");
			System.out.println("6. View all appointments");
			System.out.println("7. Rate a doctor");
			System.out.println("8. Search doctor");
			System.out.println("9. Cancel appointment");
			System.out.println("10. Emergency");

			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				getPatient();
				break;
			case 2:
				makeAppointment();
				break;
			case 3:
				updateVideoPatient();
				break;
			case 4:
				getFuturePatientVideos();
				break;
			case 5:
				getPreviousPatientVideos();
				break;

			case 6:
				getAllPatientVideos();
				break;

			case 7:
				rate();
				break;

			case 8:
				searchDoctor();
				break;
			case 9:
				deleteVideoPatient();
				break;
			case 0:
				dbman.disconnect();
				System.exit(0);
				break;
			default:
				break;
			}

		}
	}

	private static void doctorMenu() throws Exception {

		while (true) {
			System.out.println("Introduce option: ");
			System.out.println("1. Show my profile");
			System.out.println("2. View patient information");
			System.out.println("3. Change appointment");
			System.out.println("4. View future appointments");
			System.out.println("5. View previous appointments");
			System.out.println("6. View all appointments");
			System.out.println("7. View my ratings");
			System.out.println("8. Cancel Appointment");
			System.out.println("9. Add videoconsultation information");
			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				getDoctor();
				break;
			case 2:
				doctorGetPatient();
				break;
			case 3:
				updateVideoDoctor();
				break;
			case 4:
				getFutureDoctorVideos();
				break;
			case 5:
				getPreviousDoctorVideos();
				break;

			case 6:
				getAllDoctorVideos();
				break;

			case 7:
				getDoctorRatings();
				break;

			case 8:
				deleteVideoDoctor();
				break;

			case 9:
				addInfoVideoconsultation();
				break;

			case 0:
				dbman.disconnect();
				System.exit(0);
				break;

			default:
				break;
			}

		}
	}

	private static void deleteVideoDoctor() throws Exception {

		System.out.println(dbman.getDoctorFutureVideos(user.getId()));
		System.out.println("Choose id video: ");
		dbman.deleteAppointment(Integer.parseInt(reader.readLine()));

	}

	private static void updateVideoDoctor() throws Exception {

		int id_doctor = user.getId();
		System.out.println(dbman.getDoctorFutureVideos(id_doctor));

		System.out.println("Choose id video: ");
		int id_video = Integer.parseInt(reader.readLine());

		System.out.println("New Date(yyyy-MM-dd): ");
		LocalDate date = LocalDate.parse(reader.readLine(), formatter);
		if (date.isBefore(LocalDate.now())) {
			System.out.println("Invalid date");
			return;
		}

		List<Time> hours = dbman.availableHours(id_doctor, Date.valueOf(date));
		System.out.println("Available hours: ");

		for (int i = 0; i < hours.size(); i++) {
			System.out.println(i + 1 + ". " + hours.get(i));
		}
		System.out.println("0. Back to menu");
		int index = Integer.parseInt(reader.readLine());
		if (index == 0)
			return;
		dbman.changeAppointmentDate(Date.valueOf(date), id_video);
		dbman.changeAppointmentTime(hours.get(index - 1), id_video);

	}

	private static void updateVideoPatient() throws Exception {

		System.out.println(dbman.getPatientFutureVideos(user.getId()));

		System.out.println("Choose id video: ");
		int id_video = Integer.parseInt(reader.readLine());

		System.out.println("New Date(yyyy-MM-dd): ");
		LocalDate date = LocalDate.parse(reader.readLine(), formatter);
		if (date.isBefore(LocalDate.now())) {
			System.out.println("Invalid date");
			return;
		}

		int id_doctor = dbman.getVideo(id_video).getDoc().getId();

		List<Time> hours = dbman.availableHours(id_doctor, Date.valueOf(date));
		System.out.println("Available hours: ");

		for (int i = 0; i < hours.size(); i++) {
			System.out.println(i + 1 + ". " + hours.get(i));
		}
		System.out.println("0. Back to menu");
		int index = Integer.parseInt(reader.readLine());
		if (index == 0) {
			return;
		}

		dbman.changeAppointmentDate(Date.valueOf(date), id_video);
		dbman.changeAppointmentTime(hours.get(index - 1), id_video);

	}

	private static void deleteVideoPatient() throws Exception {

		System.out.println(dbman.getPatientFutureVideos(user.getId()));
		System.out.println("Choose id video: ");
		dbman.deleteAppointment(Integer.parseInt(reader.readLine()));

	}

	private static void getDoctorRatings() throws Exception {

		System.out.println(dbman.getRatingOfDoctor(user.getId()));

	}

	private static void doctorGetPatient() throws Exception {

		System.out.println("Patient id");
		int id = Integer.parseInt(reader.readLine());
		System.out.println(dbman.getPatient(id));

	}

	private static void getFuturePatientVideos() throws Exception {

		List<Video_consultation> l = dbman.getPatientFutureVideos(user.getId());
		for (Video_consultation video : l) {
			System.out.println(video);

		}

	}

	private static void getPreviousPatientVideos() throws Exception {

		List<Video_consultation> l = dbman.getPatientPreviousVideos(user.getId());
		for (Video_consultation video : l) {
			System.out.println(video);

		}

	}

	private static void getAllPatientVideos() throws Exception {

		List<Video_consultation> l = dbman.getVideosOfPatient(user.getId());
		for (Video_consultation video : l) {
			System.out.println(video);

		}

	}

	private static void getFutureDoctorVideos() throws Exception {

		System.out.println(dbman.getDoctorFutureVideos(user.getId()));

	}

	private static void getPreviousDoctorVideos() throws Exception {

		System.out.println(dbman.getDoctorPreviousVideos(user.getId()));

	}

	private static void getAllDoctorVideos() throws Exception {

		System.out.println(dbman.getVideosOfDoctor(user.getId()));

	}

	private static void rate() throws Exception {

		Integer id_patient = user.getId();
		searchDoctor();
		System.out.println("Introduce doctor`s id: ");
		Integer id_doctor = Integer.parseInt(reader.readLine());

		System.out.println("Review: ");
		String review = reader.readLine();
		System.out.println("Score(1-5): ");
		Integer score = Integer.parseInt(reader.readLine());
		System.out.println(dbman.getDoctor(id_doctor));

		Rating rating = new Rating(dbman.getDoctor(id_doctor), dbman.getPatient(id_patient), score, review);
		
		dbman.addRating(rating);

	}

	private static void getDoctor() throws Exception {

		System.out.println(user.getId());
		System.out.println(dbman.getDoctor(user.getId()));

	}

	private static void getPatient() throws Exception {

		
		Patient p = dbman.getPatient(user.getId());
		System.out.println("Patient [id_patient=" + p.getId() + ", name=" + p.getName() + ", gender=" + p.getGender()
				+ ", birth=" + p.getBirth() + ", id=" + p.getId() + ", phone_number=" + p.getPhone_number()
				+ ", postcode=" + p.getPostcode() + "]");

	}

	private static Patient askPatient() throws Exception {
		System.out.println("Introduce your email: ");
		String email = reader.readLine();

		System.out.println("Introduce password:");
		String password = reader.readLine();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		System.out.println("Introduce patient info: ");
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Gender: ");
		String gender = reader.readLine();
		System.out.print("Birth (yyyy-MM-dd): ");
		LocalDate birth = LocalDate.parse(reader.readLine(), formatter);
		System.out.println("Id: ");
		String id = reader.readLine();
		System.out.println("Phone number: ");
		String phone = reader.readLine();
		System.out.println("Postcode: ");
		String pc = reader.readLine();
		Patient p = new Patient(email, hash, "p", name, gender, Date.valueOf(birth), id, phone, pc);

		return p;

	}

	private static Doctor askDoctor() throws Exception {
		System.out.println("Introduce your email: ");
		String email = reader.readLine();

		System.out.println("Introduce password:");
		String password = reader.readLine();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();

		System.out.println("Introduce doctor info: ");
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Specialization: ");
		String specialization = reader.readLine();
		System.out.println("Hospital: ");
		String hospital = reader.readLine();

		Doctor d = new Doctor(email, hash, "d", specialization, name, hospital);

		return d;

	}

	private static void diagnose() throws Exception {

		searchPatient();
		System.out.println("Choose patient`s id: ");
		Integer patient_id = Integer.parseInt(reader.readLine());
		searchPathologies();
		System.out.println("Choose pathology`s id: ");
		Integer pathology_id = Integer.parseInt(reader.readLine());
		dbman.diagnosePathology(patient_id, pathology_id);
	}

	private static void searchPatient() throws Exception {

		System.out.println("Introduce patient name: ");
		String name = reader.readLine();
		System.out.println(dbman.searchPatientByName(name));

	}

	private static void searchDoctor() throws Exception {

		System.out.println("Introduce doctor name: ");
		String name = reader.readLine();
		System.out.println(dbman.searchDoctorByName(name));

	}

	private static void searchPathologies() throws Exception {
		System.out.println("Introduce pathology`s name: ");
		String name = reader.readLine();
		System.out.println(dbman.searchPathologyByName(name));

	}

	private static void makeAppointment() throws Exception {

		searchDoctor();
		System.out.println("Introduce doctor`s id: ");
		Integer id_doctor = Integer.parseInt(reader.readLine());
		System.out.println("Date of the appointment(yyyy-MM-dd): ");
		LocalDate date = LocalDate.parse(reader.readLine(), formatter);
		List<Time> hours = dbman.availableHours(id_doctor, Date.valueOf(date));
		System.out.println("Available hours: ");

		for (int i = 0; i < hours.size(); i++) {
			System.out.println(i + 1 + ". " + hours.get(i));
		}
		System.out.println("0. Back to menu");
		int index = Integer.parseInt(reader.readLine());
		if (index == 0)
			return;

		System.out.println("Type of appointment: ");
		String type = reader.readLine();

		Integer id_patient = user.getId();

		Video_consultation vd = new Video_consultation(Date.valueOf(date), hours.get(index - 1), type,
				dbman.getDoctor(id_doctor), dbman.getPatient(id_patient));
		dbman.addVideo_consultation(vd);

	}

	private static void addInfoVideoconsultation() throws Exception {

		System.out.println("Videoconsultation information: ");
		System.out.println("Introduce the id of the videoconsultation: ");
		getPreviousDoctorVideos();
		Integer id_video = Integer.parseInt(reader.readLine());
		System.out.println("Introduce the duration: ");
		Integer duration = Integer.parseInt(reader.readLine());
		dbman.changeVideoconsultationDuration(duration, id_video);
		System.out.println("Introduce the doctor's notes: ");
		String notes = reader.readLine();
		dbman.changeVideoconsultationNotes(notes, id_video);
		System.out.println("Introduce the prescription: ");
		prescribe(id_video);
		System.out.println("Do you want to diagnose a pathology? Y/N");

		String option = reader.readLine();
		if (option == "Y")
			diagnose();

	}

	private static void prescribe(int id_video) throws Exception {
		System.out.println("Doses: ");
		Integer doses = Integer.parseInt(reader.readLine());
		System.out.println("Duration: ");
		Integer duration = Integer.parseInt(reader.readLine());
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Notes: ");
		String notes = reader.readLine();
		System.out.println("Introduce id video: ");

		Prescription p = new Prescription(name, doses, duration, notes, dbman.getVideo(id_video));
		dbman.addPrescription(p);

	}

}
