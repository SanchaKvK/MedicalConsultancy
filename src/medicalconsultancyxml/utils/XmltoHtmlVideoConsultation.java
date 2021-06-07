package medicalconsultancyxml.utils;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XmltoHtmlVideoConsultation {
	
	public static void simpleTransform(String sourcePath, String xsltPath,String resultDir) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		
		try {
			Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.transform(new StreamSource(new File(sourcePath)),new StreamResult(new File(resultDir)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		simpleTransform("/Users/veroniqueperez-sala/git/MedicalConsultancy/src/medicalconsultancyxml/utils/Sample-videoconsultation.xml","/MedicalConsultancy/src/medicalconsultancyxml/utils/Video_consultation-Style.xslt", "/MedicalConsultancy/src/medicalconsultancyxml/utils/External-VideoConsultation.html");

	}

}
