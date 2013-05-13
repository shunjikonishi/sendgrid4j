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

import jp.co.flect.sendgrid.model.Unsubscribe;

import static jp.co.flect.sendgrid.SendGridTest.USERNAME;
import static jp.co.flect.sendgrid.SendGridTest.PASSWORD;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_FROM;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_TO;

public class UnsubscribeTest {
	
	@Test
	public void statistics() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		Unsubscribe.Get request = new Unsubscribe.Get();
		
		List<Unsubscribe> list = client.getUnsubscribes(request);
		assertEquals(1, list.size());
		assertEquals("test@flect.co.jp", list.get(0).getEmail());
		
		client.deleteUnsubscribes(new Unsubscribe.Delete());
		client.addUnsubscribes(new Unsubscribe.Add("test@flect.co.jp"));
	}
	
}
