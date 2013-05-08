package jp.co.flect.sendgrid.transport;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import jp.co.flect.sendgrid.SendGridException;
import jp.co.flect.sendgrid.model.WebMail;

public interface Transport {
	
	public String send(String url, Map<String, String[]> params) throws IOException, SendGridException;
	public void send(String url, WebMail mail, File... attachement) throws IOException, SendGridException;
	
	public ProxyInfo getProxyInfo();
	public void setProxyInfo(ProxyInfo proxy);
}

