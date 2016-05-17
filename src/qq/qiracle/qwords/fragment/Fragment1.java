package qq.qiracle.qwords.fragment;
/*Copyright (C) 2016.4
 * 
 *author:Qiangqiang Jiang
 * 
 */
import com.ericssonlabs.StudentMainActivity;
import com.ericssonlabs.TeacherMainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import qq.qiracle.qwords.R;
import qq.qiracle.qwords.utils.Util;
import qq.qiracle.userservice.ServiceRulesException;
import qq.qiracle.userservice.UserService;
import qq.qiracle.userservice.UserServiceImpl;

public class Fragment1 extends Fragment {

	//private static final int FLAG_REGISTER_SUCCESS = 1;

	//private static final String MSG_REGISTER_ERROR = "ע�����";
	//private static final String MSG_REGISTER_SUCCESS = "ע��ɹ���";
	public static final String MSG_LOGIN_FAILED = "��¼ʧ��";
	public static final String MSG_SERVER_ERROR = "�������������";
	public static final String MSG_REQUEST_TIMEOUT = "�����������ʱ��";
	public static final String MSG_RESPONSE_TIMEOUT = "��������Ӧ��ʱ��";

	View view;
	SharedPreferences sp;
	
	private Button bt;

	private EditText et_login_user;
	private EditText et_login_pwd;
	private CheckBox cb_ischeck;

	private Drawable mIconPerson;
	private Drawable mIconLock;
	private ImageView loginImage;
	private TextView topText;

	private RadioGroup rg_group;
	private UserService userService = new UserServiceImpl();

	@SuppressWarnings("deprecation")
	private void init() {
		topText = (TextView) view.findViewById(R.id.topname);
		topText.setTextColor(Color.MAGENTA);
		topText.setTextSize(24.0f);
		topText.setTypeface(Typeface.MONOSPACE, Typeface.BOLD_ITALIC);
		// ʹ��TextPaint�ķ¡����塱����setFakeBoldTextΪtrue��Ŀǰ���޷�֧�ַ¡�б�塱����
	

		mIconPerson = getResources().getDrawable(R.drawable.txt_person_icon);
		mIconPerson.setBounds(5, 1, 60, 50);
		mIconLock = getResources().getDrawable(R.drawable.txt_lock_icon);
		mIconLock.setBounds(5, 1, 60, 50);
		loginImage = (ImageView) view.findViewById(R.id.loginImage);
		loginImage.setBackgroundDrawable(new BitmapDrawable(Util.toRoundBitmap(getActivity(), "qiandao.jpg")));
		loginImage.getBackground().setAlpha(0);
		loginImage.setImageBitmap(Util.toRoundBitmap(getActivity(), "qiandao.jpg"));

		bt = (Button) view.findViewById(R.id.login);
		et_login_user = (EditText) view.findViewById(R.id.et_login_user);
		et_login_user.setCompoundDrawables(mIconPerson, null, null, null);
		et_login_pwd = (EditText) view.findViewById(R.id.et_login_pwd);
		et_login_pwd.setCompoundDrawables(mIconLock, null, null, null);
		cb_ischeck = (CheckBox) view.findViewById(R.id.cb_ischeck);
		
		
		rg_group = (RadioGroup)view.findViewById(R.id.radioGroup1);

	}

	private void rememberPwd() {
		// ��config.xml���ȡѧ������
		sp = getActivity().getSharedPreferences("config", 0);
		String name = sp.getString("name", "");
		String pwd = sp.getString("pwd", "");
		String isChecked = sp.getString("check", "false");

		et_login_user.setText(name);
		et_login_pwd.setText(pwd);
		if ("true".equals(isChecked)) {
			cb_ischeck.setChecked(true);
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.login, null);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);

		init();

		rememberPwd();

		bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				final String name = et_login_user.getText().toString().trim();
				final String pwd = et_login_pwd.getText().toString().trim();

				if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
					Toast.makeText(getActivity(), "�û��������벻��Ϊ��", Toast.LENGTH_SHORT).show();
				} else {

					Thread thread = new Thread(new Runnable() {

						@Override
						public void run() {
							boolean state = false;
							try {
							
								int radioButtonId = rg_group.getCheckedRadioButtonId();
								int type = 0;
								switch(radioButtonId){
								case R.id.rb_student:
									type = 1;
									state = userService.userLogin(name, pwd,type);
									if (state == true) {

								
										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(getActivity(), "����ɹ�", Toast.LENGTH_SHORT).show();
											}
										});
										/*******
										 * ����ִ�е���ɹ��������߼� -
										 ***********************/
										
										
										
										Intent intent = new Intent(getActivity(), StudentMainActivity.class);
										intent.putExtra("Username", name);
										
										
										getActivity().setResult(0,intent);
										
										startActivity(intent);

									}else{
										
										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(getActivity(), "ѧ�Ż��������", Toast.LENGTH_SHORT).show();
											}
										});
									}
									
									break;
									
								case R.id.rb_teacher:
									
									
									
									
									
									type = 2;
									state = userService.userLogin(name, pwd,type);
									if (state == true) {

								
										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(getActivity(), "����ɹ�", Toast.LENGTH_SHORT).show();
											}
										});
										/*******
										 * ����ִ�е���ɹ��������߼� -
										 ***********************/
										
										
										
										Intent intent = new Intent(getActivity(), TeacherMainActivity.class);
										intent.putExtra("TeacherNum", name);
										
										
										getActivity().setResult(10,intent);
								
										startActivity(intent);

									}else{
										
										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(getActivity(), "�̹��Ż��������", Toast.LENGTH_SHORT).show();
											}
										});
									}
							
									
									break;
									
								
								}
								
							
								
								
							}catch(final ServiceRulesException e) {
								
								getActivity().runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
									}
								});
								
							}
							
							
							catch (Exception e) {

								e.printStackTrace();
							}

						}

					});
					thread.start();

					/*********************************************/
					if (cb_ischeck.isChecked()) {
						Editor edit = sp.edit();
						edit.putString("name", name);
						edit.putString("pwd", pwd);
						edit.putString("check", "true");
						edit.commit();

					} else {
						Editor edit = sp.edit();
						edit.putString("name", "");
						edit.putString("pwd", "");
						edit.putString("check", "false");
						edit.commit();

					}

				}

			}

		});

		return view;

	}

}
