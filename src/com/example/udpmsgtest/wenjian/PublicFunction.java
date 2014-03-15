package com.example.udpmsgtest.wenjian;

import java.io.File;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;


public class PublicFunction {

	Context context;
	public PublicFunction(Context con){
		this.context = con;
	}
	// ��ȡ�Ѱ�װ��wps�İ���
	public String getWPSPageName() {
		String wpsPageName = null;
		ResolveInfo info = null;
		List<ResolveInfo> mApps = null;
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		mApps = context.getPackageManager().queryIntentActivities(mainIntent, 0);
		for (int i = 0; i < mApps.size(); i++) {
			info = mApps.get(i);
			String appLabel = info.loadLabel(context.getPackageManager()).toString();
			String packagename = info.activityInfo.packageName;
			String appname = info.activityInfo.name;
			if(appname.contains("wps.")){
				wpsPageName = packagename;
				break;
			}
		}
		return wpsPageName;
	}
	//���ļ�
	public void openWordFile(String filePath){
		String wpsPageName = getWPSPageName();
		if(wpsPageName == null){
			Toast.makeText(context, "�밲װwps officeӦ��", Toast.LENGTH_LONG).show();
//			((Object) context).finish();
		}else{
//			finish();
			// ����adobe�����ļ�
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			File file = new File(filePath);
			String fileType = getMIMEType(file);
			Uri uri = Uri.fromFile(file);
			
			//ֱ������wps
			intent.setClassName(wpsPageName,"cn.wps.moffice.documentmanager.PreStartActivity");
			intent.putExtra("ClearTrace", true);
			intent.putExtra("ClearBuffer", true);
			intent.setDataAndType(uri, fileType);
			try  
			{ 
				context.startActivity(intent);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * �����ļ���׺����ö�Ӧ��MIME���͡�
	 * @param file
	 */ 
	public String getMIMEType(File file) {
	     
	    String type="*/*"; 
	    String fName = file.getName(); 
	    //��ȡ��׺��ǰ�ķָ���"."��fName�е�λ�á� 
	    int dotIndex = fName.lastIndexOf("."); 
	    if(dotIndex < 0){ 
	        return type; 
	    } 
	    /* ��ȡ�ļ��ĺ�׺��*/ 
	    String end=fName.substring(dotIndex,fName.length()).toLowerCase(); 
	    if(end=="")return type; 
	    //��MIME���ļ����͵�ƥ������ҵ���Ӧ��MIME���͡� 
	    for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??��������һ�������ʣ����MIME_MapTable��ʲô�� 
	        if(end.equals(MIME_MapTable[i][0])) 
	            type = MIME_MapTable[i][1]; 
	    }        
	    return type; 
	} 
	
	private final String[][] MIME_MapTable={
            //{��׺����MIME����} 
            {".3gp",    "video/3gpp"}, 
            {".apk",    "application/vnd.android.package-archive"}, 
            {".asf",    "video/x-ms-asf"}, 
            {".avi",    "video/x-msvideo"}, 
            {".bin",    "application/octet-stream"}, 
            {".bmp",    "image/bmp"}, 
            {".c",  "text/plain"}, 
            {".class",  "application/octet-stream"}, 
            {".conf",   "text/plain"}, 
            {".cpp",    "text/plain"}, 
            {".doc",    "application/msword"}, 
            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}, 
            {".xls",    "application/vnd.ms-excel"},  
            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, 
            {".exe",    "application/octet-stream"}, 
            {".gif",    "image/gif"}, 
            {".gtar",   "application/x-gtar"}, 
            {".gz", "application/x-gzip"}, 
            {".h",  "text/plain"}, 
            {".htm",    "text/html"}, 
            {".html",   "text/html"}, 
            {".jar",    "application/java-archive"}, 
            {".java",   "text/plain"}, 
            {".jpeg",   "image/jpeg"}, 
            {".jpg",    "image/jpeg"}, 
            {".js", "application/x-javascript"}, 
            {".log",    "text/plain"}, 
            {".m3u",    "audio/x-mpegurl"}, 
            {".m4a",    "audio/mp4a-latm"}, 
            {".m4b",    "audio/mp4a-latm"}, 
            {".m4p",    "audio/mp4a-latm"}, 
            {".m4u",    "video/vnd.mpegurl"}, 
            {".m4v",    "video/x-m4v"},  
            {".mov",    "video/quicktime"}, 
            {".mp2",    "audio/x-mpeg"}, 
            {".mp3",    "audio/x-mpeg"}, 
            {".mp4",    "video/mp4"}, 
            {".mpc",    "application/vnd.mpohun.certificate"},        
            {".mpe",    "video/mpeg"},   
            {".mpeg",   "video/mpeg"},   
            {".mpg",    "video/mpeg"},   
            {".mpg4",   "video/mp4"},    
            {".mpga",   "audio/mpeg"}, 
            {".msg",    "application/vnd.ms-outlook"}, 
            {".ogg",    "audio/ogg"}, 
            {".pdf",    "application/pdf"}, 
            {".png",    "image/png"}, 
            {".pps",    "application/vnd.ms-powerpoint"}, 
            {".ppt",    "application/vnd.ms-powerpoint"}, 
            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"}, 
            {".prop",   "text/plain"}, 
            {".rc", "text/plain"}, 
            {".rmvb",   "audio/x-pn-realaudio"}, 
            {".rtf",    "application/rtf"}, 
            {".sh", "text/plain"}, 
            {".tar",    "application/x-tar"},    
            {".tgz",    "application/x-compressed"},  
            {".txt",    "text/plain"}, 
            {".wav",    "audio/x-wav"}, 
            {".wma",    "audio/x-ms-wma"}, 
            {".wmv",    "audio/x-ms-wmv"}, 
            {".wps",    "application/vnd.ms-works"}, 
            {".xml",    "text/plain"}, 
            {".z",  "application/x-compress"}, 
            {".zip",    "application/x-zip-compressed"}, 
            {"",        "*/*"}   
        }; 
}
