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
	 * 函数定义：获取需要解压的文件，并且对其进行解压
	 * 参数定义：
	 * 		filePath：需要解压的文件地址
	 * 		folderPath：需要存放解压完后的数据的文件夹地址
	 * 返回数据定义：
	 * 		isOk：是否解压成功，
	 * 				0表示失败，
	 * 				1表示成功，
	 * 				-1表示文件不存在，
	 * 				-2表示文件夹不存在
	 * 				-3表示没有传入有效的Context
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
	 * 函数定义：解压缩文件
	 * 参数定义：
	 * 		filePath：文件地址
	 * 返回数据定义：
	 * 		isOk：是否解压成功，0表示失败，1表示成功
	 * 
	 * */
	public int UnzippedFile(File file, String folderPath)throws ZipException,IOException{
		int isOk = 1;
		int BUFFER = 4096; // 这里缓冲区我们使用4KB，
		ZipFile zfile = new ZipFile(file);
		Enumeration zList = zfile.entries();
		ZipEntry ze = null;
		byte[] buf = new byte[BUFFER];
		Log.i(tag, "UnzippedFile;01;");
		BufferedOutputStream dest = null; // 缓冲输出流
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
	 * 函数定义：给定根目录，返回一个相对路径所对应的实际文件名
	 * 参数定义：
	 * 		baseDir：指定根目录
	 * 		absFileName：相对路径名
	 * 返回数据定义：
	 * 		file：实际的文件
	 * 
	 * */
	public static File getRealFileName(String baseDir, String absFileName){
		String[] dirs = absFileName.split(LoginContent.initFolder);//拆分字符串
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
				ret.mkdir();//多级目录必须使用这种的方式：mkdirs
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
