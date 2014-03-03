package com.example.udpmsgtest.publicfunction;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PublicFunctions {

	private String TAG = "PublicFunctions";
	public PublicFunctions(){}
	/*author:DerekXie
	 * 函数定义：获取当前手机的ip地址，如果没有，则返回null
	 * 参数定义：无参数
	 * */
	public String getPhoneIP(){
		String ipAddress = null;
		try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress()) {
	                	ipAddress = inetAddress.getHostAddress().toString();
	                	Log.i(TAG, "getPhoneIP;info;text: "+ipAddress);
	                    return ipAddress;
	                }
	            }
	        }
	    } catch (SocketException ex) {
	        Log.e(TAG, "getPhoneIP;error;text: "+ex.toString());
	    }
		return ipAddress;
	}
	/*
	 * 与上面的功能相同，等待查找上面功能为啥不可用
	 * */
    public static String getIp(){
        String localip=null;
        String netip=null;
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded=false;
            while(netInterfaces.hasMoreElements() && !finded){
                NetworkInterface ni=netInterfaces.nextElement();
                Enumeration<InetAddress> address=ni.getInetAddresses();
                while(address.hasMoreElements()){
                    ip=address.nextElement();
                    if( !ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":")==-1){
                        netip=ip.getHostAddress();
                        finded=true;
                        break;
                    }else if(ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":")==-1){
                        localip=ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if(netip!=null && !"".equals(netip)){
            return netip;
        }else{
            return localip;
        }
    }
	 //byte转int
	public static int byteArrayToInt(byte[] b) {
		int fromByte = 0;
		for (int i = 0; i < 2; i++){
			int n = (b[i] < 0 ? (int)b[i] + 256 : (int)b[i]) << (8 * i);
			fromByte += n;
		}
		return fromByte;
	}
	//获取设备标识信息
	public String getDeviceMeesage(){
//		//获取sim卡信息
//		TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
//		String imei = tm.getDeviceId();  
//		if(imei.length()<15){
//			for(int i=15-imei.length(); i>0; i--){
//				imei = "0"+imei;
//			}
//		}
//		return imei;
		return "xlk123";
	}
}
