package medicalconsultancyxml.utils;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

public class SQLTimeAdapter extends XmlAdapter<String,Time>{

	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
	
	
	
	@Override
	public Time unmarshal(String v) throws Exception {
		LocalTime localTime = LocalTime.parse(v, formatter);
		return Time.valueOf(localTime);
	}

	
	
	@Override
	public String marshal(Time sqlTime) throws Exception {
		// TODO Auto-generated method stub
		return sqlTime.toLocalTime().format(formatter);
					
	}
	
}
