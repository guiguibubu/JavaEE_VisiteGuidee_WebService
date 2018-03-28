package fr.eseo.javaee.projet.tools;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConvertisseurDateTest {

	@Test
	void testAsLocalDate() {
		Date date = null;
		try {
			date = ConvertisseurDate.dateFormatter.parse("1900-01-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(ConvertisseurDate.dateParDefaut, ConvertisseurDate.asLocalDate(date));
	}

	@Test
	void testAsLocalDateTime() {
		Date date = null;
		try {
			date = ConvertisseurDate.dateTimeFormatter.parse("1900-01-01 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(ConvertisseurDate.dateTimeParDefaut, ConvertisseurDate.asLocalDateTime(date));
	}

	@Test
	void testAsUtilDateObjectFromLocalDate() {
		GregorianCalendar c = new GregorianCalendar(1900, 0, 1, 0, 0, 0);
		c.setTimeZone(TimeZone.getDefault());
		Assertions.assertEquals(c.getTime(), ConvertisseurDate.asUtilDate(ConvertisseurDate.dateParDefaut));
	}

	@Test
	void testAsUtilDateObjectFromLocalDateTime() {
		GregorianCalendar c = new GregorianCalendar(1900, 0, 1, 0, 0, 0);
		c.setTimeZone(TimeZone.getDefault());
		Assertions.assertEquals(c.getTime(), ConvertisseurDate.asUtilDate(ConvertisseurDate.dateTimeParDefaut));
	}

}
