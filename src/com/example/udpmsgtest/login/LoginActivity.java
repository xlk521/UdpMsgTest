package com.example.udpmsgtest.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.udpmsgtest.BaseActivity;
import com.example.udpmsgtest.R;
import com.example.udpmsgtest.R.layout;
import com.example.udpmsgtest.R.menu;
import com.example.udpmsgtest.login.entity.CrewEntity;
import com.example.udpmsgtest.login.initdata.LoginInitDataFunction;
import com.example.udpmsgtest.login.publics.LoginContent;
import com.example.udpmsgtest.server.InitServer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

	private String tag = "LoginActivity";
	private Button loginOk = null;
	private EditText editUserName = null;
	private EditText editPassword = null;
	private String userName = "";
	private String password = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initData();
		btnClick();
	}
	/*@author:DerekXie
	 * 函数定义：初始化数据
	 * 参数定义：无参数
	 * */
	public void initData(){
		initFileData();
		loginOk = (Button)this.findViewById(R.id.loginOk);
		editUserName = (EditText)this.findViewById(R.id.editUserName);
		editPassword = (EditText)this.findViewById(R.id.editPassword);
	}
	/*@author:DerekXie
	 * 函数定义：登录按钮事件响应
	 * 参数定义：无参数
	 * */
	public void btnClick(){
		loginOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				userName = editUserName.getText().toString();
				password = editPassword.getText().toString();
				List<CrewEntity> crewList = null;
				if (userName != null && !userName.equals("")) {
					if (password != null && !password.equals("")) {
						try {
							InitServer<CrewEntity> server = new InitServer<CrewEntity>(getApplicationContext(), CrewEntity.class);
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("E_ID", userName);
							map.put("Password", password);
							crewList = server.getClientInfoList(map);
							if (crewList != null && crewList.size() > 0) {
								CrewEntity crewEntity = crewList.get(0);
								if (crewEntity != null) {
									LoginContent.crewEntity = crewEntity;
									//将信息存储到简易数据库中
									SharedPreferences sharedPrefence = getSharedPreferences(LoginContent.LoginSharedPrefence, 0);
									sharedPrefence.edit().putString(LoginContent.Eid, crewEntity.getE_ID()).commit();
									handler.sendEmptyMessage(LoginContent.LoginOk);
								}else{
									handler.sendEmptyMessage(LoginContent.LoginOk);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						Toast.makeText(LoginActivity.this, "Password is null!", 2000).show();
					}
				}else{
					Toast.makeText(LoginActivity.this, "UserName is null!", 2000).show();
				}
			}
		});
	}
	/*@author:DerekXie
	 * 函数定义：初始化数据库文件
	 * 参数定义：无参数
	 * */
	public void initFileData(){
		LoginInitDataFunction lidf = new LoginInitDataFunction(LoginActivity.this);
		boolean isFileDeal1 = lidf.parseJcBureau(LoginContent.bureauFilePath);
		boolean isFileDeal2 = lidf.parseJcDept(LoginContent.JcDeptFilePath);
		boolean isFileDeal3 = lidf.parseJcEmployee(LoginContent.JcEmployeeFilePath);
		boolean isFileDeal4 = lidf.parseJcGroup(LoginContent.JcGroupFilePath);
		boolean isFileDeal5 = lidf.parseJcTeam(LoginContent.JcTeamFilePath);
		boolean isFileDeal6 = lidf.parseJcTime(LoginContent.JcTimeFilePath);
		if (!isFileDeal1){
			Toast.makeText(LoginActivity.this, "JcBureau文件解析失败！", 2000).show();
		}
		if (!isFileDeal2){
			Toast.makeText(LoginActivity.this, "JcDept文件解析失败！", 2000).show();
		}
		if (!isFileDeal3){
			Toast.makeText(LoginActivity.this, "JcEmployee文件解析失败！", 2000).show();
		}
		if (!isFileDeal4){
			Toast.makeText(LoginActivity.this, "JcGroup文件解析失败！", 2000).show();
		}
		if (!isFileDeal5){
			Toast.makeText(LoginActivity.this, "JcTeam文件解析失败！", 2000).show();
		}
		if (!isFileDeal6){
			Toast.makeText(LoginActivity.this, "JcTime文件解析失败！", 2000).show();
		}
	}
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LoginContent.LoginOk:
				Log.i(tag, "handler;LoginOk;");
				Toast.makeText(getApplicationContext(), "LoginOk", 2000).show();
				break;
			case LoginContent.LoginNo:
				Log.i(tag, "handler;LoginNo;");
				Toast.makeText(getApplicationContext(), "LoginNo", 2000).show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
}
