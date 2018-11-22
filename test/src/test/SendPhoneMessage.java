package test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @project_name PMSC_FINE  
 * @author sulijun
 * @date 2014年12月11日
 * @filename SendPhoneMessage.java 
 * @Description : 发送短信接口程序
 */
public class SendPhoneMessage {

    // 发送消息的参数名称
    private static final String SENDMESSAGEPAPRM = "param";
    // 发送消息的系统的名称
    private static final String SENDMESSAGEFROM = "paramFrom";
    // 发送消息的url
    private static final String SENDMESSAGEURL = "http://naquVM.local.weather.com.cn/websms/ws/sms/weatherpush/";
    //private static final String SENDMESSAGEURL = "http://10.14.85.163:8080/websms/ws/sms/weatherpush/";
    
    public static void main(String[] args) {
        // 电话号码,短信内容,系统描述
    	//18210469796
        boolean flag = sendMessage("18637197975", "在测试一次群发", "短信监控");
        System.out.println(flag);
    }
    
    /**
     * 
     * @author sulijun
     * @date 2014年12月7日
     * @param sendPhoneMessage 电话号码
     *                 message 短信内容
     *              systemName 系统描述
     * @return
     * @Description : 向手机发送短信方法
     * {"type":"01","data":[{"tel":"","msg":""},{"tel":"","msg":""},{"tel":"","msg":""}]}
     * {"type":"02","data":{"tel":["","",""],"msg":""}}
     * 
     */
    public static boolean sendMessage(String sendPhoneMessage, String message, String systemName){
        String[] arrayPhone = sendPhoneMessage.split(",");
        StringBuffer sendMessageBuffer = new StringBuffer();
        if(arrayPhone.length == 1){
            sendMessageBuffer.append("{\"tel\":\"");
            sendMessageBuffer.append(arrayPhone[0].trim());
            sendMessageBuffer.append("\",\"msg\":\"");
            sendMessageBuffer.append(message);
            sendMessageBuffer.append("\"}");
        }else if(arrayPhone.length > 1){
            sendMessageBuffer.append("{\"tel\":[");
            String phone = "";
            for (int i = 0; i < arrayPhone.length; i++) {
                if(phone.length() > 0){
                    phone+=",";
                }
                phone+="\""+arrayPhone[i]+"\"";
            }
            sendMessageBuffer.append(phone.trim());
            sendMessageBuffer.append("],\"msg\":\"");
            sendMessageBuffer.append(message);
            sendMessageBuffer.append("\"}");
        }
        
        try {
            //设置短信发送的参数
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String sendMessage = new String(sendMessageBuffer.toString().getBytes("utf8"), "iso-8859-1");
            String msmFrom = new String(systemName.toString().getBytes("utf8"), "iso-8859-1");
            if(arrayPhone.length==1){
            	params.add(new BasicNameValuePair(SENDMESSAGEPAPRM, "{\"type\":\"01\",\"data\":["+(sendMessage)+"]}"));
            }else{
            	params.add(new BasicNameValuePair(SENDMESSAGEPAPRM, "{\"type\":\"02\",\"data\":"+(sendMessage)+"}"));
            }
            
            params.add(new BasicNameValuePair(SENDMESSAGEFROM, msmFrom));
            String requestMessage = requestPostURL(SENDMESSAGEURL, params);
            if(requestMessage.indexOf("success") >= 0) {
                return true ;
            }
            //System.out.println(requestMessage);
            return false ;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false ;
    }
    /**
     * 
     * @author sulijun
     * @date 2014年12月11日
     * @param url
     * @param params
     * @return String
     * @throws ClientProtocolException
     * @throws IOException
     * @Description : 携带参数请求url
     */
    public static String requestPostURL(String url,List<NameValuePair> params) throws ClientProtocolException, IOException {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost(url);
        //如果参数不为空
        if(params != null && params.size() > 0){
            httppost.setEntity(new UrlEncodedFormEntity(params));
        }
        CloseableHttpResponse response = httpclient.execute(httppost);
        System.out.println(response.toString());
        HttpEntity entity = response.getEntity();
        String jsonStr = EntityUtils.toString(entity, "utf-8");
        //System.out.println(jsonStr);
        httppost.releaseConnection();
        return jsonStr ;
    }
}
