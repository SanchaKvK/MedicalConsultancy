package medicalConsultancy.db;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import db.pojos.Prescription;
import db.pojos.Video_consultation;

import java.io.File;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class XMLManager {

	private JAXBContext jaxbC;
	private Marshaller jaxbM;
	private Unmarshaller jaxbU;
	private File file;
	public void JavatoXMlVideoconsultation(Video_consultation vc) throws JAXBException {
		jaxbC = JAXBContext.newInstance(Video_consultation.class);
		jaxbM = jaxbC.createMarshaller();
		jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		file = new File("/MedicalConsultancy/src/medicalconsultancyxml/xmls/FirstXmlFile.xml");
		jaxbM.marshal(vc,file);
		jaxbM.marshal(vc,System.out);
		
	}
	
	public void JavatoXMlPrescription(Prescription p) throws JAXBException {
		jaxbC = JAXBContext.newInstance(Prescription.class);
		jaxbM = jaxbC.createMarshaller();
		jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		File file = new File("/MedicalConsultancy/src/medicalconsultancyxml/xmls/FirstXmlFile.xml");
		jaxbM.marshal(p,file);
		jaxbM.marshal(p,System.out);
		
	}
	/*
	
	public Video_consultation XmltoJavaVideoconsultation() throws JAXBException {
		jaxbC = JAXBContext.newInstance(Video_consultation.class);
		jaxbU = jaxbC.createUnmarshaller();
		Video_consultation  vc = (Video_consultation) jaxbU.unmarshal(XMLFile)
		
		jaxbM = jaxbC.createMarshaller();
		jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		File file = new File("/MedicalConsultancy/src/medicalconsultancyxml/xmls/FirstXmlFile.xml");
		jaxbM.marshal(vc,file);
		jaxbM.marshal(vc,System.out);
		
	}
	
	public Prescription XmltoJavaPrescription() throws JAXBException {
		jaxbC = JAXBContext.newInstance(Video_consultation.class);
		jaxbM = jaxbC.createMarshaller();
		jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		File file = new File("/MedicalConsultancy/src/medicalconsultancyxml/xmls/FirstXmlFile.xml");
		jaxbM.marshal(vc,file);
		jaxbM.marshal(vc,System.out);
		
	}
	
	*/
	
	
	
	
	
	
	
	/*
	
	
	// Put entity manager and buffered reader here so it can be used
		// in several methods
		private static EntityManager em;
		private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
		private static void printPrescriptions() {
			Query q1 = em.createNativeQuery("SELECT * FROM prescription", Prescription.class);
			List<Prescription> prescriptions = (List<Prescription>) q1.getResultList();
			// Print the departments
			for (Prescription prescription : prescriptions) {
				System.out.println(prescription);
			}
		}
		
		public static void main(String[] args) throws Exception {
			// Get the entity manager
			// Note that we are using the class' entity manager
			em = Persistence.createEntityManagerFactory("user-provider").createEntityManager();
			em.getTransaction().begin();
			em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
			em.getTransaction().commit();
					
			// Create the JAXBContext
			JAXBContext jaxbContext = JAXBContext.newInstance(Prescription.class);
			// Get the marshaller
			Marshaller marshaller = jaxbContext.createMarshaller();
			
			// Pretty formatting
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
			
			// Choose the report to turn into an XML
			// Choose his new department
			printPrescriptions();
			System.out.print("Choose a prescription to turn into an XML file:");
			int rep_id = Integer.parseInt(reader.readLine());
			Query q2 = em.createNativeQuery("SELECT * FROM prescription WHERE id = ?", Prescription.class);
			q2.setParameter(1, rep_id);
			Prescription prescription = (Prescription) q2.getSingleResult();
			
			// Use the Marshaller to marshal the Java object to a file
			File file = new File("./xmls/Sample-Report.xml");
			marshaller.marshal(prescription, file);
			// Printout
			marshaller.marshal(prescription, System.out);

		}
}
	
	/*
	private static EntityManager em;
	
	
	private void exportPrescriptiontoXML(Prescription p) throws JAXBException {
		em = Persistence.createEntityManagerFactory("user-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Prescription.class);

		Marshaller marshaller = jaxbContext.createMarshaller();
		
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		
		File file = new File("./xmls/Sample-Report.xml");
		marshaller.marshal(p, file);
		//para chequear, esto despues se quita de la función 
		marshaller.marshal(p, System.out);

	}
	
	public static void main(String[] args) throws Exception {
			//In actual main 
			//use function to print out all of the prescriptions that exist
			//save the number of the prescription that wants to turned into XML
			// get the prescription and save it into a prescription object
			//use the function export prescriptiontoXML to save the prescription into a file
			Prescription p = new Prescription(1,"Hola",4,)
					private Integer id_prescription;
			@XmlAttribute
			private String name;
			@XmlElement
			private Integer doses;
			@XmlElement
			private Integer duration;
			@XmlElement
			private String notes;
			@ManyToOne(fetch = FetchType.LAZY)

			@JoinColumn(name = "id_video")
			@XmlElement (name = "Video_Consultation")
			private Video_consultation vd; 
			

		}*/
	
	
	public static void main(String[] args) throws Exception {
		
		/*
		
		
		//JDBC
		
		
		System.out.println("Abriendo database+ creando tablas");
		// PRIMERO VAMOS A INSERTAR 3 PRESCRIPTIONS
		
		Class.forName("org.sqlite.JDBC");
		Connection c = DriverManager.getConnection("jdbc:sqlite:./db/med.db");
		c.createStatement().execute("PRAGMA foreign_keys = ON");
		
		Statement stmt;
		//CREAMOS TABLAS	
		stmt = c.createStatement();

		String sql1 = "CREATE TABLE users " + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "role_name TEXT NOT NULL, " + "gender TEXT, " + "birth DATE, " + "DNI TEXT UNIQUE, "
				+ "phone_number TEXT UNIQUE, " + "postcode TEXT, " + "specialization TEXT , "
				+ "name TEXT NOT NULL, " + "hospital TEXT, " + "email TEXT NOT NULL, " + "password BLOB NOT NULL "+" photo BLOB NULL)";

		stmt.executeUpdate(sql1);

		sql1 = "CREATE TABLE videoconsultation " + "(id_video INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "consultation_date DATE NOT NULL, " + "consultation_time TIME NOT NULL, " + "duration INTEGER, "
				+ "type_of_call TEXT, " + "notes TEXT, "
				+ "id_doctor INTEGER REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL, "
				+ "id_patient INTEGER REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL)";

		stmt.executeUpdate(sql1);

		sql1 = "CREATE TABLE prescription " + "(id_prescription INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "doses INTEGER NOT NULL, " + "notes TEXT, " + "duration INTEGER NOT NULL, "
				+ "name TEXT NOT NULL, "
				+ "id_video INTEGER REFERENCES videoconsultation(id_video) ON UPDATE CASCADE ON DELETE SET NULL)";

		stmt.executeUpdate(sql1);

		sql1 = "CREATE TABLE pathology " + "(id_pathology INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "name TEXT NOT NULL, " + "type TEXT)";

		stmt.executeUpdate(sql1);

		sql1 = "CREATE TABLE patient_pathology " + "(id_patient INTEGER REFERENCES users(id) ON DELETE SET NULL, "
				+ "id_pathology INTEGER REFERENCES pathology(id_pathology) ON UPDATE CASCADE ON DELETE SET NULL, "
				+ "PRIMARY KEY(id_patient,id_pathology))";

		stmt.executeUpdate(sql1);

		sql1 = "CREATE TABLE rating "
				+ "(id_patient INTEGER REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL, "
				+ "id_doctor INTEGER REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL, "
				+ "score INTEGER, " + "review TEXT, " + "PRIMARY KEY(id_patient,id_doctor))";

		stmt.executeUpdate(sql1);
		stmt.close();

		//INSERT vamos a usar prepared statement ya para ponerlo en el main asi. 

		
		System.out.println("Insertando varias prescriptions");
		String sql = "INSERT INTO prescription (doses,notes,duration,name,id_video) " + "VALUES (?,?,?,?,?)";
		PreparedStatement prep = c.prepareStatement(sql);
		// PRIMER PRESCRIPTION
		prep.setInt(1,3);
		prep.setString(2, "First dose is the most important");
		prep.setInt(3, 4);
		prep.setString(4,"Ibuprofen");
		prep.setInt(4, 12);
		prep.executeUpdate();
		//SEGUNDO PRESCRIPTION
		prep.setInt(1,17);
		prep.setString(2, "Dilute to take");
		prep.setInt(3, 13);
		prep.setString(4,"Metalgial");
		prep.setInt(4, 12);
		prep.executeUpdate();

		//System.out.println("Choose a prescription");
		// CREAR FUNCION PARA MOSTRAR PRESCRIPTIONS
		 
		
		Statement stmt2 = c.createStatement();
		String sql2 = "SELECT * FROM prescription";
		ResultSet rs = stmt2.executeQuery(sql2);
		while(rs.next()) {
			Integer id = rs.getInt("id_prescription");
			Integer doses = rs.getInt("doses");
			String notes = rs.getString("notes");
			Integer duration = rs.getInt("duration");
			String name = rs.getString("name");
			Integer idvideo = rs.getInt("id_video");
			Prescription p = new Prescription(name,doses,duration,notes);
			System.out.println(p);
		}
		
		//ACCESO A LA BASE DE DATOS EN ESTE CASO LO HACEMOS MEDIANTE DE JDBC
		rs.close();
		stmt2.close();
		//COGEMOS EL VALOR QUE QUIERA EL PACIENTE
		//HACEMOS FUNCION METEMOS EL OBJETO DENTRO DE LA FUNCIOn
		//CREAMOS LA FUNCION
		//METEMOS VALORES 
		prep.close();
		c.close();*/
		
		
	}
	
}
	
