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
	
	
	private static DBManager dbman=new DBManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
	
	
	public static void main(String[] args) throws Exception {
		dbman.connect();
		do {
		System.out.println("Choose an option:");
		System.out.println("1. Add a patient");
		System.out.println("2. Add a doctor");
		System.out.println("3. Prescribe");
		System.out.println("4. Make an appointment");
		System.out.println("5. Rate a doctor");
		System.out.println("6. Diagnose");
		System.out.println("7. Search a patient by name");
		System.out.println("8. Search a doctor by name");
		System.out.println("9. Get video by id");

	

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
			prescribe();
			break;
		case 4:
			makeAppointment();
			break;
		case 5:
			rate();
			break;
		case 6:
			diagnose();
			break;
		case 7:
			searchPatient();
			break;
			
		case 8:
			searchDoctor();
			break;
						
		case 9:
			getVideo();
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
	
	
	
	
	private static void prescribe() throws Exception{
		System.out.println("Doses: ");
		Integer doses=Integer.parseInt(reader.readLine());
		System.out.println("Duration: ");
		Integer duration=Integer.parseInt(reader.readLine());
		System.out.println("Name: ");
		String name=reader.readLine();
		System.out.println("Notes: ");
		String notes=reader.readLine();
		System.out.println("Introduce id video: ");
		Integer id_video=Integer.parseInt(reader.readLine());
		Prescription p=new Prescription(name,doses,duration,notes,dbman.getVideo(id_video));
		dbman.addPrescription(p);
		
		
	}
	
	
	
	private static void rate() throws Exception{
		System.out.println("Introduce your id:");
		Integer id_patient=Integer.parseInt(reader.readLine());
		searchDoctor();
		System.out.println("Introduce doctor`s id: ");
		Integer id_doctor=Integer.parseInt(reader.readLine());
		
		System.out.println("Review: ");
		String review=reader.readLine();
		System.out.println("Score(0-5): ");
		Integer score=Integer.parseInt(reader.readLine());
		
		Rating rating=new Rating(dbman.getDoctor(id_doctor),dbman.getPatient(id_patient),score,review);
		dbman.addRating(rating);
		
		
		
		
	}
	private static void getRate() throws Exception{
		
		System.out.println("Introduce your id: ");
		Integer id_patient=Integer.parseInt(reader.readLine());
		searchDoctor();
		System.out.println("Introduce doctor`s id: ");
		Integer id_doctor=Integer.parseInt(reader.readLine());
		System.out.println(dbman.getRating(id_doctor, id_patient));
		
	}
	
	private static void getPrescription()throws Exception{
		
		System.out.println("Prescription id: ");
		System.out.println(dbman.getPrescription(Integer.parseInt(reader.readLine())));
		
		
	}
	
	private static void getVideo()throws Exception{
		
		System.out.println("Video id: ");
		System.out.println(dbman.getVideo(Integer.parseInt(reader.readLine())));
		
		
	}
	private static void getDoctor() throws Exception{
		System.out.println("Doctor id:");
	
		System.out.println(	dbman.getDoctor(Integer.parseInt(reader.readLine())));
		
	}
	
	
	
	private static void getPatient() throws Exception{
		
	System.out.println("Patient id");
		System.out.println(	dbman.getPatient(Integer.parseInt(reader.readLine())));
		
	}
	
	
	private static void getPathology() throws Exception{
		System.out.println("Pathology id: ");
	
		System.out.println(	dbman.getPathology(Integer.parseInt(reader.readLine())));
		
	}

	
	private static void addPathology() throws Exception{
		
		
		System.out.println("Introduce pathology info: ");
		System.out.println("Name: ");
		String name=reader.readLine();
		System.out.println("Type: ");
		String type=reader.readLine();
		dbman.addPathology(new Pathology(name,type));
		
		
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
		
		
		

		}
		

		
	
	


