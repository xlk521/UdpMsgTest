package com.example.udpmsgtest.login.publics;

import com.example.udpmsgtest.login.entity.CrewEntity;

public class LoginContent {

	public static CrewEntity crewEntity = null;
	public static final String LoginSharedPrefence = "LoginSharedPrefence";
	public static final String Eid = "Eid";
	public static final int LoginOk = 1;//表示登录成功
	public static final int LoginNo = 0;//表示登录失败
	//获取本机的sdcard的地址
	public static final String sdCardPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String baseFolder = sdCardPath + "/xlk/";
	public static final String initFolder = baseFolder + "KydUpload/";
	public static final String initFileZip = baseFolder + "KydUpload.zip";
	public static final String fileEnd = ".ini";
	public static final String bureauFilePath = initFolder + "JcBureau" + fileEnd;
	public static final String JcDeptFilePath = initFolder + "JcDept" + fileEnd;
	public static final String JcEmployeeFilePath = initFolder + "JcEmployee" + fileEnd;
	public static final String JcGroupFilePath = initFolder + "JcGroup" + fileEnd;
	public static final String JcTeamFilePath = initFolder + "JcTeam" + fileEnd;
	public static final String JcTimeFilePath = initFolder + "JcTime" + fileEnd;
}
