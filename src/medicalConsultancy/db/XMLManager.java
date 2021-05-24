package medicalConsultancy.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import db.pojos.Prescription;
import db.pojos.Video_consultation;
import mconsultancy.db.ifaces.XMLinterface;

public class XMLManager {

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
	

	
