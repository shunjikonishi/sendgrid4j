package jp.co.flect.sendgrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Date;
import java.util.Calendar;

public class SendGridTest {
	
	public static String USERNAME;
	public static String PASSWORD;
	public static String MAIL_FROM;
	public static String MAIL_TO;
	public static String BOUNCE_FROM;
	public static String BOUNCE_TO;
	
	static {
		try {
			Properties props = new Properties();
			
			InputStream is = new FileInputStream(new File("test.properties"));
			try {
				props.load(is);
			} finally {
				is.close();
			}
			USERNAME  = props.getProperty("SENDGRID_USERNAME");
			PASSWORD  = props.getProperty("SENDGRID_PASSWORD");
			MAIL_FROM = props.getProperty("MAIL_FROM");
			MAIL_TO   = props.getProperty("MAIL_TO");
			BOUNCE_FROM = props.getProperty("BOUNCE_FROM");
			BOUNCE_TO   = props.getProperty("BOUNCE_TO");
			
			assertNotNull(USERNAME);
			assertNotNull(PASSWORD);
			assertNotNull(MAIL_FROM);
			assertNotNull(MAIL_TO);
			assertNotNull(BOUNCE_FROM);
			assertNotNull(BOUNCE_TO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Date getDateBefore(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(0 - days, Calendar.DATE);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
