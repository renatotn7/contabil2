package br.com.cvm.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.common.io.ByteSource;

public class ApacheHttpGet {
public ApacheHttpGet() {
	
}

public String getContent(String url) {
	String content = null;
	  CloseableHttpClient httpclient = HttpClients.createDefault();
      try {
          HttpGet httpGet = new HttpGet(url);
          CloseableHttpResponse response1 = httpclient.execute(httpGet);
        
          try {
              System.out.println(response1.getStatusLine());
           
              HttpEntity entity1 = response1.getEntity();
          
              ByteSource byteSource = new ByteSource() {
                  @Override
                  public InputStream openStream() throws IOException {
                      return entity1.getContent();
                  }
              };
           
             content = byteSource.asCharSource(Charsets.UTF_8).read();
              
              EntityUtils.consume(entity1);
          } finally {
              response1.close();
          }

      /*    HttpPost httpPost = new HttpPost("http://httpbin.org/post");
          List <NameValuePair> nvps = new ArrayList <NameValuePair>();
          nvps.add(new BasicNameValuePair("username", "vip"));
          nvps.add(new BasicNameValuePair("password", "secret"));
          httpPost.setEntity(new UrlEncodedFormEntity(nvps));
          CloseableHttpResponse response2 = httpclient.execute(httpPost);

          try {
              System.out.println(response2.getStatusLine());
              HttpEntity entity2 = response2.getEntity();
              // do something useful with the response body
              // and ensure it is fully consumed
              EntityUtils.consume(entity2);
          } finally {
              response2.close();
          }*/
      } catch (ClientProtocolException e) {
		// TODO Bloco catch gerado automaticamente
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Bloco catch gerado automaticamente
		e.printStackTrace();
	} finally {
          try {
			httpclient.close();
		} catch (IOException e) {
			// TODO Bloco catch gerado automaticamente
			e.printStackTrace();
		}
      }
      return content;
}
}
