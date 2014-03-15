package com.example.udpmsgtest.publicfunction;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.SimpleTimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.udpmsgtest.basestation.BaseStationFunction;
import com.example.udpmsgtest.basestation.BaseStationFunction.SCell;
import com.example.udpmsgtest.service.UdpService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PublicFunctions {

	private Context context = null;
	private String tag = "PublicFunctions";
	
	public PublicFunctions(){
		
	}
	public PublicFunctions(Context con){
		this.context = con;
	}
	/*author:DerekXie
	 * 函数定义：获取当前手机的ip地址，如果没有，则返回null
	 * 参数定义：无参数
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
	/*@author:DerekXie
	 * 函数功能：开启一个闹钟，定时发送数据
	 * 参数定义：无参数
	 * */
	public void sendDataOnClick(){
		if (context != null) {
			try{
				//获取AlarmManager系统服务
				AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
				//包装需要执行Service的Intent
				Intent intent = new Intent(context, UdpService.class);
				//获取当前的基站信息
				BaseStationFunction bsFunction = new BaseStationFunction(context);
				String sendStr = bsFunction.getSendStr(0);
				intent.putExtra(UdpConstant.SbMsg, sendStr);
				intent.putExtra(UdpConstant.SbMsgType, UdpConstant.SbType);
				intent.putExtra(UdpConstant.SbSaveDbStr, sendStr);
				intent.putExtra(UdpConstant.SbIsNeedSave, 0);
//				intent.setAction(action);
				PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				//触发服务的起始时间，根据传来的时间启动
				//long triggerAtTime = getNextAlarmTime(begintime);
				int interval = 60 * 1000 * 1;//闹铃间隔
				//使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
				manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			Log.i(tag, "sendDataOnClick;context = null;");
		}
	}

}
