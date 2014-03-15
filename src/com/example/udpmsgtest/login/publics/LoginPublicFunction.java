package com.example.udpmsgtest.login.publics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.util.Log;

public class LoginPublicFunction {

	private String tag = "LoginPublicFunction";
	private Context context = null;
	public LoginPublicFunction(){
		
	}
	public LoginPublicFunction(Context con){
		this.context = con;
	}
	/*@author:DerekXie
	 * 函数定义：判断文件夹是否存在，如果不存在则创建文件夹
	 * 参数定义：
	 * 		filePath：文件地址
	 * 返回数据定义：
	 * 		isExist：默认为0，表示创建的新文件；
	 * 					如果为1，表示文件一直存在;
	 * 					如果为2，表示创建失败;
	 * 					如果为-1，表示传入的文件地址为空;
	 * */
	public int isFolderExist(String folderPath){
		int isExist = 0;
		if (folderPath != null && !folderPath.equals("")) {
			File file = new File(folderPath);
			if (!file.exists()) {
				try {
					file.mkdirs();
				} catch (Exception e) {
					isExist = 2;
				}
			}else{
				isExist = 1;
			}
		}else{
			isExist = -1;
		}
		return isExist;
	}
	/*@author:DerekXie
	 * 函数定义：判断文件是否存在
	 * 参数定义：
	 * 		filePath：文件地址
	 * 返回数据定义：
	 * 		isExist：默认为0，无定义；
	 * 					如果为1，表示文件一直存在;
	 * 					如果为2，表示文件不存在;
	 * 					如果为-1，表示传入的文件地址为空;
	 * */
	public int isFileExist(String filePath){
		int isExist = 0;
		if (filePath != null && !filePath.equals("")) {
			File file = new File(filePath);
			if (file.exists()) {
				isExist = 1;
			}else{
				isExist = 2;
			}
		}else{
			isExist = -1;
		}
		return isExist;
	}
	/*@author:DerekXie
	 * 函数定义：判断是否存在外置存储卡
	 * 参数定义：无参数
	 * 返回数据：bool值，true表示存在，否则表示不存在
	 * 
	 * */
	public boolean isSdCardExist(){
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	/*@author:DerekXie
	 * 函数定义：删除指定目录的文件
	 * 参数定义：
	 * fileName：文件地址
	 * 返回数据：bool值，true表示成功，否则表示失败
	 * 
	 * */
	public boolean isDeleteFile(String filePath){
		File file = new File(filePath);
		if (file.exists()) {
			if (file.delete()) {
				return true;
			}
		}
		return false;
	}
	/*@author:DerekXie
	 * 函数定义：读取指定的文件内容
	 * 参数定义：
	 * fileName：文件地址
	 * 返回数据：
	 * fileContent:文件内容
	 * 
	 * */
	public String readFile(String filePath){
		String fileContent = "";
		int isFileExist = isFileExist(filePath);
		if (isFileExist == 1) {
			File file = new File(filePath);
			try {
				InputStream is = new FileInputStream(file);
				if (is != null) {
					InputStreamReader isR;
					try {
						isR = new InputStreamReader(is,"UTF-8");
						BufferedReader br = new BufferedReader(isR);
						String line = "";
						//分行读取数据
						while ((line = br.readLine()) != null) {
							fileContent = fileContent + line;
						}
						is.close();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			Log.i(tag, "readFile;The file of 【 "+filePath+" 】 is not exist!");
		}
		Log.i(tag, "readFile;fileContent:" + fileContent);
		return fileContent;
	}
}
