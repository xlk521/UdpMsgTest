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
	/*author：DerekXie
	 * 函数功能：如果不存在则创建数据库，否则升级数据库
	 * 参数定义：无参
	 * 
	 * */
	public void deleteDB(){
		File dbFile = new File(Constant.DB_PATH);
		if (dbFile.exists()) {
			dbFile.delete();
		}
	}
	/*author：DerekXie
	 * 函数功能：如果不存在则创建数据库，否则升级数据库
	 * 参数定义：无参
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
        	//升级db
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
	/*author：DerekXie
	 * 函数功能：获取指定文件夹下的对应后缀的文件列表
	 * 参数定义
	 * Path：搜索目录
	 * Extension：扩展名
	 * IsIterative：是否进入子文件夹
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
	        	//判断扩展名
	            if(f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension)){
	                String filePath = f.getPath();
	                Log.i(tag, "GetFiles;filePath:"+filePath);
	                String fileName = f.getName();
	                String fileNameNew = AnalyseFileName(fileName);
	                String fileNumNew = AnalyseFileNum(fileName);
	                Log.i(tag, "GetFiles;fileName:"+fileNameNew+"  num:"+fileNumNew);
	                //将数据存入数据库
	                SaveFilesContent(fileClass,fileNumNew,fileNameNew,filePath);
	            }
	        }else if (f.isDirectory() && f.getPath().indexOf("/.") == -1)  //忽略点文件（隐藏文件/文件夹）
	        {//此处是扫描子文件夹
//	        	GetFiles(f.getPath(), Extension, IsIterative);
	        }
	    }
	}
	/*author：DerekXie
	 * 函数功能：将获取的文件信息保存到数据库
	 * 参数定义
	 *FileName：要保存的文件名
	 *FilePath：要保存的文件地址
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
	/*author：DerekXie
	 * 函数功能：解析文件名，获取文件名
	 * 参数定义
	 *fileName：需要解析的文件名
	 * */
	public String AnalyseFileNum(String fileName){
		//文件名.001.doc
		String analysedNum = "";
		int strNum = fileName.length();
		int pointNum = fileName.indexOf(Constant.Point);//"."
		int pointDNum = fileName.indexOf(Constant.PointD);//".d"
		if (pointNum == -1 || pointDNum == -1) {//表示不存在
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
	/*author：DerekXie
	 * 函数功能：解析文件名
	 * 参数定义
	 *fileName：需要解析的文件名
	 * */
	public String AnalyseFileName(String fileName){
		//文件名.001.doc
		String analysedName = "";
		int strNum = fileName.length();
		int pointNum = fileName.indexOf(Constant.Point);//"."
		int pointDNum = fileName.indexOf(Constant.PointD);//".d"
		if (pointNum == -1 || pointDNum == -1) {//表示不存在
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
	/*author：DerekXie
	 * 函数功能：扫描文件夹，将所有文件夹的数据存入数据库
	 * 				先删除再创建然后存入数据库
	 * 参数定义：
	 * 
	 * */
	public void saveAllFiles(){
		
	}
}
