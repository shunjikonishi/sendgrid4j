package jp.co.flect.sendgrid.transport;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import jp.co.flect.sendgrid.SendGridException;

public interface Transport {
	
	public String send(String url, Map<String, String[]> params, File... attachement) throws IOException, SendGridException;
	
	public ProxyInfo getProxyInfo();
	public void setProxyInfo(ProxyInfo proxy);
}

