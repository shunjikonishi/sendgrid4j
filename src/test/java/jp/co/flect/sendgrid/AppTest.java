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

import jp.co.flect.sendgrid.model.App;

import static jp.co.flect.sendgrid.SendGridTest.USERNAME;
import static jp.co.flect.sendgrid.SendGridTest.PASSWORD;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_FROM;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_TO;
import static jp.co.flect.sendgrid.SendGridTest.BOUNCE_FROM;
import static jp.co.flect.sendgrid.SendGridTest.BOUNCE_TO;

public class AppTest {
	
	@Test
	public void getAvailableApps() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		List<App> list = client.getAvailableApps();
		boolean b = false;
		for (App app : list) {
			if ("dkim".equals(app.getName())) {
				b = true;
				assertEquals("DKIM", app.getTitle());
				assertTrue(app.isActivated());
				assertNotNull(app.getDescription());
			}
		}
		assertTrue(b);
	}
	
	@Test
	public void activate() throws Exception {
		final String g = "gravatar";
		
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		assertFalse(isActivated(client, g));
		
		client.activateApp(g);
		assertTrue(isActivated(client, g));
		
		client.deactivateApp(g);
		assertFalse(isActivated(client, g));
	}
	
	private boolean isActivated(SendGridClient client, String name) throws Exception {
		List<App> list = client.getAvailableApps();
		Boolean b = null;
		for (App app : list) {
			if (name.equals(app.getName())) {
				b = app.isActivated();;
			}
		}
		assertNotNull(b);
		return b;
	}
	
}
