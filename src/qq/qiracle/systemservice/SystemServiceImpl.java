
//======================================================================
 //
 //        Copyright (C) 2016   
 //        All rights reserved
 //
 //        filename :SystemServiceImpl
 //        
 //
 //        created by Qiangqiang Jinag in  2016.04
 //        https://github.com/qiracle
 //		   qiracle@foxmail.com
 //
 //======================================================================
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

import qq.qiracle.fragment.Fragment1;
import qq.qiracle.fragment.Fragment11;
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

			throw new ServiceRulesException(Fragment1.MSG_SERVER_ERROR);
			
		}
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		
//		JSONObject jsonobject= new JSONObject(result);
//		String sessionid = jsonobject.getString("sessionid");
		
		System.out.println("---"+result);
		if(result.equals("isOk")){
			return true;
		}else{
			
		return false;
		}
		
		
	
	}

}
