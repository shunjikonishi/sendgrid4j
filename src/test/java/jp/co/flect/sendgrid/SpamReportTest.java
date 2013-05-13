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

import jp.co.flect.sendgrid.model.SpamReport;
import jp.co.flect.sendgrid.model.WebMail;

import static jp.co.flect.sendgrid.SendGridTest.USERNAME;
import static jp.co.flect.sendgrid.SendGridTest.PASSWORD;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_FROM;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_TO;

public class SpamReportTest {
	
	@Test
	public void get() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		SpamReport.Get request = new SpamReport.Get();
		List<SpamReport> list = client.getSpamReports(request);
		assertTrue(list.size() == 0);
	}
	
	@Test
	public void delete() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		SpamReport.Delete request = new SpamReport.Delete("hoge@flect.co.jp");
		try {
			client.deleteSpamReports(request);
			fail();
		} catch (SendGridException e) {
			assertTrue(e.getMessage().indexOf("not exist") != -1);
		}
	}
	
}
