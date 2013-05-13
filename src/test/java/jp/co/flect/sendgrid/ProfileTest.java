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

import jp.co.flect.sendgrid.model.Profile;
import jp.co.flect.sendgrid.model.WebMail;

import static jp.co.flect.sendgrid.SendGridTest.USERNAME;
import static jp.co.flect.sendgrid.SendGridTest.PASSWORD;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_FROM;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_TO;
import static jp.co.flect.sendgrid.SendGridTest.BOUNCE_FROM;
import static jp.co.flect.sendgrid.SendGridTest.BOUNCE_TO;

public class ProfileTest {
	
	@Test
	public void getAndSet() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		Profile profile = client.getProfile();
		
		String username = profile.getUserName();
		String email = profile.getEmail();
		
		String firstName = profile.getFirstName();
		String lastName = profile.getLastName();
		String addr = profile.getAddress();
		String addr2 = profile.getAddress2();
		String city = profile.getCity();
		String state = profile.getState();
		String zip = profile.getZip();
		String country = profile.getCountry();
		String phone = profile.getPhone();
		String webSite = profile.getWebSite();
		
		assertTrue(profile.isActive());
		assertTrue(profile.isWebSiteAccess());
		
		assertNotNull(username);
		assertNotNull(email);
		assertNotNull(firstName);
		assertNotNull(lastName);
		assertNotNull(addr);
		assertNotNull(addr2);
		assertNotNull(city);
		assertNotNull(state);
		assertNotNull(zip);
		assertNotNull(country);
		assertNotNull(phone);
		assertNotNull(webSite);
		
		profile.setFirstName("S" + firstName);
		profile.setLastName("S" + lastName);
		profile.setAddress("S" + addr);
		profile.setAddress2("S" + addr2);
		profile.setCity("S" + city);
		profile.setState("S" + state);
		profile.setZip("S" + zip);
		profile.setCountry("S" + country);
		profile.setPhone("S" + phone);
		profile.setWebSite(webSite + "S");
		
		client.setProfile(profile);
		profile = client.getProfile();
		
		assertEquals("S" + firstName, profile.getFirstName());
		assertEquals("S" + lastName, profile.getLastName());
		assertEquals("S" + addr, profile.getAddress());
		assertEquals("S" + addr2, profile.getAddress2());
		assertEquals("S" + city, profile.getCity());
		assertEquals("S" + state, profile.getState());
		assertEquals("S" + zip, profile.getZip());
		assertEquals("S" + country, profile.getCountry());
		assertEquals("S" + phone, profile.getPhone());
		assertEquals(webSite + "S", profile.getWebSite());
		
		profile.setFirstName(firstName);
		profile.setLastName(lastName);
		profile.setAddress(addr);
		profile.setAddress2(addr2);
		profile.setCity(city);
		profile.setState(state);
		profile.setZip(zip);
		profile.setCountry(country);
		profile.setPhone(phone);
		profile.setWebSite(webSite);
		client.setProfile(profile);
	}
	
	//@Test
	//Can not execute with heroku account
	public void password() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		String newPass = "abcde12345";
		client.setPassword(newPass);
		client.setPassword(PASSWORD);
	}
	
	//@Test
	//Can not execute with heroku account
	public void username() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		String newUser = "abcde12345@flect.co.jp";
		client.setUserName(newUser);
		client.setUserName(USERNAME);
	}
	
	//@Test
	//Can not execute with heroku account
	public void email() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		String newMail = "abcde12345@flect.co.jp";
		client.setEmail(newMail);
		client.setEmail(USERNAME);
	}
}
