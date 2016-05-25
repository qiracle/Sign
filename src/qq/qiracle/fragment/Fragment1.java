
//======================================================================
//
//        Copyright (C) 2016   
//        All rights reserved
//
//        filename :Fragment1
//        
//
//        created by Qiangqiang Jinag in  2016.04
//        https://github.com/qiracle
//		   qiracle@foxmail.com
//
//======================================================================
package qq.qiracle.fragment;

import com.zxing.activity.CaptureActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import qq.qiracle.loginActivity.LoginActivity;

import qq.qiracle.qwords.R;
import qq.qiracle.userservice.ServiceRulesException;
import qq.qiracle.userservice.UserService;
import qq.qiracle.userservice.UserServiceImpl;

public class Fragment1 extends Fragment {
	View view;
	private TextView resultTextView;
	private TextView usertext;
	private Button btnExit;
	private static final int IMAGE_HALFWIDTH = 20;
	int[] pixels = new int[2 * IMAGE_HALFWIDTH * 2 * IMAGE_HALFWIDTH];

	private final int SCANER_CODE = 1;
	private String username;

	public static final String MSG_SERVER_ERROR = "请求服务器错误。";
	private UserService userservice = new UserServiceImpl();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.student_main, null);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);

		resultTextView = (TextView) view.findViewById(R.id.tv_scan_result);
		usertext = (TextView) view.findViewById(R.id.tv_user);
		btnExit = (Button) view.findViewById(R.id.btn_exit);

		btnExit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

				dialogBuilder.setTitle("注意！");
				dialogBuilder.setMessage("您确定要退出吗？");

				dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						Intent intent = new Intent(getActivity(), LoginActivity.class);

						startActivity(intent);
						getActivity().finish();
					}
				});
				dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

				dialogBuilder.show();

			}
		});

		Intent intent = getActivity().getIntent();
		username = intent.getStringExtra("Username");
		usertext.setText("您好，您的学号为" + username);

		Button scanBarCodeButton = (Button) view.findViewById(R.id.btn_scan_barcode);
		scanBarCodeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent openCameraIntent = new Intent(getActivity(), CaptureActivity.class);
				startActivityForResult(openCameraIntent, SCANER_CODE);
			}
		});

		return view;

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		getActivity();
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == SCANER_CODE) {
				Bundle bundle = data.getExtras();
				final String scanResult = bundle.getString("result");

				new Thread(new Runnable() {

					@Override
					public void run() {
						try {

							final boolean state = userservice.sign(scanResult, username);
							getActivity().runOnUiThread(new Runnable() {

								@Override
								public void run() {
									if (state) {
										resultTextView.setText("签到成功");
										// resultTextView.setText("签到成功"+scanResult+"--"+username);
										Toast.makeText(getActivity(), "签到成功", Toast.LENGTH_SHORT).show();

									} else {
										resultTextView.setText("签到失败");
										Toast.makeText(getActivity(), "签到失败", Toast.LENGTH_SHORT).show();
									}

								}
							});

						} catch (final ServiceRulesException e) {

							getActivity().runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
								}
							});

						} catch (Exception e) {

							e.printStackTrace();
						}

					}
				}).start();

			}

		}
	}
}