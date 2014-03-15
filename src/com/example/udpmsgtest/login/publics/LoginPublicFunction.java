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
	 * �������壺�ж��ļ����Ƿ���ڣ�����������򴴽��ļ���
	 * �������壺
	 * 		filePath���ļ���ַ
	 * �������ݶ��壺
	 * 		isExist��Ĭ��Ϊ0����ʾ���������ļ���
	 * 					���Ϊ1����ʾ�ļ�һֱ����;
	 * 					���Ϊ2����ʾ����ʧ��;
	 * 					���Ϊ-1����ʾ������ļ���ַΪ��;
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
	 * �������壺�ж��ļ��Ƿ����
	 * �������壺
	 * 		filePath���ļ���ַ
	 * �������ݶ��壺
	 * 		isExist��Ĭ��Ϊ0���޶��壻
	 * 					���Ϊ1����ʾ�ļ�һֱ����;
	 * 					���Ϊ2����ʾ�ļ�������;
	 * 					���Ϊ-1����ʾ������ļ���ַΪ��;
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
	 * �������壺�ж��Ƿ�������ô洢��
	 * �������壺�޲���
	 * �������ݣ�boolֵ��true��ʾ���ڣ������ʾ������
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
	 * �������壺ɾ��ָ��Ŀ¼���ļ�
	 * �������壺
	 * fileName���ļ���ַ
	 * �������ݣ�boolֵ��true��ʾ�ɹ��������ʾʧ��
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
	 * �������壺��ȡָ�����ļ�����
	 * �������壺
	 * fileName���ļ���ַ
	 * �������ݣ�
	 * fileContent:�ļ�����
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
						//���ж�ȡ����
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
			Log.i(tag, "readFile;The file of �� "+filePath+" �� is not exist!");
		}
		Log.i(tag, "readFile;fileContent:" + fileContent);
		return fileContent;
	}
}
