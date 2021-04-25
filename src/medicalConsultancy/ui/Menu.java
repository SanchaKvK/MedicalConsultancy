package medicalConsultancy.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import db.pojos.Doctor;
import db.pojos.Pathology;
import db.pojos.Patient;
import db.pojos.Prescription;
import db.pojos.Rating;
import db.pojos.Video_consultation;
import medicalConsultancy.db.DBManager;

public class Menu {

	private static DBManager dbman = new DBManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

	public static void main(String[] args) throws Exception {
		dbman.connect();
		do {
			System.out.println("Choose an option:");
			System.out.println("1. Are you a patient");
			System.out.println("2. Are you a doctor");
			System.out.println("3. Add patient");
			System.out.println("4. Add doctor");

			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				patientMenu();
				break;
			case 2:
				doctorMenu();
				break;

			case 3:
				addPatient();
				break;
			case 4:
				addDoctor();
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

		System.out.println("Introduce your id: ");
		System.out.println(dbman.getDoctorFutureVideos(Integer.parseInt(reader.readLine())));
		System.out.println("Choose id video: ");
		dbman.deleteAppointment(Integer.parseInt(reader.readLine()));

	}

	private static void updateVideoDoctor() throws Exception {

		System.out.println("Introduce your id: ");
		int id_doctor = Integer.parseInt(reader.readLine());
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

		System.out.println("Introduce your id: ");
		System.out.println(dbman.getPatientFutureVideos(Integer.parseInt(reader.readLine())));

		System.out.println("Choose id video: ");
		int id_video = Integer.parseInt(reader.readLine());

		System.out.println("New Date(yyyy-MM-dd): ");
		LocalDate date = LocalDate.parse(reader.readLine(), formatter);
		if (date.isBefore(LocalDate.now())) {
			System.out.println("Invalid date");
			return;
		}

		int id_doctor = dbman.getVideo(id_video).getDoc().getId_doctor();

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

		System.out.println("Introduce your id: ");
		System.out.println(dbman.getPatientFutureVideos(Integer.parseInt(reader.readLine())));
		System.out.println("Choose id video: ");
		dbman.deleteAppointment(Integer.parseInt(reader.readLine()));

	}

	private static void getDoctorRatings() throws Exception {

		System.out.println("Id doctor: ");
		System.out.println(dbman.getRatingOfDoctor(Integer.parseInt(reader.readLine())));

	}

	private static void doctorGetPatient() throws Exception {

		System.out.println("Patient id");
		int id = Integer.parseInt(reader.readLine());
		System.out.println(dbman.getPatient(id));

	}

	private static void getFuturePatientVideos() throws Exception {

		System.out.println("Id patient: ");
		List<Video_consultation> l = dbman.getPatientFutureVideos(Integer.parseInt(reader.readLine()));
		for (Video_consultation video : l) {
			System.out.println(video);

		}

	}

	private static void getPreviousPatientVideos() throws Exception {

		System.out.println("Id patient: ");
		List<Video_consultation> l = dbman.getPatientPreviousVideos(Integer.parseInt(reader.readLine()));
		for (Video_consultation video : l) {
			System.out.println(video);

		}

	}

	private static void getAllPatientVideos() throws Exception {

		System.out.println("Id patient: ");
		List<Video_consultation> l = dbman.getVideosOfPatient(Integer.parseInt(reader.readLine()));
		for (Video_consultation video : l) {
			System.out.println(video);

		}

	}

	private static void getFutureDoctorVideos() throws Exception {

		System.out.println("Id doctor: ");
		System.out.println(dbman.getDoctorFutureVideos(Integer.parseInt(reader.readLine())));

	}

	private static void getPreviousDoctorVideos() throws Exception {

		System.out.println("Id doctor: ");
		System.out.println(dbman.getDoctorPreviousVideos(Integer.parseInt(reader.readLine())));

	}

	private static void getAllDoctorVideos() throws Exception {

		System.out.println("Id doctor: ");
		System.out.println(dbman.getVideosOfDoctor(Integer.parseInt(reader.readLine())));

	}

	private static void prescribe() throws Exception {
		System.out.println("Doses: ");
		Integer doses = Integer.parseInt(reader.readLine());
		System.out.println("Duration: ");
		Integer duration = Integer.parseInt(reader.readLine());
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Notes: ");
		String notes = reader.readLine();
		System.out.println("Introduce id video: ");
		Integer id_video = Integer.parseInt(reader.readLine());
		Prescription p = new Prescription(name, doses, duration, notes, dbman.getVideo(id_video));
		dbman.addPrescription(p);

	}

	private static void rate() throws Exception {
		System.out.println("Introduce your id:");
		Integer id_patient = Integer.parseInt(reader.readLine());
		searchDoctor();
		System.out.println("Introduce doctor`s id: ");
		Integer id_doctor = Integer.parseInt(reader.readLine());

		System.out.println("Review: ");
		String review = reader.readLine();
		System.out.println("Score(1-5): ");
		Integer score = Integer.parseInt(reader.readLine());

		Rating rating = new Rating(dbman.getDoctor(id_doctor), dbman.getPatient(id_patient), score, review);
		dbman.addRating(rating);

	}

	private static void getDoctor() throws Exception {
		System.out.println("Doctor id:");

		System.out.println(dbman.getDoctor(Integer.parseInt(reader.readLine())));

	}

	private static void getPatient() throws Exception {

		System.out.println("Patient id");
		Patient p = dbman.getPatient(Integer.parseInt(reader.readLine()));
		System.out.println("Patient [id_patient=" + p.getId_patient() + ", name=" + p.getName() + ", gender="
				+ p.getGender() + ", birth=" + p.getBirth() + ", id=" + p.getId() + ", phone_number="
				+ p.getPhone_number() + ", postcode=" + p.getPostcode() + "]");

	}

	private static void addPatient() throws Exception {

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
		Patient p = new Patient(name, gender, Date.valueOf(birth), id, phone, pc);

		dbman.addPatient(p);

	}

	private static void addDoctor() throws Exception {

		System.out.println("Introduce doctor info: ");
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Specialization: ");
		String specialization = reader.readLine();
		System.out.println("Hospital: ");
		String hospital = reader.readLine();
		Doctor d = new Doctor(specialization, name, hospital);

		dbman.addDoctor(d);

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
		System.out.println("Which is your id?");
		Integer id_patient = Integer.parseInt(reader.readLine());

		Video_consultation vd = new Video_consultation(Date.valueOf(date), hours.get(index - 1), type,
				dbman.getDoctor(id_doctor), dbman.getPatient(id_patient));
		dbman.addVideo_consultation(vd);

	}

}
