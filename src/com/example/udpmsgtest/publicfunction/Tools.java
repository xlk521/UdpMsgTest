package com.example.udpmsgtest.publicfunction;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class Tools {
	private final static String TAG = "Tools";
	private final static boolean isDebug = true; // debug模式，可以输出log，默认为true；当为false时关闭log输出

	
	private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte
	public final static void Log(String tag, String msg) {
		if (isDebug) {
			android.util.Log.i(tag, msg);
		}
	}

	//判断手机网络是否可用
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isConnectedOrConnecting();//mMobileNetworkInfo.isAvailable();
				
			}
		}
		return false;
	}
	
	//判断手机wifi是否可用
	public static boolean isWIFIConnected(Context context){
		if (context != null) {
			 WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
			 if(wifiManager.isWifiEnabled()){
				 ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
				 NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
				 return mWiFiNetworkInfo.isConnected();
			 }else{
				 return false; 
			 }
		}
		return false;
	}
	
	//读取文件内容
	public static String readFile(String filePath) {
		String content = null; // 文件内容字符串
		String path = filePath;
		
		Tools.Log(TAG, "readFile;path="+path);
		File initFile = new File(path);
		boolean fileHas = false;
		if (initFile.exists()) {
			fileHas = true;
		}else{
			ArrayList<String> sdcardPath = getSdcardPath();
			Tools.Log(TAG, "readFile;getSdcardPath="+getSdcardPath());
			if(sdcardPath != null && sdcardPath.size() > 0){
				for(int i=0; i<sdcardPath.size(); i++){
					String path1 = sdcardPath.get(i) + "/" + filePath;
					Tools.Log(TAG, "readFile;path1="+path);
					initFile = new File(path1);
					if(initFile.exists()) {
						fileHas = true;
						break;
					}
				}
			}
			
		}
		if(fileHas){
			try {
				InputStream instream = new FileInputStream(initFile);
				if (instream != null) {
					InputStreamReader inputreader = new InputStreamReader(instream,"UTF-8");
					BufferedReader buffreader = new BufferedReader(inputreader);
					String line;
					// 分行读取
					content = "";
					while ((line = buffreader.readLine()) != null) {
						content += line;
					}
					instream.close();
				}
			} catch (java.io.FileNotFoundException e) {
				Log(TAG, "The File doesn't not exist.");
			} catch (IOException e) {
				Log(TAG, e.getMessage());
			}
		}else{
			return content;
		}
		return content;
	}
	//写入文件内容
	public static void writeFile(String filePath,String fileContent){
		File initFile = new File(filePath);
		if(initFile.exists()){
			try {  
				Log(TAG,"writeFile;filename="+initFile.getName()+"filePath="+filePath);
				FileOutputStream outputStream = new FileOutputStream(initFile, false);
	            outputStream.write(fileContent.getBytes());  
	            outputStream.flush();  
	            outputStream.close();  
	            Log(TAG,"writeFile success");  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  

		}
	}
	/**
	 * 去除空格键
	 * 
	 **/
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	private static ArrayList<String> getSdcardPath() {
		ArrayList<String> sdcardNames = new ArrayList<String>();
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			String mount = new String();
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;

				if (line.contains("fat")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						sdcardNames.add(columns[1]);
					}
				} else if (line.contains("fuse")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						sdcardNames.add(columns[1]);
					}
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdcardNames;
	}
	
	

	//解压缩文件
	public final static boolean Unzip(String zipFile, String targetDir) {
		Tools.Log(TAG, "zipFile="+zipFile+";targetDir="+targetDir);
		boolean unzip = false;
		int BUFFER = 4096; // 这里缓冲区我们使用4KB，
		String strEntry; // 保存每个zip的条目名称

		String zipOutDir = targetDir;
		String iniDir = targetDir;
		String bookDir = targetDir;
		try {
			BufferedOutputStream dest = null; // 缓冲输出流
			FileInputStream fis = new FileInputStream(zipFile);
			ZipInputStream zis = new ZipInputStream( new BufferedInputStream(fis));
			ZipEntry entry; // 每个zip条目的实例

			while ((entry = zis.getNextEntry()) != null) {
				if(entry.isDirectory()){
					bookDir = bookDir + entry.toString();
				}else{
					if(entry.toString().contains("ini")){
						zipOutDir = iniDir;
					}else{
						zipOutDir = targetDir;
					}
					try {
						int count;
						byte data[] = new byte[BUFFER];
						strEntry = entry.getName();
	
						Tools.Log(TAG, "Unzip;zipOutDir="+zipOutDir);
						File entryFile = new File(zipOutDir + strEntry);
						File entryDir = new File(entryFile.getParent());
						Tools.Log(TAG, "Unzip;entryFile="+targetDir + strEntry+";entryDir"+entryFile.getParent()+";entryFile.name="+entryFile.getName());
						if (!entryDir.exists()) {
							entryDir.mkdirs();
						}

						FileOutputStream fos = new FileOutputStream(entryFile);
						dest = new BufferedOutputStream(fos, BUFFER);
						while ((count = zis.read(data, 0, BUFFER)) != -1) {
							dest.write(data, 0, count);
						}
						dest.flush();
						dest.close();
						unzip = true;
					} catch (Exception ex) {
						ex.printStackTrace();
						unzip = false;
					}
				}
			}
			zis.close();
		} catch (Exception cwj) {
			cwj.printStackTrace();
		}
		return unzip;
	}
	
	
//	/**  
//	    * 压缩文件,文件夹  
//	     * @param srcFileString 要压缩的文件/文件夹名字  
//	     * @param zipFileString 指定压缩的目的和名字  
//	    * @throws Exception  
//	     */  
//	    public static boolean ZipFolder(String srcFileString, String zipFileString){
//	    	boolean state = false;
//	    	try{
//		        android.util.Log.v("XZip", "ZipFolder(String, String)");   
//		           
//		        //创建Zip包   
//		       java.util.zip.ZipOutputStream outZip = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(zipFileString));   
//		           
//		     //打开要输出的文件   
//		       java.io.File file = new java.io.File(srcFileString);   
//		 
//		     //压缩   
//		      ZipFiles(file.getParent()+java.io.File.separator, file.getName(), outZip);   
//		       
//		        //完成,关闭   
//		     outZip.finish();   
//		     outZip.close();  
//		     deleteFile("CW",srcFileString);
//		     SharedPreferences perference = ClientApp.instance.getApplicationContext().getSharedPreferences( Constant.sharePerferceStr, 0);
//			 String EID = perference.getString(Constant.NOW_USER_E_ID, null);
//			 if(EID != null){
//				 deleteFile("SP+",EID,srcFileString);
//			 }
//		     state = true;
//	    	}catch(Exception e){
//	    	 e.printStackTrace();
//	     }
//	       return state;
//	    }//end of func   
//	       
//	     /**  
//	      * 压缩文件  
//	      * @param folderString  
//	      * @param fileString  
//	     * @param zipOutputSteam  
//	      * @throws Exception  
//	     */  
//	     private static void ZipFiles(String folderString, String fileString, java.util.zip.ZipOutputStream zipOutputSteam)throws Exception{   
//	        android.util.Log.v("XZip", "ZipFiles(String, String, ZipOutputStream)");   
//	          
//	        if(zipOutputSteam == null)   
//	            return;   
//	           
//	      java.io.File file = new java.io.File(folderString+fileString);  
//	      
//	      SharedPreferences perference = ClientApp.instance.getSharedPreferences( Constant.sharePerferceStr, 0);
//		  String EID = perference.getString(Constant.NOW_USER_E_ID, null);
//          
//      //判断是不是文件   
//        if (file.isFile()) {   
//        	if(fileString.contains("+"+EID)){
//				java.util.zip.ZipEntry zipEntry = new java.util.zip.ZipEntry(fileString);
//				java.io.FileInputStream inputStream = new java.io.FileInputStream(file);
//				zipOutputSteam.putNextEntry(zipEntry);
//
//				int len;
//				byte[] buffer = new byte[4096];
//
//				while ((len = inputStream.read(buffer)) != -1) {
//					zipOutputSteam.write(buffer, 0, len);
//				}
//			}
//			zipOutputSteam.closeEntry();
//
//		} else {   
//	               
//	           //文件夹的方式,获取文件夹下的子文件   
//	             String fileList[] = file.list();   
//	               
//	            //如果没有子文件, 则添加进去即可   
//	            if (fileList.length <= 0) {   
//	             java.util.zip.ZipEntry zipEntry =  new java.util.zip.ZipEntry(fileString+java.io.File.separator);   
//	               zipOutputSteam.putNextEntry(zipEntry);   
//	               zipOutputSteam.closeEntry();                   
//	            }   
//	            
//	           //如果有子文件, 遍历子文件   
//	            for (int i = 0; i < fileList.length; i++) {   
//	             ZipFiles(folderString, fileString+java.io.File.separator+fileList[i], zipOutputSteam);   
//	            }//end of for   
//	       
//	        }//end of if   
//	           
//	    }//end of func   



	// 用十六进制（基数 16）参数表示的无符号整数值的字符串表示形式。
	// 转化字符串为十六进制编码
	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	// 转化十六进制编码为字符串
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/*
	 * 16进制数字字符集
	 */
	private static String hexString = "0123456789ABCDEF";

	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = new byte[0];
		try {
			bytes = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2){
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		}
		String returnStr = null;
		try{
			returnStr = new String(baos.toByteArray(),"utf-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnStr;
	}


	public static void convertToUnicode(String originString) {
		String[] utfStrings= new String[3];
		byte[] UTF8_Encoding = new byte[3];
		int index = 0;

		try {
			for (int i = 0; i < originString.length(); i++) {
				char cur = originString.charAt(i);
				if (cur == '\\' && i + 2 < originString.length()) {
					String str = originString.substring(i, i + 3);
					char a = originString.charAt(++i);
					char b = originString.charAt(++i);
					if (isHexNum(a) && isHexNum(b)) {
						utfStrings[index++] = String.valueOf(str);
					} else {
						Tools.Log(TAG, "str="+str);
						index = 0;
					}
				} else {
					for (int j = 0; j < index; j++) {
						System.out.print(utfStrings[j]);
					}
					Tools.Log(TAG, "cur="+cur);
					index = 0;
				}

				if (index == UTF8_Encoding.length) {
					for (int  j = 0; j < utfStrings.length; j++) {
						UTF8_Encoding[j] = (byte) (Integer.parseInt(utfStrings[j].substring(1),
								16));
					}
					String a = new String(UTF8_Encoding, "UTF-8");
					Tools.Log(TAG, "a"+a);
					index = 0;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static boolean isHexNum(char ch) {
		return (ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f');
	}

	public static void convertString(String utf ) {
		byte[] codes = new byte[3];
		int index = 0;
		char[] chs = new char[2];
		try {
			for (int i = 0; i < utf.length(); i++) {
				char ch = utf.charAt(i);
				if (ch == '\\') {
					if (i + 2 >= utf.length()) {
						Tools.Log(TAG, "awrong input! Please check");
						break;
					}

					chs[0] = utf.charAt(++i);
					chs[1] = utf.charAt(++i);
					if (isNum(chs[0]) && isNum(chs[1])) {
						String s = String.valueOf(chs);
						codes[index++] = (byte) (0xff & Integer.parseInt(s, 16));
					} else {
						System.out.print(ch);
						Tools.Log(TAG, "ch="+ch);
						System.out.print(chs);
						Tools.Log(TAG, "chs="+chs);
					}
				} else {
					System.out.print(ch);
					Tools.Log(TAG, "else ch="+ch);
				}

				if (index >= codes.length) {
					index = 0;
					String a = new String(codes, "UTF-8");
					Tools.Log(TAG, "a ch="+a);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean isNum(char a) {
		return (a >= '0' && a <= '9') || (a >= 'a' && a <= 'z');
	}
	
	
	//删除以固定字符开头的文件
	private static void deleteFile(String startStr,String folderPath){
		try{
			if(folderPath != null){
				File folderFile = new File(folderPath);
				if(folderFile.isDirectory()){
					String[] fileNameList = folderFile.list();
					if(fileNameList != null && fileNameList.length > 0){
						for(int i=0; i<fileNameList.length; i++){
							if(fileNameList[i].startsWith("CW+") || fileNameList[i].startsWith("CWRZ+")){
								Tools.Log(TAG, "delete:"+fileNameList[i]+";folderPath + fileNameList[i]="+folderPath + fileNameList[i]);
								File file = new File(folderPath + fileNameList[i]);
								if(file.exists()){
									file.delete();
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void deleteFile(String filetype, String userID, String folderPath){
		try{
			if(folderPath != null){
				File folderFile = new File(folderPath);
				if(folderFile.isDirectory()){
					String[] fileNameList = folderFile.list();
					if(fileNameList != null && fileNameList.length > 0){
						for(int i=0; i<fileNameList.length; i++){
							if(fileNameList[i].startsWith(filetype)&&fileNameList[i].contains("+"+userID)){
								File file = new File(folderPath + fileNameList[i]);
								if(file.exists()){
									file.delete();
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
