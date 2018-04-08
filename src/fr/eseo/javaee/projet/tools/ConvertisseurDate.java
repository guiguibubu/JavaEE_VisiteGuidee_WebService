package fr.eseo.javaee.projet.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ConvertisseurDate {

	public static final String DATE_FORMATTER_STRING = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMATTER_STRING = "yyyy-MM-dd HH:mm:ss";
	public static final DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATTER_STRING);
	public static final DateFormat dateTimeFormatter = new SimpleDateFormat(DATE_TIME_FORMATTER_STRING);

	public static final LocalDateTime dateTimeParDefaut = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
	public static final LocalDate dateParDefaut = LocalDate.of(1900, 1, 1);

	private ConvertisseurDate() {}
	/**
	 * Renvoie un objet {@link java.time.LocalDate} correspondant à la date de l'objet {@link java.util.Date}
	 * @param date, L'objet Date que l'on souhaite convertir
	 * @return L'objet LocalDate correspondant à la date entrée
	 */
	public static LocalDate asLocalDate(java.util.Date date) {
		LocalDate dateTime;
		if(date == null) {
			dateTime = dateParDefaut;
		} else {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(date);
			c.setTimeZone(TimeZone.getDefault());
			dateTime = LocalDate.of(c.get(Calendar.YEAR),
					c.get(Calendar.MONTH)+1,
					c.get(Calendar.DAY_OF_MONTH));
		}
		return dateTime;
	}

	/**
	 * Renvoie un objet {@link java.time.LocalDateTime} correspondant à la date de l'objet {@link java.util.Date}
	 * @param date, L'objet Date que l'on souhaite convertir
	 * @return L'objet LocalDateTime correspondant à la date entrée
	 */
	public static LocalDateTime asLocalDateTime(java.util.Date date) {
		LocalDateTime dateTime;
		if(date == null) {
			dateTime = dateTimeParDefaut;
		} else {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(date);
			c.setTimeZone(TimeZone.getDefault());
			dateTime = LocalDateTime.of(c.get(Calendar.YEAR),
					c.get(Calendar.MONTH)+1,
					c.get(Calendar.DAY_OF_MONTH),
					c.get(Calendar.HOUR_OF_DAY),
					c.get(Calendar.MINUTE),
					c.get(Calendar.SECOND));
		}
		return dateTime;
	}

	/**
	 * Calls {@link #asUtilDate(Object, ZoneId)} with the system default time zone.
	 */
	public static java.util.Date asUtilDate(Object date) {
		return asUtilDate(date, ZoneId.systemDefault());
	}

	/**
	 * Creates a {@link java.util.Date} from various date objects. Is null-safe. Currently supports:<ul>
	 * <li>{@link java.util.Date}
	 * <li>{@link java.sql.Date}
	 * <li>{@link java.sql.Timestamp}
	 * <li>{@link java.time.LocalDate}
	 * <li>{@link java.time.LocalDateTime}
	 * <li>{@link java.time.ZonedDateTime}
	 * <li>{@link java.time.Instant}
	 * </ul>
	 *
	 * @param zone Time zone, used only if the input object is LocalDate or LocalDateTime.
	 *
	 * @return {@link java.util.Date} (exactly this class, not a subclass, such as java.sql.Date)
	 */
	public static java.util.Date asUtilDate(Object date, ZoneId zone) {
		if (date == null) {
			return null;
		}

		if (date instanceof java.sql.Date || date instanceof java.sql.Timestamp) {
			return new java.util.Date(((java.util.Date) date).getTime());
		}
		if (date instanceof java.util.Date) {
			return (java.util.Date) date;
		}
		if (date instanceof LocalDate) {
			GregorianCalendar c = new GregorianCalendar(((LocalDate) date).getYear(), ((LocalDate) date).getMonthValue()-1, ((LocalDate) date).getDayOfMonth());
			c.setTimeZone(TimeZone.getDefault());
			return c.getTime();
		}
		if (date instanceof LocalDateTime) {
			GregorianCalendar c = new GregorianCalendar(((LocalDateTime) date).getYear(), ((LocalDateTime) date).getMonthValue()-1, ((LocalDateTime) date).getDayOfMonth(), ((LocalDateTime) date).getHour(), ((LocalDateTime) date).getMinute(), ((LocalDateTime) date).getSecond());
			c.setTimeZone(TimeZone.getDefault());
			return c.getTime();
		}
		if (date instanceof ZonedDateTime) {
			return java.util.Date.from(((ZonedDateTime) date).toInstant());
		}
		if (date instanceof Instant) {
			return java.util.Date.from((Instant) date);
		}

		throw new UnsupportedOperationException("Don't know hot to convert " + date.getClass().getName() + " to java.util.Date");
	}

	/**
	 * Creates an {@link Instant} from {@code java.util.Date} or it's subclasses. Null-safe.
	 */
	public static Instant asInstant(Date date) {
		if (date == null) {
			return null;
		} else {
			return Instant.ofEpochMilli(date.getTime());
		}
	}

	/**
	 * Calls {@link #asZonedDateTime(Date, ZoneId)} with the system default time zone.
	 */
	public static ZonedDateTime asZonedDateTime(Date date) {
		return asZonedDateTime(date, ZoneId.systemDefault());
	}

	/**
	 * Creates {@link ZonedDateTime} from {@code java.util.Date} or it's subclasses. Null-safe.
	 */
	public static ZonedDateTime asZonedDateTime(Date date, ZoneId zone) {
		if (date == null) {
			return null;
		} else {
			return asInstant(date).atZone(zone);
		}
	}

}
