package medicalConsultancy.db;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import db.pojos.Prescription;
import db.pojos.Video_consultation;

import java.awt.Menu;
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
	private File file;
	
	public void JavatoXMlVideoconsultation(Video_consultation vc) throws JAXBException {
	// Create the JAXBContext
	JAXBContext jaxbC = JAXBContext.newInstance(Video_consultation.class);
	// Get the marshaller
	Marshaller marshaller = jaxbC.createMarshaller();
	// Pretty formatting
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
	File file = new File("./medicalconsultancyxml.utils/Sample-report.xml");
	marshaller.marshal(vc, file);
	// Printout
	marshaller.marshal(vc, System.out);
	
	}
	
}
	
