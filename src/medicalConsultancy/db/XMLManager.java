package medicalConsultancy.db;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import db.pojos.Patient;
import db.pojos.Prescription;
import db.pojos.Video_consultation;

import java.awt.Menu;
import java.io.File;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class XMLManager {
	private JAXBContext jaxbC;
	private Marshaller jaxbM;
	private File file;
	private Unmarshaller unmarshaller;


	public void JavatoXMlVideoconsultation(Video_consultation vc) {
	try {
		
		// Create the JAXBContext
	jaxbC = JAXBContext.newInstance(Video_consultation.class);
	// Get the marshaller
	Marshaller marshaller = jaxbC.createMarshaller();
	// Pretty formatting
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
	
	marshaller.marshal(vc, System.out);
	file = new File("/Users/sanchavonknobloch/git/MedicalConsultancy/src/medicalconsultancyxml/utils/Sample-videoconsultation.xml");
	marshaller.marshal(vc, file);
	// Printout
	}catch(JAXBException e) {
		e.printStackTrace();
	}
	
}
	
	public void JavatoXMlPrescription(Prescription p) {
	try {
		
		// Create the JAXBContext
	jaxbC = JAXBContext.newInstance(Prescription.class);
	// Get the marshaller
	Marshaller marshaller = jaxbC.createMarshaller();
	// Pretty formatting
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
	marshaller.marshal(p, System.out);
	file = new File("/Users/sanchavonknobloch/git/MedicalConsultancy/src/medicalconsultancyxml/utils/Sample_prescription.xml");
	marshaller.marshal(p, file);
	// Printout
	}catch(JAXBException e) {
		e.printStackTrace();
	}
	
}
	
	public Video_consultation XMLtoJavaVideoconsultation(Patient p){		 
		Video_consultation v= new Video_consultation(p);
		try {
			jaxbC = JAXBContext.newInstance(Video_consultation.class);
			// Get the unmarshaller
				unmarshaller = jaxbC.createUnmarshaller();
				// Use the Unmarshaller to unmarshal the XML document from a file
				file = new File("/Users/sanchavonknobloch/git/MedicalConsultancy/src/medicalconsultancyxml/utils/dtdcheckvideoconsultation.xml");
				 v = (Video_consultation) unmarshaller.unmarshal(file);
				 System.out.println("duracion"+v.getDuration()+"tiempo"+v.getConsultation_date()+"consultation time"+v.getConsultatiton_time()+"doctor"+v.getDoc()+"patient"+v.getPat());
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return v;
	}

/*
public Prescription XMLtoJavaPrescription(){	
	}
*/	
	
	
}
	
