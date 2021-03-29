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
import db.pojos.Rating;
import db.pojos.Video_consultation;
import medicalConsultancy.db.DBManager;

public class Menu {
	
	
	private static DBManager dbman=new DBManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("hh:mm");
	
	
	public static void main(String[] args) throws Exception {
		dbman.connect();
		do {
		System.out.println("Choose an option:");
		System.out.println("1. Add a patient");
		System.out.println("2. Add a doctor");
		System.out.println("3. Add a pathology");
		System.out.println("4. Search a patient");
		System.out.println("5. Search a doctor");
		System.out.println("6. Search a pathology");
		System.out.println("7. Diagnose pathology");
		System.out.println("8. Make an appointment");
		
		System.out.println("0. Exit");
		int choice = Integer.parseInt(reader.readLine());
		switch (choice) {
		case 1:
			addPatient();
			break;
		case 2:
			addDoctor();
			break;
			
		case 3: 
			addPathology();
			break;
		case 4:
			searchPatient();
			break;
			
		case 5:
			searchDoctor();
			break;
			
		case 6:
			searchPathologies();
			break;
			
		case 7:
			diagnose();
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
	
	
	private static void addPathology() throws Exception{
		
		
		System.out.println("Introduce pathology info: ");
		System.out.println("Name: ");
		String name=reader.readLine();
		System.out.println("Type: ");
		String type=reader.readLine();
		dbman.addPathology(new Pathology(name,type));
		
		
	}
	
	
	private static void makeAppointment()throws Exception{
		
		System.out.println("Date of the appointment(yyyy-MM-dd): ");
		LocalDate date=LocalDate.parse(reader.readLine(),formatter);
		System.out.println("Hour of the appointment");
		LocalTime time=LocalTime.parse(reader.readLine(), formatterTime);
		System.out.println("Type of appointment: ");
		String type=reader.readLine();
		searchDoctor();
		System.out.println("Introduce doctor`s id: ");
		Integer id_doctor=Integer.parseInt(reader.readLine());
		System.out.println("Which is your id?");
		Integer id_patient=Integer.parseInt(reader.readLine());
		
		
		Video_consultation vd=new Video_consultation(Date.valueOf(date),Time.valueOf(time),type,dbman.getDoctor(id_doctor),dbman.getPatient(id_patient));
		dbman.addVideo_consultation(vd);
		
		
	}
		
		private static void addPatient() throws Exception {
			
			System.out.println("Introduce patient info: ");
			System.out.println("Name: ");
			String name=reader.readLine();
			System.out.println("Gender: ");
			String gender=reader.readLine();
			System.out.print("Birth (yyyy-MM-dd): ");
			LocalDate birth = LocalDate.parse(reader.readLine(), formatter);
			System.out.println("Id: ");
			String id=reader.readLine();
			System.out.println("Phone number: ");
			String phone=reader.readLine();
			System.out.println("Postcode: ");
			String pc=reader.readLine();
			Patient p=new Patient(name,gender,Date.valueOf(birth),id,phone,pc);
			
			dbman.addPatient(p);
			
			
		}
		
		
		private static void addDoctor() throws Exception {
			
			System.out.println("Introduce doctor info: ");
			System.out.println("Name: ");
			String name=reader.readLine();
			System.out.println("Specialization: ");
			String specialization=reader.readLine();
			System.out.println("Hospital: ");
			String hospital=reader.readLine();
			Doctor d=new Doctor(specialization,name,hospital);
			
			
			dbman.addDoctor(d);
			
			
		}
		
		private static void diagnose() throws Exception {
			
			searchPatient();
			System.out.println("Choose patient`s id: ");
			Integer patient_id=Integer.parseInt(reader.readLine());
			searchPathologies();
			System.out.println("Choose pathology`s id: ");
			Integer pathology_id=Integer.parseInt(reader.readLine());
			dbman.diagnosePathology(patient_id, pathology_id);
		}
		
		
		private static void searchPatient() throws Exception {
			
			System.out.println("Introduce patient name: ");
			String name=reader.readLine();
			System.out.println(dbman.searchPatientByName(name));
			
			
		}
		
		private static void searchDoctor() throws Exception {
			
			System.out.println("Introduce doctor name: ");
			String name=reader.readLine();
			System.out.println(dbman.searchDoctorByName(name));
			
			
		}
		
		private static void searchPathologies()throws Exception{
			System.out.println("Introduce pathology`s name: ");
			String name=reader.readLine();
			System.out.println(dbman.searchPathologyByName(name));
			
		}
		
		
		
	
	

}
