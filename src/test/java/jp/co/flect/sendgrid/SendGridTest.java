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
import jp.co.flect.sendgrid.model.Statistic;
import jp.co.flect.sendgrid.model.WebMail;

public class SendGridTest {
	
	public static String USERNAME;
	public static String PASSWORD;
	public static String MAIL_FROM;
	public static String MAIL_TO;
	
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
			
			assertNotNull(USERNAME);
			assertNotNull(PASSWORD);
			assertNotNull(MAIL_FROM);
			assertNotNull(MAIL_TO);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	@Test
	public void statistics() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		Statistic.Get request = new Statistic.Get();
		//request.setCategory("test");
		request.setDays(10);
		List<Statistic> list = client.getStatistics(request);
		assertTrue(list.size() > 0);
		for (Statistic stat : list) {
			System.out.println("Statistics: " + 
				stat.getDate() + ", " +
				"Category = " + stat.getCategory() + ", " +
				"Requests = " + stat.getRequests() + ", " +
				"Bounces = " + stat.getBounces() + ", " +
				"Clicks = " + stat.getClicks() + ", " +
				"Opens = " + stat.getOpens() + ", " +
				"SpamReports = " + stat.getSpamReports() + ", " +
				"UniqueClicks = " + stat.getUniqueClicks() + ", " +
				"UniqueOpens = " + stat.getUniqueOpens() + ", " +
				"Blocked = " + stat.getBlocked() + ", " +
				"Delivered = " + stat.getDelivered() + ", " +
				"Unsubscribes = " + stat.getUnsubscribes() + ", " +
				"InvalidEmails = " + stat.getInvalidEmails() + ", " +
				"RepeatUnsubscribes = " + stat.getRepeatUnsubscribes() + ", " +
				"SpamDrops = " + stat.getSpamDrops() + ", " +
				"RepeatBounces = " + stat.getRepeatBounces() + ", " +
				"RepeatSpamReports = " + stat.getRepeatSpamReports() + ", " +
				"");
		}
	}
	
	@Test
	public void categoryList() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		List<String> list = client.getCategoryList();
		assertTrue(list.size() > 0);
		
		boolean bTest = false;
		for (String s : list) {
			if (s.equals("test")) {
				bTest = true;
			}
		}
		assertTrue(bTest);
	}
	
}
