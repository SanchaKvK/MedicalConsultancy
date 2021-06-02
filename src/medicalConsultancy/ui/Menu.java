package medicalConsultancy.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import medicalConsultancy.db.JPAUserManager;
import medicalConsultancy.db.XMLManager;
import InputOutput.inputOutput;

public class Menu {

	private static User user;
	private static UserInterface usman = new JPAUserManager();
	private static DBinterface dbman = new DBManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
	private static XMLManager xmltransitionobject;

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
				usman.disconnect();
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
				getAllPatientVideos();
				JavaVideoConsultationtoXML();
				break;
			case 9:
				JavaPrescriptiontoXML();
				break;
			case 10:
				XMLVideoConsultationtoJava();
				break;
			case 11:
				XMLPrescriptiontoJava();
				break;
			case 12:
				emergency();
				break;
			case 13:
				deleteAccount();
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

				if (role_name.equals("d")) {

					getAllDoctorVideos();
				} else {
					getAllPatientVideos();
				}

				return;
			case 2:
				if (role_name.equals("d"))
					getPreviousDoctorVideos();
				else
					getPreviousPatientVideos();

				return;
			case 3:
				if (role_name.equals("d"))
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
				case 8:
					diagnose();
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

		String yesNo = inputOutput.askPhoto();

		if (yesNo.equalsIgnoreCase("N")) {

			Doctor d = new Doctor(email, hash, "d", specialization, name, hospital, null);
			return d;

		} else {

			System.out.print("Type the file name as it appears in photos, including extension: ");
			String fileName = reader.readLine();
			File photo = new File("./photos/" + fileName);
			InputStream streamBlob = new FileInputStream(photo);
			byte[] bytesBlob = new byte[streamBlob.available()];
			streamBlob.read(bytesBlob);
			streamBlob.close();
			Doctor d = new Doctor(email, hash, "d", specialization, name, hospital, bytesBlob);
			return d;

		}

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

	// PATIENT MENU

	// OPTION 1 OF THE PATIENT MENU : VIEW THEIR OWN PROFILE INFORMATION

	private static void getPatient() throws Exception {

		Patient p = dbman.getPatient(user.getId());
		System.out.println(dbman.getPatient(user.getId()));

	}

	// OPTION 2 OF THE PATIENT MENU : MAKE AN APPOINTMENT

	private static void makeAppointment() throws Exception {

		List<Doctor> d = searchDoctor();
		if (d.isEmpty()) {
			System.out.println("There is no doctor with that name");
			return;
		}
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

	private static List<Doctor> searchDoctor() throws Exception {

		System.out.println("Introduce doctor name: ");
		String name = reader.readLine();
		return dbman.searchDoctorByName(name);

	}

	// OPTION 3 OF THE PATIENT MENU : EDIT THE DATE OF AN APPOINTMENT

	private static void updateVideoPatient() throws Exception {

		List<Video_consultation> vd = dbman.getPatientFutureVideos(user.getId());
		if (vd.isEmpty()) {
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

	// OPTION 4 OF THE PATIENT MENU : VIEW VIDEOCONSULTATIONS

	private static void getAllPatientVideos() throws Exception {

		List<Video_consultation> l = dbman.getVideosOfPatient(user.getId());
		if (l.isEmpty()) {
			System.out.println("You have no videos");
			return;
		}
		for (Video_consultation video : l) {
			System.out.println(video);

		}

	}

	private static void getPreviousPatientVideos() throws Exception {

		List<Video_consultation> l = dbman.getPatientPreviousVideos(user.getId());
		if (l.isEmpty()) {
			System.out.println("You have no previous videos");
			return;
		}
		for (Video_consultation video : l) {
			System.out.println(video);

		}

	}

	private static void getFuturePatientVideos() throws Exception {

		List<Video_consultation> l = dbman.getPatientFutureVideos(user.getId());
		if (l.isEmpty()) {
			System.out.println("You have no future videos");
			return;
		}
		for (Video_consultation video : l) {
			System.out.println(video);

		}

	}

	// OPTION 5 OF THE PATIENT MENU : RATE A DOCTOR

	private static void rate() throws Exception {

		Integer id_patient = user.getId();
		List<Doctor> d = searchDoctor();
		if (d.isEmpty()) {
			System.out.println("There is no doctor with that name");
			return;
		}

		Integer id_doctor = inputOutput.askDoctorId(d);

		Integer score = inputOutput.askScore();
		System.out.println("Review: ");
		String review = reader.readLine();

		Rating rating = new Rating(dbman.getDoctor(id_doctor), dbman.getPatient(id_patient), score, review);

		dbman.addRating(rating);

	}

	// OPTION 7 OF THE PATIENT MENU : DELETE A VIDEOCONSULTATION

	// if you want to cancel an appointment
	private static void deleteVideoPatient() throws Exception {

		List<Video_consultation> vd = dbman.getPatientFutureVideos(user.getId());
		if (vd.isEmpty()) {
			System.out.println("You have no future video_consultations");
		} else {
			for (Video_consultation video_consultation : vd) {
				System.out.println(video_consultation);

			}

			dbman.deleteAppointment(inputOutput.askVideoId(vd));
			System.out.println("The videoconsultation appointment has been removed");
		}

	}

	// OPTION 8 OF THE PATIENT MENU : TURN A PATIENT VIDEOCONSULTATION INTO AN XML
	// FILE

	private static void JavaVideoConsultationtoXML() throws Exception {

		List<Video_consultation> vd = dbman.getVideosOfPatient(user.getId());
		if (vd.isEmpty()) {
			System.out.println("You have no videos to turn into an XML file");
			return;
		}
		int id_video = inputOutput.askVideoId(vd);
		Video_consultation video = dbman.getVideo(id_video);
		xmltransitionobject.JavatoXMlVideoconsultation(video);

	}

	// OPTION 9 OF THE PATIENT MENU : TURN A PATIENT PRESCRIPTION INTO AN XML FILE

	private static void JavaPrescriptiontoXML() throws Exception {

		// Video_consultation video = dbman.getVideo(id_video);

		// marshaller.JavatoXMlVideoconsultation(video);

	}

	// OPTION 10 OF THE PATIENT MENU : TURN AN XML FILE WITH A VIDEOCONSULTATION
	// INTO A JAVA VIDEOCONSULTATION OBJECT

	private static void XMLVideoConsultationtoJava() throws Exception {

	}

	// OPTION 11 OF THE PATIENT MENU : TURN AN XML FILE WITH A PRESCRIPTION INTO A
	// JAVA VIDEOCONSULTATION OBJECT

	private static void XMLPrescriptiontoJava() throws Exception {
	}

	// OPTION 12 OF THE PATIENT MENU : EMERGENCY OPTION

	private static void emergency() {

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

	// OPTION 13 OF THE PATIENT MENU : DELETE THE ACCOUNT

	private static void deleteAccount() {

		usman.deleteUser(user);

	}

	// DOCTOR MENU

	// OPTION 1 OF THE DOCTOR MENU : VIEW THEIR OWN PROFILE INFORMATION

	private static void getDoctor() throws Exception {

		System.out.println(user.getId());
		System.out.println(dbman.getDoctor(user.getId()));

	}

	// OPTION 2 OF THE DOCTOR MENU : VIEW A PATIENT'S INFORMATION

	private static void doctorGetPatient() throws IOException {

		System.out.println("Introduce patient name: ");
		String name = reader.readLine();
		List<Patient> patients = dbman.searchPatientByName(name);
		if (patients.isEmpty())
			System.out.println("There are no patients with that name");
		else
			for (Patient patient : patients) {
				System.out.println(patient);
			}

	}

	// OPTION 3 OF THE DOCTOR MENU : CHANGE A VIDEOCONSULTATION DATE

	private static void updateVideoDoctor() throws IOException {

		int id_doctor = user.getId();
		List<Video_consultation> vd = dbman.getDoctorFutureVideos(id_doctor);
		if (vd.isEmpty()) {
			System.out.println("You have no future videos");
			return;
		}
		for (Video_consultation video_consultation : vd) {
			System.out.println(video_consultation);
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

	// OPTION 4 OF THE DOCTOR MENU : VIEW THEIR VIDEOCONSULTATIONS

	private static void getPreviousDoctorVideos() throws Exception {

		List<Video_consultation> l = dbman.getDoctorPreviousVideos(user.getId());
		if (l.isEmpty()) {
			System.out.println("You have no previous videos");
			return;
		}
		for (Video_consultation video_consultation : l) {
			System.out.println(video_consultation);
		}

	}

	private static void getAllDoctorVideos() throws Exception {

		List<Video_consultation> l = dbman.getVideosOfDoctor(user.getId());

		if (l.isEmpty()) {
			System.out.println("You have no videos");
			return;
		}
		for (Video_consultation video_consultation : l) {
			System.out.println(video_consultation);
		}

	}

	private static void getFutureDoctorVideos() throws Exception {

		List<Video_consultation> l = dbman.getDoctorFutureVideos(user.getId());
		if (l.isEmpty()) {
			System.out.println("You have no future videos");
			return;
		}
		for (Video_consultation video_consultation : l) {
			System.out.println(video_consultation);
		}

	}

	// OPTION 5 OF THE DOCTOR MENU : GET THEIR RATINGS

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

	// OPTION 5 OF THE DOCTOR MENU : CANCEL A VIDEOCONSULTATION

	private static void deleteVideoDoctor() throws Exception {
		List<Video_consultation> vd = dbman.getDoctorFutureVideos(user.getId());
		if (vd.isEmpty()) {
			System.out.println("You have no future appointments");
			return;
		} else
			dbman.deleteAppointment(inputOutput.askVideoId(vd));

	}

	// OPTION 5 OF THE DOCTOR MENU : ADD MORE INFORMATION ON A VIDEOCONSULTATION

	// to fill the information of previous appointments
	private static void addInfoVideoconsultation() throws Exception {

		System.out.println("Videoconsultation information: ");
		List<Video_consultation> vd = dbman.getVideosOfDoctor(user.getId());
		if (vd.isEmpty()) {
			System.out.println("You have no previous video consultations");
			return;
		}

		for (Video_consultation video_consultation : vd) {
			System.out.println(video_consultation);
		}
		int id_video = inputOutput.askVideoId(vd);
		Video_consultation v = dbman.getVideo(id_video);

		Integer duration = inputOutput.askDurationVideo();
		System.out.println("Introduce the doctor's notes on the videoconsultation: ");
		dbman.changeVideoconsultationDuration(duration, id_video);

		String notes = reader.readLine();
		System.out.println("Introduce the prescription: ");
		dbman.changeVideoconsultationNotes(notes, id_video);

		prescribe(v);


	}

	private static void prescribe(Video_consultation v) throws Exception {

		Integer doses = inputOutput.askDoses();

		Integer duration = inputOutput.askDurationPrescription();
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Notes regarding the prescription: ");
		String notes = reader.readLine();

		Prescription p = new Prescription(name, doses, duration, notes, v);
		dbman.addPrescription(p);

	}

	private static void diagnose() throws Exception {

		List<Patient> p = searchPatient();
		if (p.isEmpty()) {
			System.out.println("There is no patient with that name");
			return;
		}
		Integer id_patient = inputOutput.askPatientId(p);
		List<Pathology> path = searchPathologies();

		if (path.isEmpty()) {
			System.out.println("There is no pathology with that name");
			return;
		}

		Integer id_pathology = inputOutput.askPathologyId(path);

		Patient patient = usman.getPatient(id_patient);
		Pathology pathology = usman.getPathology(id_pathology);
		
		if (patient.getPathologies().contains(pathology)) {
			System.out.println("The patient already has this disease");
			return;
		}
		usman.diagnose(patient, pathology);
	}

	private static List<Patient> searchPatient() throws Exception {

		System.out.println("Introduce patient name: ");
		String name = reader.readLine();
		return dbman.searchPatientByName(name);

	}

	private static List<Pathology> searchPathologies() throws Exception {
		System.out.println("Introduce pathology`s name: ");
		String name = reader.readLine();
		List<Pathology> p = dbman.searchPathologyByName(name);
		return p;

	}

}
