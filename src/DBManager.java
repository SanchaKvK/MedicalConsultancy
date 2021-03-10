import java.sql.*;

import db.pojos.Patient;

public class DBManager {
	
	
	private Connection c;
	
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/MedicalConsultancy.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened");
			this.createTables();
			
		}catch(SQLException sqlE) {
			System.out.println("There was a database exception");
			
			
		}catch(Exception e) {
			System.out.println("There was a general exception");
			e.printStackTrace();
		}
	}
	
	private void createTables() {
		Statement stmt1;
		try {
			stmt1 = c.createStatement();
		
			String sql1 = " CREATE TABLE patient"
					+ "(id_patient INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT NOT NULL"
					+ "gender TEXT"
					+ "date of birth DATE NOT NULL"
					+ "id TEXT NOT NULL UNIQUE"
					+ "phone number TEXT UNIQUE"
					+ "postcode TEXT)";
			
			stmt1.executeUpdate(sql1);
			sql1 = "CREATE TABLE doctor"
					+ "(id_doctor INTEGER PRIMARY KEY AUTOINCREMENT"
					+ "specialization TEXT NOT NULL"
					+ "name TEXT NOT NULL"
					+ "hospital TEXT)";
			stmt1.executeUpdate(sql1);
			
			
			sql1 = "CREATE TABLE videoconsultation"
					 + "id_video INTEGER PRIMARY KEY AUTOINCREMENT"
					+	"consultation_date DATE NOT NULL"
					+		"consultation_time TIME NOT NULL"
					+		"duration INTEGER"
					+	"type of call TEXT"
					+	"notes TEXT"
					+  "id_doctor INTEGER REFERENCES doctor(id_doctor) ON DELETE SET NULL"
					+ "id_patient INTEGER REFERENCES patient(id_patient) ON DELETE SET NULL)";	

			stmt1.executeUpdate(sql1);
			
			
			sql1 = "CREATE TABLE prescription"
					+ "(id_prescription INTEGER PRIMARY KEY AUTOINCREMENT"
					+ "doses INTEGER NOT NULL"
					+ "notes TEXT"
					+ "duration INTEGER NOT NULL"
					+ "name TEXT NOT NULL"
					+ "id_video INTEGER REFERENCES video_consultation(id_video) ON DELETE SET NULL)";
			
			stmt1.executeUpdate(sql1);
			
			sql1 = "CREATE TABLE pathology"
					+ "id_pathology INTEGER PRIMARY KEY AUTOINCREMENT"
					+ "name TEXT NOT NULL"
					+ "type TEXT"
					+ "CREATE TABLE patient_pathology"
					+ "id_patient INTEGER REFERENCES patient(id_patient) ON DELETE SET NULL"
					+ "id_pathology INTEGER REFERENCES pathology(id_pathology) ON DELETE SET NULL"
					+ "PRIMARY KEY(id_patient,id_pathology))";
					
			stmt1.executeUpdate(sql1);
			
			sql1 = "CREATE TABLE rating"
					+ "id_patient INTEGER REFERENCES patient(id_patient) ON DELETE SET NULL"
					+ "id_doctor INTEGER REFERENCES doctor(id_doctor) ON DELETE SET NULL"
					+ "score INTEGER"
					+ "review TEXT"
					+ "PRIMARY KEY(id_patient,id_doctor))";
			
			
			stmt1.executeUpdate(sql1);
			stmt1.close();
					
		}catch(SQLException e) {
			if(!e.getMessage().contains("already exists")) {
				e.printStackTrace();
			}
		}
		
	}

	public void disconnect() {
		try {
			c.close();
		}catch(SQLException e) {
			System.out.println("There was a problem whilst closing the database connection");
			e.printStackTrace();
		}
	}
	
	
	public void addPatient(Patient p){
		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO Patient (name , gender, date of birth, id, phone number, postcode) INSERT ("") "
		}
	}
		
	
	
	
	

}
