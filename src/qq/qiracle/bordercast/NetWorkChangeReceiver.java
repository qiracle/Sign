//======================================================================
 //
 //        Copyright (C) 2016   
 //        All rights reserved
 //
 //        filename :NetWorkChangeReceiver
 //        
 //
 //        created by Qiangqiang Jinag in  2016.04
 //        https://github.com/qiracle
 //		   qiracle@foxmail.com
 //
 //======================================================================
package qq.qiracle.bordercast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetWorkChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		
		if(ni ==null||!ni.isAvailable()){
			Toast.makeText(context,"当前无网络连接,请检查网络设置" , Toast.LENGTH_SHORT).show();
		}
	}

}
