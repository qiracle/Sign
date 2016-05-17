package qq.qiracle.systemservice;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.ericssonlabs.StudentMainActivity;

import qq.qiracle.userservice.ServiceRulesException;

public class SystemServiceImpl implements SystemService{

	@Override
	public boolean setStringQrcode(String StringQrcode) throws Exception {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://10.105.2.44:8080/AndroidServer/receiveQrcode";
		HttpPost post = new HttpPost(uri);
		
		/**
		 * Qrcode{"StringQRcode":"StringQrcode"}
		 * 
		 */
		JSONObject object = new JSONObject();
		object.put("StringQRcode", StringQrcode);
		
	BasicNameValuePair parameter = new BasicNameValuePair("Qrcode", object.toString());
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(parameter);

		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

		HttpResponse response = client.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode != HttpStatus.SC_OK) {

			throw new ServiceRulesException(StudentMainActivity.MSG_SERVER_ERROR);
			
		}
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		System.out.println("---"+result);
		if(result.equals("isOk")){
			return true;
		}else{
			
		return false;
		}
		
		
	
	}

}
