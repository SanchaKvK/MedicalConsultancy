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

import InputOutput.inputOutput;

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

			int choice = inputOutput.OptionMenuLoginRegister();
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

		int option = inputOutput.OptionMenuRegister();

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

			int choice = inputOutput.OptionMenuPatient();

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
				videosMenu();
				break;
			case 5:
				rate();
				break;

			case 6:
				searchDoctor();
				break;

			case 7:
				deleteVideoPatient();
				break;

			case 8:
				deleteAccount();
				return;
			case 9:
				emergency();
				break;
			case 0:
				dbman.disconnect();
				usman.disconnect();
				System.exit(0);
				break;
			default:
				break;
			}
		}

	}

	private static void videosMenu() throws Exception {

		while (true) {

			int choice = inputOutput.OptionMenuVideo();
			String role_name = user.getRole_name();

			switch (choice) {

			case 1:
				if (role_name == "d")
					getAllDoctorVideos();
				else
					getAllPatientVideos();

				return;
			case 2:
				if (role_name == "d")
					getPreviousDoctorVideos();
				else
					getPreviousPatientVideos();

				return;
			case 3:
				if (role_name == "d")
					getFutureDoctorVideos();
				else
					getFuturePatientVideos();
				return;
			}

		}

	}

	private static void doctorMenu() throws Exception {

		while (true) {

			try {

				int choice = inputOutput.OptionMenuDoctor();

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
					videosMenu();
					break;
				case 5:
					getDoctorRatings();
					break;

				case 6:
					deleteVideoDoctor();
					break;

				case 7:
					addInfoVideoconsultation();
					break;

				case 0:
					dbman.disconnect();
					usman.disconnect();
					System.exit(0);
					break;

				default:
					break;
				}

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static void deleteAccount() {

		usman.deleteUser(user);

	}

	// if you want to cancel an appointment
	private static void deleteVideoDoctor() throws Exception {
		List<Video_consultation> vd = dbman.getDoctorFutureVideos(user.getId());
		if (vd == null) {
			System.out.println("You have no future appointments");
			return;
		} else
			dbman.deleteAppointment(inputOutput.askVideoId(vd));

	}

	// if you wanna change the appointment
	private static void updateVideoDoctor() throws IOException {

		int id_doctor = user.getId();
		List<Video_consultation> vd = dbman.getDoctorFutureVideos(id_doctor);
		if (vd == null) {
			System.out.println("You have no future videos");
			return;
		}
		for (Video_consultation video_consultation : vd) {
			System.out.println(vd);
		}

		int id_video = inputOutput.askVideoId(vd);

		LocalDate date = inputOutput.askDateAppointment();

		List<Time> hours = dbman.availableHours(id_doctor, Date.valueOf(date));

		int index = inputOutput.availableHoursMenu(hours);

		if (index == 0)
			return;
		dbman.changeAppointmentDate(Date.valueOf(date), id_video);
		dbman.changeAppointmentTime(hours.get(index - 1), id_video);

	}

	private static void updateVideoPatient() throws Exception {

		List<Video_consultation> vd = dbman.getPatientFutureVideos(user.getId());
		if (vd == null) {
			System.out.println("You have no future videos");
			return;
		}

		int id_video = inputOutput.askVideoId(vd);

		LocalDate date = inputOutput.askDateAppointment();

		int id_doctor = dbman.getVideo(id_video).getDoc().getId();

		List<Time> hours = dbman.availableHours(id_doctor, Date.valueOf(date));

		int index = inputOutput.availableHoursMenu(hours);
		if (index == 0) {
			return;
		}

		dbman.changeAppointmentDate(Date.valueOf(date), id_video);
		dbman.changeAppointmentTime(hours.get(index - 1), id_video);

	}

	// if you want to cancel an appointment
	private static void deleteVideoPatient() throws Exception {

		List<Video_consultation> vd = dbman.getPatientFutureVideos(user.getId());
		if (vd == null) {
			System.out.println("You have no future video_consultations");
		} else {
			for (Video_consultation video_consultation : vd) {
				System.out.println(vd);

			}

			dbman.deleteAppointment(inputOutput.askVideoId(vd));
		}

	}

	private static void getDoctorRatings() throws Exception {

		List<Rating> ratings = dbman.getRatingOfDoctor(user.getId());
		if (ratings.isEmpty()) {
			System.out.println("You have no ratings");
			return;
		}

		else
			for (Rating rating : ratings) {
				System.out.println(rating);
			}

	}

	private static void doctorGetPatient() throws IOException {

		System.out.println("Introduce patient name: ");
		String name = reader.readLine();
		List<Patient> patients = dbman.searchPatientByName(name);
		if(patients.isEmpty())System.out.println("There are no patients with that name");
		else for (Patient patient : patients) {
			System.out.println(patient);
		}
		

	}

	private static void getFuturePatientVideos() throws Exception {

		List<Video_consultation> l = dbman.getPatientFutureVideos(user.getId());
		if (l == null) {
			System.out.println("You have no future videos");
			return;
		}
		for (Video_consultation video : l) {
			System.out.println(video);

		}

	}

	private static void getPreviousPatientVideos() throws Exception {

		List<Video_consultation> l = dbman.getPatientPreviousVideos(user.getId());
		if (l == null) {
			System.out.println("You have no previous videos");
			return;
		}
		for (Video_consultation video : l) {
			System.out.println(video);

		}

	}

	private static void getAllPatientVideos() throws Exception {

		List<Video_consultation> l = dbman.getVideosOfPatient(user.getId());
		if (l == null) {
			System.out.println("You have no videos");
			return;
		}
		for (Video_consultation video : l) {
			System.out.println(video);

		}

	}

	private static void getFutureDoctorVideos() throws Exception {

		List<Video_consultation> l = dbman.getDoctorFutureVideos(user.getId());
		if (l == null) {
			System.out.println("You have no future videos");
			return;
		}
		for (Video_consultation video_consultation : l) {
			System.out.println(video_consultation);
		}

	}

	private static void getPreviousDoctorVideos() throws Exception {

		List<Video_consultation> l = dbman.getDoctorPreviousVideos(user.getId());
		if (l == null) {
			System.out.println("You have no previous videos");
			return;
		}
		for (Video_consultation video_consultation : l) {
			System.out.println(video_consultation);
		}

	}

	private static void getAllDoctorVideos() throws Exception {

		List<Video_consultation> l = dbman.getVideosOfDoctor(user.getId());
		if (l == null) {
			System.out.println("You have no videos");
			return;
		}
		for (Video_consultation video_consultation : l) {
			System.out.println(video_consultation);
		}

	}

	private static void rate() throws Exception {

		Integer id_patient = user.getId();
		List<Doctor>d=searchDoctor();

		Integer id_doctor = inputOutput.askDoctorId(d);

		System.out.println("Review: ");
		String review = reader.readLine();

		Rating rating = new Rating(dbman.getDoctor(id_doctor), dbman.getPatient(id_patient), score, review);

		dbman.addRating(rating);

	}

	private static void getDoctor() throws Exception {

		System.out.println(user.getId());
		System.out.println(dbman.getDoctor(user.getId()));

	}

	private static void getPatient() throws Exception {

		Patient p = dbman.getPatient(user.getId());
		System.out.println(dbman.getPatient(user.getId()));

	}

	private static Patient askPatient() throws Exception {
	
		String email = inputOutput.askEmail();

		System.out.println("Introduce password:");
		String password = reader.readLine();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		System.out.println("Introduce patient info: ");

		String name = inputOutput.askName();

		String gender = inputOutput.askGender();

		LocalDate birth = inputOutput.askBirth();

		System.out.println("Id: ");
		String id = reader.readLine();

		String phone = inputOutput.askPhone();

		System.out.println("Postcode: ");
		String pc = reader.readLine();
		Patient p = new Patient(email, hash, "p", name, gender, Date.valueOf(birth), id, phone, pc);

		return p;

	}

	private static Doctor askDoctor() throws Exception {
		
		String email = inputOutput.askEmail();

		System.out.println("Introduce password:");
		String password = reader.readLine();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();

		System.out.println("Introduce doctor info: ");

		String name = inputOutput.askName();

		String specialization = inputOutput.askSpecialization();

		System.out.println("Hospital: ");
		String hospital = reader.readLine();

		Doctor d = new Doctor(email, hash, "d", specialization, name, hospital);

		return d;
		
		
		}

	}

	private static void diagnose() throws Exception {

		List<Patient>p=searchPatient();
		Integer patient_id = inputOutput.askPatientId(p);
		List<Pathology> path=searchPathologies();

		Integer pathology_id = inputOutput.askPathologyId(path);
		dbman.diagnosePathology(patient_id, pathology_id);
	}

	private static List<Patient> searchPatient() throws Exception {

		System.out.println("Introduce patient name: ");
		String name = reader.readLine();
		return dbman.searchPatientByName(name);

	}

	private static List<Doctor> searchDoctor() throws Exception {

		System.out.println("Introduce doctor name: ");
		String name = reader.readLine();
		return dbman.searchDoctorByName(name);

	}

	private static List<Pathology> searchPathologies() throws Exception {
		System.out.println("Introduce pathology`s name: ");
		String name = reader.readLine();
		return dbman.searchPathologyByName(name);

	}

	private static void makeAppointment() throws Exception {

		List<Doctor>d=searchDoctor();
		Integer id_doctor = inputOutput.askDoctorId(d);

		LocalDate date = inputOutput.askDateAppointment();

		List<Time> hours = dbman.availableHours(id_doctor, Date.valueOf(date));

		int index = inputOutput.availableHoursMenu(hours);
		if (index == 0)
			return;

		String type = inputOutput.typeAppointment();

		Integer id_patient = user.getId();

		Video_consultation vd = new Video_consultation(Date.valueOf(date), hours.get(index - 1), type,
				dbman.getDoctor(id_doctor), dbman.getPatient(id_patient));
		dbman.addVideo_consultation(vd);

	}

	// to fill the information of previous appointments

	private static void addInfoVideoconsultation() throws Exception {

		System.out.println("Videoconsultation information: ");
		List<Video_consultation> vd = dbman.getDoctorPreviousVideos(user.getId());
		if (vd == null) {
			System.out.println("You have no previous video consultations");
			return;
		}

		for (Video_consultation video_consultation : vd) {
			System.out.println(video_consultation);
		}
		int id_video = inputOutput.askVideoId(vd);

		Integer duration = inputOutput.askDurationVideo();
		System.out.println("Introduce the doctor's notes: ");
		String notes = reader.readLine();
		System.out.println("Introduce the prescription: ");
		Prescription p = prescribe(id_video);
		usman.addInfoVideo(id_video, notes, duration, p);
		

		Boolean optionDiagnose =inputOutput.askYesNo();
		if (optionDiagnose == true)
			diagnose();

	}

	private static Prescription prescribe(int id_video) throws Exception {

		Integer doses = inputOutput.askDoses();

		Integer duration = inputOutput.askDurationPrescription();
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Notes: ");
		String notes = reader.readLine();

		Prescription p = new Prescription(name, doses, duration, notes);
		dbman.addPrescription(p);
		return p;

	}

	private static void emergency() {

		List<Doctor> doctors = dbman.searchDoctorType("Emergency:");
		
		int id_doctor=(int) (Math.random()*doctors.size());
		
		List<Doctor> doctors = dbman.searchDoctorType("Medical emergencies");
		if (doctors == null) {
			System.out.println("Right now we do not have an emergency doctor");
			return;
		}

		int id_doctor = (int) (Math.random() * doctors.size());

		List<Doctor> doctors = dbman.searchDoctorType("Medical emergencies");
		if (doctors == null) {
			System.out.println("Right now we do not have an emergency doctor");
			return;
		}

		int id_doctor = (int) (Math.random() * doctors.size());

		List<Doctor> doctors = dbman.searchDoctorType("Medical emergencies");
		if (doctors == null) {
			System.out.println("Right now we do not have an emergency doctor");
			return;
		}

		int id_doctor = (int) (Math.random() * doctors.size());

		LocalDate date = LocalDate.now();


		LocalTime time = LocalTime.now();
		Integer id_patient = user.getId();

		Video_consultation vd = new Video_consultation(Date.valueOf(date), Time.valueOf(time), "Medical emergencies",
				dbman.getDoctor(id_doctor), dbman.getPatient(id_patient));
		dbman.addVideo_consultation(vd);

	}

}
