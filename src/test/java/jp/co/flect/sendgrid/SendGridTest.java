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
import java.util.List;

import jp.co.flect.sendgrid.model.InvalidEmail;

public class SendGridTest {
	
	private static String USERNAME;
	private static String PASSWORD;
	@BeforeClass
	public static void setup() throws Exception {
		Properties props = new Properties();
		
		InputStream is = new FileInputStream(new File("test.properties"));
		try {
			props.load(is);
		} finally {
			is.close();
		}
		USERNAME = props.getProperty("SENDGRID_USERNAME");
		PASSWORD = props.getProperty("SENDGRID_PASSWORD");
		
		assertNotNull(USERNAME);
		assertNotNull(PASSWORD);
	}
	
	@Test
	public void invalidMails() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		InvalidEmail.Get request = new InvalidEmail.Get();
		List<InvalidEmail> list = client.getInvalidEmails(request);
		assertTrue(list.size() > 0);
		for (InvalidEmail mail : list) {
			System.out.println("InvalidEmail: " + mail.getEmail() + ", " + mail.getCreated() + ", " + mail.getReason());
		}
	}
	
	@Test
	public void limitError() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		InvalidEmail.Get request = new InvalidEmail.Get();
		request.setLimit(0);
		try {
			client.getInvalidEmails(request);
			fail();
		} catch (SendGridException e) {
			System.out.println("limitError:" + e.getMessage());
			System.out.println("limitError2:" + e.getAllMessages());
			assertTrue(e.getMessage().indexOf("Limit") > 0);
		}
	}
}
