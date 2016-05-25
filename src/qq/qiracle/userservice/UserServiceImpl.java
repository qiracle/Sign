//======================================================================
 //
 //        Copyright (C) 2016   
 //        All rights reserved
 //
 //        filename :UserServiceImpl
 //        
 //
 //        created by Qiangqiang Jinag in  2016.04
 //        https://github.com/qiracle
 //		   qiracle@foxmail.com
 //
 //======================================================================


package qq.qiracle.userservice;

//10.105.2.44 10.105.2.44
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
import qq.qiracle.fragment.Fragment2;

public class UserServiceImpl implements UserService {

	@Override
	public boolean userLogin(String loginName, String loginPassword,int type) throws Exception {
		/**
		 * UserLogin{"LoginName":"loginName","LoginPassword":"loginPassword"}
		 * 
		 */
		
		
	HttpClient client = new DefaultHttpClient();
		String uri = "http://10.105.2.44:8080/AndroidServer/login";
		HttpPost post = new HttpPost(uri);
		/**
		 * 
		 * JSON数据的封装
		 */

		JSONObject object = new JSONObject();
		object.put("LoginName", loginName);
		object.put("LoginPassword", loginPassword);
		object.put("Type", type);

		BasicNameValuePair parameter = new BasicNameValuePair("UserLogin", object.toString());

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(parameter);

		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

		HttpResponse response = client.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode != HttpStatus.SC_OK) {

			throw new ServiceRulesException(Fragment1.MSG_SERVER_ERROR);
			
		}
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		if(result.equals("success")){
			return true;
		}else{
			
		return false;
		}

		
	}

	@Override
	public boolean modifyPwd(String loginName, String OldPassword, String NewPassword,int type) throws Exception {
		
		
		HttpClient client = new DefaultHttpClient();
		String uri = "http://10.105.2.44:8080/AndroidServer/modifyPwd";
		HttpPost post = new HttpPost(uri);
		JSONObject object = new JSONObject();
		object.put("LoginName", loginName);
		object.put("LoginOldPassword", OldPassword);
		object.put("LoginNewPassword", NewPassword);
		object.put("Type", type);
		BasicNameValuePair parameter = new BasicNameValuePair("ModifyPwd", object.toString());

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(parameter);

		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

		HttpResponse response = client.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {

			throw new ServiceRulesException(Fragment2.MSG_SERVER_ERROR);
			
		}
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		if(result.equals("ModSuccess")){
			return true;
		}else{
			
		return false;
		}
	}

	public boolean sign(String scanResult,String userName) throws Exception{
		/**
		 * 
		 * Sign{"ScanResult":"scanResult","UserName":"userName"}
		 * 
		 */
		HttpClient client = new DefaultHttpClient();
		String uri = "http://10.105.2.44:8080/AndroidServer/sign";
		HttpPost post = new HttpPost(uri);
		/**
		 * 
		 * JSON数据的封装
		 */

		JSONObject object = new JSONObject();
		object.put("ScanResult", scanResult);
		object.put("UserName", userName);
		
		BasicNameValuePair parameter = new BasicNameValuePair("Sign", object.toString());
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(parameter);

		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

		HttpResponse response = client.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode != HttpStatus.SC_OK) {

			throw new ServiceRulesException(Fragment1.MSG_SERVER_ERROR);
			
		}
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		System.out.println("---"+result);
		if(result.equals("SignSuccess")){
			return true;
		}else{
		return false;
		}
		
		
	}
	
	
	
}
