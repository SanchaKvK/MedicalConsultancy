package InputOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import db.pojos.Doctor;
import db.pojos.Pathology;
import db.pojos.Patient;
import db.pojos.Video_consultation;
import db.pojos.users.User;
import mconsultancy.db.ifaces.DBinterface;
import mconsultancy.db.ifaces.UserInterface;
import medicalConsultancy.db.DBManager;
import medicalConsultancy.db.JPAUserManager;


public class inputOutput {

	private static User user;
	private static UserInterface usman = new JPAUserManager();
	private static DBinterface dbman = new DBManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

	// it shows these three options. The user will not be able to exit the loop if
	// the user enters options that are not there or if he/she enters letters.
	public static Integer OptionMenuLoginRegister() throws IOException {
		Integer option = -55;
		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Choose an option:");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("0. Exit");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				option = Integer.parseInt(string);
				if (option < 0 || option > 2) {
					System.out.println("Error: " + option + " is not an option");

				}

				else
					break;

			}

		}

		return option;

	}

	// it shows these two options. The user will not be able to exit the loop if the
	// user enters options that are not there or if he/she enters letters.
	public static Integer OptionMenuRegister() throws IOException {
		Integer option = -55;
		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Are you a patient? Press 1");
			System.out.println("Are you a doctor? Press 2");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				option = Integer.parseInt(string);
				if (option < 0 || option > 2) {
					System.out.println("Error: " + option + " is not an option");

				}

				else
					break;

			}

		}

		return option;

	}

	// it shows these ten options. The user will not be able to exit the loop if the
	// user enters options that are not there or if he/she enters letters.
	public static Integer OptionMenuPatient() throws IOException {
		Integer option = -55;
		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Introduce option: ");
			System.out.println("1. Show my profile");
			System.out.println("2. Make appointment");
			System.out.println("3. Change appointment");
			System.out.println("4. View appointments");
			System.out.println("5. Rate a doctor");
			System.out.println("6. Search doctor");
			System.out.println("7. Cancel appointment");
			System.out.println("8. Delete account");
			System.out.println("9. Emergency");

			System.out.println("0. Exit");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				option = Integer.parseInt(string);
				if (option < 0 || option > 9) {
					System.out.println("Error: " + option + " is not an option");

				}

				else
					break;

			}

		}

		return option;

	}

	// it shows these three options. The user will not be able to exit the loop if
	// the user enters options that are not there or if he/she enters letters.

	public static Integer OptionMenuVideo() throws IOException {
		Integer option = -55;
		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Introduce option: ");
			System.out.println("1. View all videos");
			System.out.println("2. View previous videos");
			System.out.println("3. View future videos");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				option = Integer.parseInt(string);
				if (option < 1 || option > 3) {
					System.out.println("Error: " + option + " is not an option");

				}

				else
					break;

			}

		}

		return option;

	}

	// it shows these eight options. The user will not be able to exit the loop if
	// the user enters options that are not there or if he/she enters letters.
	public static Integer OptionMenuDoctor() throws IOException {
		Integer option = -55;
		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Introduce option: ");
			System.out.println("1. Show my profile");
			System.out.println("2. View patient information");
			System.out.println("3. Change appointment");
			System.out.println("4. View appointments");
			System.out.println("5. View my ratings");
			System.out.println("6. Cancel Appointment");
			System.out.println("7. Add videoconsultation information");
			System.out.println("0. Exit");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				option = Integer.parseInt(string);
				if (option < 0 || option > 7) {
					System.out.println("Error: " + option + " is not an option");

				}

				else
					break;

			}

		}

		return option;

	}

	// This function will ask the patient an email. The function will use checkEmail  function to comprobate if the email has already been created.
	// If the email is already been created, the patient will be asked for a new  email until he introduces an email that is not in the database.
	public static String askEmail() throws IOException {
		String string = "";
		Boolean state = false;
		usman.connect();

		while (true) {
			System.out.println("Introduce your email: ");
			string = reader.readLine();
			state = usman.checkEmail(string);
			if (state == false) {
				break;

			} else
				System.out.println("Error: " + string + " is already in use");

		}

		return string;

	}

	// in the menu, the user will search a doctor by name with the function
	// searchDoctor. It will return a list of doctors with that name.
	// This list of doctors is the list doctors of the askDoctorId function.
	// The user has to introduce the id_doctor of one of the doctors of the list
	// doctors and it can't be a letter.
	// It is possible that the user enters an id_doctor that exists in the database
	// but it may not exist in the list of doctors, so, the user has to introduce
	// an id_doctor of that list doctors.The function will return an Integer, which is the id_doctor of the one of the doctors of the doctors list. 
	public static Integer askDoctorId(List<Doctor> doctors) throws IOException {
		dbman.connect();
		Integer id_doctor = -1;
		String string = "";
		Boolean state = false;

		while (true) {

			System.out.println("Introduce id doctor:");
			for (Doctor doctor : doctors) {
				System.out.println(doctor);
			}
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				id_doctor = Integer.parseInt(string);
				if (dbman.getDoctor(id_doctor) == null || !doctors.contains(dbman.getDoctor(id_doctor))) {
					System.out.println("Error: " + "This id_doctor" + id_doctor
							+ " does not exist in the list of doctors that you have searched with that name");

				}

				else
					break;

			}

		}
		dbman.disconnect();

		return id_doctor;

	}

	// it shows these twp options. The user will not be able to exit the loop if
	// the user enters options that are not there or if he/she enters letters.
	public static String askGender() throws IOException {
		dbman.connect();
		Integer option = -1;
		String string = "";
		String gender = "";
		Boolean state = false;

		while (true) {

			System.out.println("Gender: ");
			System.out.println("1. Male");
			System.out.println("2. Female");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				option = Integer.parseInt(string);
				if (option > 2 || option < 0) {
					System.out.println("Error: " + "This option" + option + " does not exist");

				}

				else
					break;

			}

		}

		if (option == 1)
			gender = "Male";
		else
			gender = "Female";
		dbman.disconnect();

		return gender;

	}

	//We pass a list of patients to the function. This function will show all the patients of that list and the user has to introduce the id of one of those patients.
	//If the option is not an id of the patients of the list or is a letter, the function will keep asking the user for a correct id until he introduce an id of the list of patients.
	public static Integer askPatientId(List<Patient> patients) throws IOException {
		dbman.connect();
		Integer id_patient = -1;
		String string = "";
		Boolean state = false;

		while (true) {

			System.out.println("Introduce id patient:");
			for (Patient patient : patients) {
				System.out.println(patient);
			}
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				id_patient = Integer.parseInt(string);
				if (dbman.getPatient(id_patient) == null || !patients.contains(dbman.getPatient(id_patient))) {
					System.out.println("Error: " + "This id_patient" + id_patient
							+ " does not exist in the list of patients that you have searched with that name");

				}

				else
					break;

			}

		}
		dbman.disconnect();
		return id_patient;

	}

	//We pass a list of pathologies to the function. This function will show all the pathologies of that list and the user has to introduce the id of one of those pathologies.
	//If the option is not an id of the pathologies of the list or is a letter, the function will keep asking the user for a correct id until he introduce an id of the list of pathologies.	
	public static Integer askPathologyId(List<Pathology> p) throws IOException {
		dbman.connect();
		Integer id_path = -1;
		String string = "";
		Boolean state = false;

		while (true) {

			System.out.println("Introduce id pathology:");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				id_path = Integer.parseInt(string);
				if (dbman.getPathology(id_path) == null || !p.contains(dbman.getPathology(id_path))) {
					System.out.println("Error: " + "This id_pathology" + id_path
							+ " does not exist in the list of pathologies that you have searched with that name");

				}

				else
					break;

			}

		}
		dbman.disconnect();

		return id_path;

	}


	//We pass a list of videos to the function. This function will show all the videos of that list and the user has to introduce the id of one of those videos.
	//If the option is not an id of the videos of the list or is a letter, the function will keep asking the user for a correct id until he introduce an id of the list of videos.
	public static Integer askVideoId(List<Video_consultation> vd) throws IOException {
		dbman.connect();
		Integer id_video = -1;
		String string = "";
		Boolean state = false;

		while (true) {

			System.out.println("Introduce id video: ");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				id_video = Integer.parseInt(string);
				if (vd.contains(dbman.getVideo(id_video))) {
					System.out.println("Error: " + "This id_video " + id_video + " does not exist");

				}

				else
					break;

			}

		}
		dbman.disconnect();

		return id_video;

	}

	// It will ask the user to introduce a score from 1 to 5. 
	// The user will not be able to exit the loop if the user enters options that are not between 1 or 5 or if he/she enters letters.

	public static Integer askScore() throws IOException {
		Integer option = -55;
		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Introduce score(1-5): ");

			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				option = Integer.parseInt(string);
				if (option < 1 || option > 5) {
					System.out.println("Error: Score must be between 1 and 5");

				}

				else
					break;

			}

		}

		return option;

	}
	
	// it shows these two options. The user will not be able to exit the loop if
	// the user enters options that are not there or if he/she enters letters.

	public static boolean askYesNo() throws IOException {
		Integer option = -55;
		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Do you want to diagnose a pathology?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				option = Integer.parseInt(string);
				if (option < 0 || option > 2) {
					System.out.println("Error: " + option + " is not an option");

				}

				else
					break;

			}

		}
		if (option == 1)
			state = true;
		else
			state = false;
		return state;

	}

	// It will ask the user to introduce a phone number
	// The user will not be able to exit the loop if the user enters letters.
	public static String askPhone() throws IOException {

		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Introduce your phone number: ");

			string = reader.readLine();
			state = isNumeric(string);

			if (state == true)
				break;

		}

		return string;

	}

	// It will ask the user to introduce a name
	// The user will not be able to exit the loop if the user enters numbers.
	public static String askName() throws IOException {

		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Introduce name: ");

			string = reader.readLine();
			state = isNumeric(string);

			if (state == false)
				break;

		}

		return string;

	}

	// It will ask the user to introduce a specialization
	// The user will not be able to exit the loop if the user enters numbers.
	public static String askSpecialization() throws IOException {

		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Introduce specialization: ");

			string = reader.readLine();
			state = isNumeric(string);

			if (state == false)
				break;

		}

		return string;

	}
	// It will ask the user to introduce a date of an appointment
	// The user will not be able to exit the loop if the user introduces a patron that is not yyyy-mm-dd or if the date of the appointment is in past dates.
	//To comprobate that it follows a patron the function will use the isDate(string) function.
	
	public static LocalDate askDateAppointment() throws IOException {
		String string = "";
		Boolean state = false;
		LocalDate ld;

		while (true) {

			System.out.println("Date of the appointment(yyyy-MM-dd): ");
			string = reader.readLine();
			state = isDate(string);

			if (state == true) {
				ld = LocalDate.parse(string, formatter);

				if (LocalDate.now().isAfter(ld))
					System.out.println("Error: You cannot make appointments in past dates");
				else
					break;

			}

		}

		return ld;

	}
	
	// It will ask the user to introduce a date of birth
	// The user will not be able to exit the loop if the user introduces a patron that is not yyyy-mm-dd or if the date of the birth is in future dates.
	//To comprobate that it follows a patron the function will use the isDate(string) function.

	public static LocalDate askBirth() throws IOException {
		String string = "";
		Boolean state = false;
		LocalDate ld;

		while (true) {

			System.out.println("Birth(yyyy-MM-dd): ");
			string = reader.readLine();
			state = isDate(string);

			if (state == true) {
				ld = LocalDate.parse(string, formatter);

				if (LocalDate.now().isBefore(ld))
					System.out.println("Error: You birth can't be in future dates");
				else
					break;

			}

		}

		return ld;

	}
	// it shows the size of list <time> hours options. The user will not be able to exit the loop if
	// the user enters options that are not there or if he/she enters letters.
	public static Integer availableHoursMenu(List<Time> hours) throws IOException {
		Integer option = -55;
		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Available hours: ");

			for (int i = 0; i < hours.size(); i++) {
				System.out.println(i + 1 + ". " + hours.get(i));
			}
			System.out.println("0. Back to menu");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				option = Integer.parseInt(string);
				if (option < 0 || option > hours.size()) {
					System.out.println("Error: " + option + " is not an option");

				}

				else
					break;

			}

		}

		return option;

	}

	// It will ask the user to introduce a duration of a video consultation
	// The user will not be able to exit the loop if the user enters letters or if the duration is less than 0 or bigger than 60.
	public static Integer askDurationVideo() throws IOException {
		Integer option = -55;
		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Introduce the duration of the video consultation: ");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				option = Integer.parseInt(string);
				if (option < 0 || option > 60) {
					System.out.println("Error: Video consultation must last between 0 and 60 minutes");

				}

				else
					break;

			}

		}

		return option;

	}

	// It will ask the user to introduce a dose of a medication
	// The user will not be able to exit the loop if the user enters letters or if the dose is negative
	public static Integer askDoses() throws IOException {
		Integer option = -55;
		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Introduce doses ");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				option = Integer.parseInt(string);
				if (option < 0) {
					System.out.println("Error: Doses can't be negative");

				}

				else
					break;

			}

		}

		return option;

	}

	// It will ask the user to introduce a duration of medication
	// The user will not be able to exit the loop if the user enters letters or if the duration is negative
	public static Integer askDurationPrescription() throws IOException {
		Integer option = -55;
		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Introduce the duration of the medication ");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				option = Integer.parseInt(string);
				if (option < 0) {
					System.out.println("Error: Duration can't be negative");

				}

				else
					break;

			}

		}

		return option;

	}
	
	
	// it shows these 5 options. The user will not be able to exit the loop if
	// the user enters options that are not there or if he/she enters letters.
	public static String typeAppointment() throws IOException {
		Integer option = -55;
		String string = "";
		Boolean state = false;

		while (true) {
			System.out.println("Type of appointment(1-5): ");
			System.out.println("1. Medical emergencies");
			System.out.println("2. Medical doubts");
			System.out.println("3. Medication");
			System.out.println("4. Treatments");
			System.out.println("5. Review of results");
			string = reader.readLine();
			state = isNumeric(string);

			if (state == true) {
				option = Integer.parseInt(string);
				if (option < 0 || option > 5) {
					System.out.println("Error: " + option + " is not an option");

				}

				else
					break;

			}

		}

		String type = "";
		if (option == 1)
			type = "Medical emergencies";
		else if (option == 2)
			type = "Medical doubts";
		else if (option == 3)
			type = "Medication";
		else if (option == 4)
			type = "Treatments";
		else if (option == 5)
			type = "Review of results";

		return type;

	}

	
	//this function checks is a string is a number. Return true if it's a number and false if is a string.
	public static boolean isNumeric(String string) {
		int numero = 0;
		try {

			numero = Integer.parseInt(string);

			return true;
		} catch (NumberFormatException ex) {
			System.out.println("You have introduce string instead of numbers");
			return false;
		}

	}

	//this function checks if a string can be converted to a date
	public static boolean isDate(String string) {
		LocalDate ld;
		try {

			ld = LocalDate.parse(string, formatter);

			return true;
		} catch (Exception ex) {
			System.out.println("The date does not exist or doesnt follow the patron yyyy-mm-dd");
			return false;
		}

	}

}
