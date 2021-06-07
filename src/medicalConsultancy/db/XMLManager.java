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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class XMLManager {
	private JAXBContext jaxbC;
	private Marshaller jaxbM;
	private File file;
	private Unmarshaller unmarshaller;


	public void JavatoXMlVideoconsultation(Video_consultation vc, File file) {
	try {
		
		// Create the JAXBContext
	jaxbC = JAXBContext.newInstance(Video_consultation.class);
	// Get the marshaller
	Marshaller marshaller = jaxbC.createMarshaller();
	// Pretty formatting
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
	
	marshaller.marshal(vc, System.out);
	marshaller.marshal(vc, file);
	// Printout
	}catch(JAXBException e) {
		e.printStackTrace();
	}
	
}
	
	public void JavatoXMlPrescription(Prescription p, File file) {
	try {
		
		// Create the JAXBContext
	jaxbC = JAXBContext.newInstance(Prescription.class);
	// Get the marshaller
	Marshaller marshaller = jaxbC.createMarshaller();
	// Pretty formatting
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
	marshaller.marshal(p, System.out);
	marshaller.marshal(p, file);
	// Printout
	}catch(JAXBException e) {
		e.printStackTrace();
	}
	
}
	
	public Video_consultation XMLtoJavaVideoconsultation(Patient p, File file){		 
		Video_consultation v= new Video_consultation();
		try {
			jaxbC = JAXBContext.newInstance(Video_consultation.class);
			// Get the unmarshaller
				unmarshaller = jaxbC.createUnmarshaller();
				// Use the Unmarshaller to unmarshal the XML document from a file
				 v = (Video_consultation) unmarshaller.unmarshal(file);
				 v.setPat(p);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return v;
	}


public Prescription XMLtoJavaPrescription(File file){	
	Prescription p= new Prescription();
	try {
		jaxbC = JAXBContext.newInstance(Prescription.class);
		// Get the unmarshaller
			unmarshaller = jaxbC.createUnmarshaller();
			// Use the Unmarshaller to unmarshal the XML document from a file
			 p = (Prescription) unmarshaller.unmarshal(file);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return p;
	
	
	
	
	
	}
	
	
public static void simpleTransform(String sourcePath, String xsltPath,String resultDir) {
	TransformerFactory tFactory = TransformerFactory.newInstance();
	System.out.println("i entered the simple transform function");
	try {
		Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xsltPath)));
		transformer.transform(new StreamSource(new File(sourcePath)),new StreamResult(new File(resultDir)));
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
	
