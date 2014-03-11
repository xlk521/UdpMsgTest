package com.example.udpmsgtest;

import java.io.File;

import com.example.udpmsgtest.db.DatabaseHelper;
import com.example.udpmsgtest.entity.TkyDbTest;
import com.example.udpmsgtest.publicfunction.Constant;
import com.example.udpmsgtest.server.InitServer;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
public class DataFunction {

	private String tag = "";
	private Context contex = null;
	public DataFunction(Context con){
		this.contex = con;
	}
	/*author��DerekXie
	 * �������ܣ�����������򴴽����ݿ⣬�����������ݿ�
	 * �������壺�޲�
	 * 
	 * */
	public void deleteDB(){
		File dbFile = new File(Constant.DB_PATH);
		if (dbFile.exists()) {
			dbFile.delete();
		}
	}
	/*author��DerekXie
	 * �������ܣ�����������򴴽����ݿ⣬�����������ݿ�
	 * �������壺�޲�
	 * 
	 * */
	public void creatDB(){
		File dbFile = new File(Constant.DB_PATH);
		SharedPreferences preference = contex.getSharedPreferences("client_app_preference", 0);
        if (!dbFile.exists()) {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Constant.DB_PATH,null);
            DatabaseHelper orm = new DatabaseHelper(contex.getApplicationContext());
            orm.onCreate(db);
            orm.close();
            db.close();
            preference.edit().putInt("now_db_version", Constant.DB_VERSION).commit();
        }else{
        	//����db
        	int nowDBVersion = preference.getInt("now_db_version", 1); 
        	if(nowDBVersion < Constant.DB_VERSION){
	        	SQLiteDatabase db = SQLiteDatabase.openDatabase(Constant.DB_PATH,null,Constant.DB_VERSION);
	        	DatabaseHelper dbHelper = new DatabaseHelper(contex.getApplicationContext());
	        	dbHelper.onUpgrade(db, nowDBVersion,Constant.DB_VERSION);
	        	dbHelper.close();
	        	db.close();
	        	preference.edit().putInt("now_db_version", Constant.DB_VERSION).commit();
        	}
        }
	}
	/*author��DerekXie
	 * �������ܣ���ȡָ���ļ����µĶ�Ӧ��׺���ļ��б�
	 * ��������
	 * Path������Ŀ¼
	 * Extension����չ��
	 * IsIterative���Ƿ�������ļ���
	 * */
	public void GetFiles(String Path, String Extension, boolean IsIterative,String fileClass)  
	{
	    File[] files = new File(Path).listFiles();
	    
	    Log.i(tag, "GetFiles;files.length:"+files.length);
	    for (int i = 0; i < files.length; i++)
	    {
	        File f = files[i];
	        if(f.isDirectory()){}
	        if (f.isFile())
	        {
	        	//�ж���չ��
	            if(f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension)){
	                String filePath = f.getPath();
	                Log.i(tag, "GetFiles;filePath:"+filePath);
	                String fileName = f.getName();
	                String fileNameNew = AnalyseFileName(fileName);
	                String fileNumNew = AnalyseFileNum(fileName);
	                Log.i(tag, "GetFiles;fileName:"+fileNameNew+"  num:"+fileNumNew);
	                //�����ݴ������ݿ�
	                SaveFilesContent(fileClass,fileNumNew,fileNameNew,filePath);
	            }
	        }else if (f.isDirectory() && f.getPath().indexOf("/.") == -1)  //���Ե��ļ��������ļ�/�ļ��У�
	        {//�˴���ɨ�����ļ���
//	        	GetFiles(f.getPath(), Extension, IsIterative);
	        }
	    }
	}
	/*author��DerekXie
	 * �������ܣ�����ȡ���ļ���Ϣ���浽���ݿ�
	 * ��������
	 *FileName��Ҫ������ļ���
	 *FilePath��Ҫ������ļ���ַ
	 * */
	public void SaveFilesContent(String fileClass ,String id, String FileName,String FilePath){
		InitServer<TkyDbTest> initServer = new InitServer<TkyDbTest>(contex, TkyDbTest.class);
		TkyDbTest tkyDbTest = new TkyDbTest();
		tkyDbTest.setFileName(FileName);
		tkyDbTest.setFilePath(FilePath);
		tkyDbTest.setId(id);
		tkyDbTest.setFileClass(fileClass);
		initServer.insertTrainClient(tkyDbTest);
	}
	/*author��DerekXie
	 * �������ܣ������ļ�������ȡ�ļ���
	 * ��������
	 *fileName����Ҫ�������ļ���
	 * */
	public String AnalyseFileNum(String fileName){
		//�ļ���.001.doc
		String analysedNum = "";
		int strNum = fileName.length();
		int pointNum = fileName.indexOf(Constant.Point);//"."
		int pointDNum = fileName.indexOf(Constant.PointD);//".d"
		if (pointNum == -1 || pointDNum == -1) {//��ʾ������
			analysedNum = null;
		}else{
			if (pointNum == pointDNum) {
				analysedNum = null;
			}else{
				analysedNum = fileName.substring(pointNum+1, pointNum+4);
			}
		}
		return analysedNum;
	}
	/*author��DerekXie
	 * �������ܣ������ļ���
	 * ��������
	 *fileName����Ҫ�������ļ���
	 * */
	public String AnalyseFileName(String fileName){
		//�ļ���.001.doc
		String analysedName = "";
		int strNum = fileName.length();
		int pointNum = fileName.indexOf(Constant.Point);//"."
		int pointDNum = fileName.indexOf(Constant.PointD);//".d"
		if (pointNum == -1 || pointDNum == -1) {//��ʾ������
			analysedName = null;
		}else{
			if(pointNum == pointDNum){
				analysedName = null;
			}else{
				analysedName = fileName.substring(0, pointNum);
			}
		}
		return analysedName;
	}
	/*author��DerekXie
	 * �������ܣ�ɨ���ļ��У��������ļ��е����ݴ������ݿ�
	 * 				��ɾ���ٴ���Ȼ��������ݿ�
	 * �������壺
	 * 
	 * */
	public void saveAllFiles(){
		
	}
}
