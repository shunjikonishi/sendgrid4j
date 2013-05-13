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
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import jp.co.flect.sendgrid.model.InvalidEmail;
import jp.co.flect.sendgrid.model.WebMail;

import static jp.co.flect.sendgrid.SendGridTest.USERNAME;
import static jp.co.flect.sendgrid.SendGridTest.PASSWORD;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_FROM;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_TO;

public class InvalidEmailTest {
	
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
	
	@Test
	public void delete() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		InvalidEmail.Delete request = new InvalidEmail.Delete("test@flect.co.jpx");
		client.deleteInvalidEmails(request);
		
		WebMail mail = new WebMail();
		mail.setFrom(MAIL_FROM);
		mail.setTo("test@flect.co.jpx");
		mail.setSubject("test");
		mail.setText("test");
		
		client.mail(mail);
	}
}
