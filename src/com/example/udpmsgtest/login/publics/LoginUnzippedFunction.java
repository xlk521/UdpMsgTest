package com.example.udpmsgtest.login.publics;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import android.content.Context;
import android.util.Log;

public class LoginUnzippedFunction {

	private static String tag = "LoginUnzippedFunction";
	private Context context = null;
	public LoginUnzippedFunction(){
		
	}
	public LoginUnzippedFunction(Context con){
		this.context = con;
	}
	/*@author:DerekXie
	 * �������壺��ȡ��Ҫ��ѹ���ļ������Ҷ�����н�ѹ
	 * �������壺
	 * 		filePath����Ҫ��ѹ���ļ���ַ
	 * 		folderPath����Ҫ��Ž�ѹ�������ݵ��ļ��е�ַ
	 * �������ݶ��壺
	 * 		isOk���Ƿ��ѹ�ɹ���
	 * 				0��ʾʧ�ܣ�
	 * 				1��ʾ�ɹ���
	 * 				-1��ʾ�ļ������ڣ�
	 * 				-2��ʾ�ļ��в�����
	 * 				-3��ʾû�д�����Ч��Context
	 * 
	 * */
	public int getUnzipFile(String folderPath, String filePath){
		int isOk = 0;
		if (context != null) {
			LoginPublicFunction lpf = new LoginPublicFunction(context);
			int isFolder = lpf.isFolderExist(folderPath);
			if (isFolder == 1 || isFolder == 0) {
				int isFile = lpf.isFileExist(filePath);
				if (isFile == 1) {
					File file = new File(filePath);
					try {
						int isUnzipped = UnzippedFile(file, folderPath);
						if (isUnzipped == 1) {
							isOk = 1;
							return isOk;
						}
					} catch (ZipException e) {
						e.printStackTrace();
						return isOk;
					} catch (IOException e) {
						e.printStackTrace();
						return isOk;
					}
				}else{
					isOk = -1;
					return isOk;
				}
			}else{
				isOk = -2;
				return isOk;
			}
		}else{
			isOk = -3;
			return isOk;
		}
		return isOk;
	}
	/*@author:DerekXie
	 * �������壺��ѹ���ļ�
	 * �������壺
	 * 		filePath���ļ���ַ
	 * �������ݶ��壺
	 * 		isOk���Ƿ��ѹ�ɹ���0��ʾʧ�ܣ�1��ʾ�ɹ�
	 * 
	 * */
	public int UnzippedFile(File file, String folderPath)throws ZipException,IOException{
		int isOk = 1;
		int BUFFER = 4096; // ���ﻺ��������ʹ��4KB��
		ZipFile zfile = new ZipFile(file);
		Enumeration zList = zfile.entries();
		ZipEntry ze = null;
		byte[] buf = new byte[BUFFER];
		Log.i(tag, "UnzippedFile;01;");
		BufferedOutputStream dest = null; // ���������
		while (zList.hasMoreElements()) {
			ze = (ZipEntry)zList.nextElement();
			if (ze.isDirectory()) {
				Log.i(tag, "UnzippedFile;ze.getName:"+ze.getName());
				String dirStr = folderPath + ze.getName();
				dirStr = new String(dirStr.getBytes("8859_1"),"GB2312");
				Log.i(tag, "UnzippedFile;str:"+dirStr);
				File f = new File(dirStr);
				f.mkdir();
				continue;
			}
			Log.d(tag, "UnzippedFile;ze.getName:"+folderPath+ze.getName());
			File entryFile = new File(folderPath + ze.getName());
			FileOutputStream fos = new FileOutputStream(entryFile);
			dest = new BufferedOutputStream(fos, BUFFER);
			
			OutputStream os = dest;//new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath,ze.getName())));
			InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
			int readLen = 0;
			while ((readLen=is.read(buf,0,BUFFER)) != -1) {
				os.write(buf,0,readLen);
			}
			is.close();
			os.close();
		}
		zfile.close();
		Log.i(tag, "UnzippedFile;finish;");
		return isOk;
	}
	/*@author:DerekXie
	 * �������壺������Ŀ¼������һ�����·������Ӧ��ʵ���ļ���
	 * �������壺
	 * 		baseDir��ָ����Ŀ¼
	 * 		absFileName�����·����
	 * �������ݶ��壺
	 * 		file��ʵ�ʵ��ļ�
	 * 
	 * */
	public static File getRealFileName(String baseDir, String absFileName){
		String[] dirs = absFileName.split(LoginContent.initFolder);//����ַ���
		File ret = new File(baseDir);
		String substr = null;
		Log.d(tag, "getRealFileName;subStr:?01");
		if (dirs.length > 1) {
			Log.d(tag, "getRealFileName;subStr:?01substr:?");
			for (int i = 0; i < dirs.length-1; i++) {
				substr = dirs[i];
				Log.d(tag, "getRealFileName;subStr:?01substr:01:"+substr);
				try {
					substr = new String(substr.getBytes("8859_1"),"GB2312");
					Log.d(tag, "getRealFileName;subStr:?01substr:"+substr);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				ret = new File(ret,substr);
			}
			if (!ret.exists()) {
				Log.d(tag, "getRealFileName;subStr:?01");
				ret.mkdir();//�༶Ŀ¼����ʹ�����ֵķ�ʽ��mkdirs
			}
			substr = dirs[dirs.length-1];
			try {
				substr = new String(substr.getBytes("8859_1"),"GB2312");
				Log.d(tag, "getRealFileName;subStr:"+substr);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			ret = new File(ret,substr);
			return ret;
		}
		Log.d(tag, "getRealFileName;subStr:?09");
		return ret;
	}
}
