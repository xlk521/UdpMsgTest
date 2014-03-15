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
	 * �������壺��ȡ��ǰ�ֻ���ip��ַ�����û�У��򷵻�null
	 * �������壺�޲���
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
	 //byteתint
	public static int byteArrayToInt(byte[] b) {
		int fromByte = 0;
		for (int i = 0; i < 2; i++){
			int n = (b[i] < 0 ? (int)b[i] + 256 : (int)b[i]) << (8 * i);
			fromByte += n;
		}
		return fromByte;
	}
	//��ȡ�豸��ʶ��Ϣ
	public String getDeviceMeesage(){
//		//��ȡsim����Ϣ
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
	 * �������ܣ�����һ�����ӣ���ʱ��������
	 * �������壺�޲���
	 * */
	public void sendDataOnClick(){
		if (context != null) {
			try{
				//��ȡAlarmManagerϵͳ����
				AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
				//��װ��Ҫִ��Service��Intent
				Intent intent = new Intent(context, UdpService.class);
				//��ȡ��ǰ�Ļ�վ��Ϣ
				BaseStationFunction bsFunction = new BaseStationFunction(context);
				String sendStr = bsFunction.getSendStr(0);
				intent.putExtra(UdpConstant.SbMsg, sendStr);
				intent.putExtra(UdpConstant.SbMsgType, UdpConstant.SbType);
				intent.putExtra(UdpConstant.SbSaveDbStr, sendStr);
				intent.putExtra(UdpConstant.SbIsNeedSave, 0);
//				intent.setAction(action);
				PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				//�����������ʼʱ�䣬���ݴ�����ʱ������
				//long triggerAtTime = getNextAlarmTime(begintime);
				int interval = 60 * 1000 * 1;//������
				//ʹ��AlarmManger��setRepeating�������ö���ִ�е�ʱ������seconds�룩����Ҫִ�е�Service
				manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			Log.i(tag, "sendDataOnClick;context = null;");
		}
	}

}
