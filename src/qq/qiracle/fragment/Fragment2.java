
//======================================================================
 //
 //        Copyright (C) 2016   
 //        All rights reserved
 //
 //        filename :Fragment2
 //        
 //
 //        created by Qiangqiang Jinag in  2016.04
 //        https://github.com/qiracle
 //		   qiracle@foxmail.com
 //
 //======================================================================

package qq.qiracle.fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import qq.qiracle.qwords.R;
import qq.qiracle.userservice.ServiceRulesException;
import qq.qiracle.userservice.UserService;
import qq.qiracle.userservice.UserServiceImpl;

public class Fragment2 extends Fragment {
	
	public static final String MSG_SERVER_ERROR = "����������";
	
	View view;
	// private MyDatabaseHelper dbHelper;
	private EditText et_reg_user;
	private EditText et_pwd;
	private EditText et_reg_pwd;
	private EditText et_reg_pwds;
	private Button reg;
	private Button clear;
	private Drawable mIconPerson;
	private Drawable mIconLock;
	private RadioGroup rg_group;
	private UserService userService = new UserServiceImpl();

	private void init() {
		mIconPerson = getResources().getDrawable(R.drawable.txt_person_icon);
		mIconPerson.setBounds(5, 1, 60, 50);
		mIconLock = getResources().getDrawable(R.drawable.txt_lock_icon);
		mIconLock.setBounds(5, 1, 60, 50);
		et_reg_user = (EditText) view.findViewById(R.id.et_reg_user);
		et_reg_user.setCompoundDrawables(mIconPerson, null, null, null);
		et_pwd = (EditText) view.findViewById(R.id.et_pwd);
		et_pwd.setCompoundDrawables(mIconLock, null, null, null);
		et_reg_pwd = (EditText) view.findViewById(R.id.et_reg_pwd);
		et_reg_pwd.setCompoundDrawables(mIconLock, null, null, null);
		et_reg_pwds = (EditText) view.findViewById(R.id.et_reg_pwds);
		et_reg_pwds.setCompoundDrawables(mIconLock, null, null, null);

		reg = (Button) view.findViewById(R.id.reg);
		clear = (Button) view.findViewById(R.id.clear);
		rg_group = (RadioGroup) view.findViewById(R.id.radioGroup2);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.register, null);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);

		
		init();

		
		clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				et_reg_user.setText("");
				et_pwd.setText("");
				et_reg_pwd.setText("");
				et_reg_pwds.setText("");
				
			}
		});

		reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String username = et_reg_user.getText().toString().trim();
				final String password = et_pwd.getText().toString().trim();
				final String newpwd = et_reg_pwd.getText().toString().trim();
				String newpwds = et_reg_pwds.getText().toString().trim();

				if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
					Toast.makeText(getActivity(), "�û��������벻��Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}

				else if (TextUtils.isEmpty(newpwd) || TextUtils.isEmpty(newpwds)) {
					Toast.makeText(getActivity(), "�������ȷ�����벻��Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				} else if (!newpwd.equals(newpwds)) {

					Toast.makeText(getActivity(), "�����ȷ�����벻һ�£�����������", Toast.LENGTH_SHORT).show();

					return;
				}

				else {

					Thread thread = new Thread(new Runnable() {

						@Override
						public void run() {
							boolean state = false;
							try {
								int radioButtonId = rg_group.getCheckedRadioButtonId();
								int type = 0;
								switch(radioButtonId){
								
							
								case R.id.rb_mod_student:
									type = 1;
									state = userService.modifyPwd(username, password, newpwd,type);
									if (state == true) {

										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(getActivity(), "�����޸ĳɹ�", Toast.LENGTH_SHORT).show();
											}
										});
										

									}else{
										
										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(getActivity(), "�û�����ԭ�������", Toast.LENGTH_SHORT).show();
											}
										});
									}
									
									break;
									
								case R.id.rb_mod_teacher:
									type = 2;
									state = userService.modifyPwd(username, password, newpwd,type);
									if (state == true) {

										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(getActivity(), "�����޸ĳɹ�", Toast.LENGTH_SHORT).show();
											}
										});
										

									}else{
										
										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(getActivity(), "�û�����ԭ�������", Toast.LENGTH_SHORT).show();
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
				}

			}
		});

		return view;
	}



}
