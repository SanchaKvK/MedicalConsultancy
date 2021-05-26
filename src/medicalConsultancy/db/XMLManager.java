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
	
}
	
