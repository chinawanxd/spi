package test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class HttpClientUtil {
    
	//转发商家的接�?
	public static String sellerGetUrl(String decodeurl) throws UnsupportedEncodingException{
		//httpClient
	    HttpClient httpClient = new DefaultHttpClient();
	    
	    String temp="";
	    try{
	    	URL url = new URL(decodeurl);
	    	URI uri = new URI(url.getProtocol(), url.getHost()+":"+url.getPort(), url.getPath(), url.getQuery(), null);
	    	//logger.info("HttpClientUtil url:"+uri.toString());
	    	//get method
	    	HttpGet httpGet = new HttpGet(uri);
	    	HttpResponse response = null;
	        response = httpClient.execute(httpGet);
	        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			   //响应失败处理  
			   //temp=StringUtil.errMsg(ConstantCollection.CC1000);
		    }else{
		       HttpEntity entity = response.getEntity();
			   temp=EntityUtils.toString(entity,"UTF-8");
		    }
	        httpGet.releaseConnection();
	        httpClient.getConnectionManager().shutdown();
	    }catch (Exception e) {
			//temp=StringUtil.errMsg(ConstantCollection.CC1000);
	    }
		return temp;
	}
	
//	public static void main(String[] args) throws UnsupportedEncodingException, URISyntaxException, MalformedURLException {
//        URL url = new URL("http://api.weatherdt.com/common/?area=101010100&type=forecast|index|alarm|observe|air&key=02d61b01952f28eb547e49adb668bef5");
//        //URL url = new URL("http://way.weatherdt.com/tianyi/grid_fd_observe?serialNo=1000302&appkey=cd91b38e0e2cd472b013fe0c2432ff37");
//        URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
//		String thePath=uri.toString();
//		System.out.println(thePath);
//		System.out.println(sellerGetUrl("http://api.weatherdt.com/common/?area=101010100&type=forecast|index|alarm|observe|air&key=02d61b01952f28eb547e49adb668bef5"));
//	}
	
}
