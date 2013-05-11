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

import jp.co.flect.sendgrid.model.Block;

import static jp.co.flect.sendgrid.SendGridTest.USERNAME;
import static jp.co.flect.sendgrid.SendGridTest.PASSWORD;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_FROM;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_TO;

public class BlockTest {
	
	@Test
	public void invalidMails() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		Block.Get request = new Block.Get();
		List<Block> list = client.getBlocks(request);
		assertTrue(list.size() == 0);
	}
	
	@Test
	public void delete() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		Block.Delete request = new Block.Delete();
		request.setEmail("hoge@flect.co.jp");
		
		try {
			client.deleteBlocks(request);
			fail();
		} catch (SendGridException e) {
		}
	}
	
}
