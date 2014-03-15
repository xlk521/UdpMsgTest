package com.example.udpmsgtest.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import com.example.udpmsgtest.BaseActivity;
import com.example.udpmsgtest.basestation.activity.MainActivity;
import com.example.udpmsgtest.publicfunction.Constant;
import com.example.udpmsgtest.publicfunction.PublicFunctions;
import com.example.udpmsgtest.publicfunction.UdpConstant;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class UdpService extends Service{

	public ProgressDialog progressDialog = null;
	//UDP
	private WifiManager.MulticastLock lock;
	private WifiManager manager;
	private String tag = "UdpService";
	private final IBinder binder = new UdpBinder();
	private int biaozhi = -10;
	@Override
	public void onCreate() {
		//�����豸UDP���ܵĹ�����
		manager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
	    lock= manager.createMulticastLock("lockwifi");
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if (lock.isHeld()) {
			lock.release();
		}
		//���մ��������
		Intent getIntent = intent;
		if (getIntent != null) {
			String msg = getIntent.getStringExtra(UdpConstant.SbMsg);
			String msgType = getIntent.getStringExtra(UdpConstant.SbMsgType);
			String dbSaveMsg = getIntent.getStringExtra(UdpConstant.SbSaveDbStr);
			Boolean needSave = false;
			
			if (msgType != null && msgType.equalsIgnoreCase(UdpConstant.SbType)) {
				int isSaveState = getIntent.getIntExtra(UdpConstant.SbIsNeedSave, 0);
				if (isSaveState == 1) {
					needSave = true;
				}
			}
			//�������ݵ�������
			sendThread(0 ,msg, needSave, msgType, dbSaveMsg);
		}else{
			Log.i(tag, "UdpService;onStart;intent is null;");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
    public class UdpBinder extends Binder {
    	public UdpService getService() {
            return UdpService.this;
        }
    }
  
    public IBinder onBind(Intent intent) {
        return binder;
    }
	/*@author��DerekXie
	 * �������壺���ص�ǰ�����ݱ�ʶ��biaozhi�������жϽ��շ����������ݵ����
	 * �������壺�޲���
	 * */
	public int getBiaoZhi(){
		return biaozhi;
	}
	/*@author��DerekXie
	 * �������壺��ȡ��ǰ�����ݱ�ʶ��biaozhi�������жϽ��շ����������ݵ����
	 * �������壺�޲���
	 * */
	public void setBiaoZhi(int num){
		Log.i(tag, "biaozhi=num="+num);
		biaozhi = num;
	}
	
	/*@author��DerekXie
	 * �������壺ͨ���̷߳�������
	 * �������壺
	 * testTime���ظ��������ݵĴ���
	 * msg����Ҫ���͵�����
	 * isNeedSave���������ݿ��Ƿ���Ҫ����
	 * msgType����Ϣ����
	 * dbSavemsg����
	 * */
	public void sendThread(final int testTime ,final String msg, final boolean isNeedSave, final String msgType, final String dbSaveMsg){
		//����һ���̣߳����ڷ������ݡ��������ݵķ�����Ϣ
		new Thread(new Runnable() {
			@Override
			public void run() {
		    	String textStr = "null";
		    	if (msg != null) {
					textStr = msg;
				}
		    	String newStr = "Derek+"+textStr+"+Xie";
		    	Message msg = new Message();
		        msg.what = UdpConstant.UdpSending;//
		        msg.obj = newStr;
		        handler.sendMessage(msg);
		    	send(newStr);
			}
		}).start();
	}
	/*author:DerekXie
	 * �������壺����UDPЭ�鷢������
	 * �������壺
	 * message������Ҫ���͵�������Ϣ
	 * 
	 * */
	public void send(String message){
		//------------------------------------------------------------------------------//
        message = (message == null ? "Hello IdeasAndroid!" : message);
        int server_port = Constant.UDP_SERVER_PORT;
        PublicFunctions pubFun = new PublicFunctions();
        String IP = pubFun.getIp();//��ȡ�ֻ���ǰ��ip
		if(IP == null){
			return;
		}
		Log.i("IP", "IP="+IP);
		DatagramSocket s = null;
		InetSocketAddress socketAddress = new InetSocketAddress(IP, Constant.UDP_CLIENT_PORT); // �󶨱��ط��Ͷ˿�
		try {
			s = new DatagramSocket(socketAddress);
			s.setSoTimeout(0);
		} catch (SocketException e) {
			e.printStackTrace();
		}
        InetAddress local = null;
        try {
            // ���ɷ�������IP
            local = InetAddress.getByName(Constant.BASE_IP);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int msg_length = message.length();
        byte[] messageByte;
		try {
			messageByte = message.getBytes("UTF-8");
			DatagramPacket p = new DatagramPacket(messageByte, messageByte.length, local, server_port);
	        try {
	        	lock.acquire();
	            s.send(p);
	            reseveMsg(s);
	            lock.release();
	            s.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
    }
	/*author:DerekXie
	 * �������壺���շ����ͨ��UDPЭ�鷵�ص���Ϣ
	 * �������壺
	 * datagramSocket����������ʱ���õ�������������������
	 * 
	 * */
	public void reseveMsg(final DatagramSocket datagramSocket){
    	byte[] buf_recv;
        buf_recv = new byte[1024];
        try {
            DatagramPacket pack = new DatagramPacket(buf_recv,buf_recv.length);
            datagramSocket.setSoTimeout(6000);//���ó�ʱ��������ʱ��δ�ӵ���Ϣ��socket�Զ����������������������ĺ���
            datagramSocket.receive(pack); // ������Ϣ
            byte[] packData = pack.getData();//��ȡ���յ�������
            String strMsg=new String(packData).trim();
            Log.i(tag, "UdpTest;msg.obj:"+strMsg);
            Message msg = new Message();
            if (strMsg.equalsIgnoreCase(UdpConstant.returnResoutOk) || strMsg.endsWith(UdpConstant.returnResoutOk)) {
            	msg.what = UdpConstant.ReceiveResultOk;//�ɹ���������
			}else if (strMsg.equalsIgnoreCase(UdpConstant.returnResoutNull)) {
				msg.what = UdpConstant.ReceiveResultNull;//���͵�����Ϊ��ֵ����Ҳ�����˽��ճɹ�����Ϣ
			}else if (strMsg.equalsIgnoreCase(UdpConstant.returnResoutError)) {
				msg.what = UdpConstant.ReceiveResultError;//���͵����ݰ���ʧ��������
			}else{
				msg.what = UdpConstant.ReceiveResultOther;//����δ֪�ķ�����Ϣ
			}
            msg.obj = strMsg;
            handler.sendMessage(msg);
            Arrays.fill(buf_recv, (byte) 0); // ����
        }catch(IOException e){
        	Message msg = new Message();
        	msg.what = UdpConstant.ReceiveResultTimeOut;//����δ֪�ķ�����Ϣ
        	msg.obj = "";
            handler.sendMessage(msg);
        }
	}
	
	Handler handler = new Handler() {
		@Override
	    public void handleMessage(Message msg) {

	        switch (msg.what) {
	        case UdpConstant.UdpSending://���ڷ�������
	            Toast.makeText(getApplicationContext(), "���ڷ�������:" + (String) msg.obj, 2000).show(); // չʾ���յ����ַ���
	            setBiaoZhi(UdpConstant.UdpSending);
	    		getBiaoZhi();
	            break;
	        case UdpConstant.ReceiveResultOk://���ݷ��ͳɹ����ڽ��պ����м�飩
	            Toast.makeText(getApplicationContext(), "���ݷ���[�ɹ�]!", 2000).show(); // չʾ���յ����ַ���
	            setBiaoZhi(UdpConstant.ReceiveResultOk);
	    		getBiaoZhi();
	            break;
	        case UdpConstant.ReceiveResultNull://���ݷ���ʧ�ܣ��ڽ��պ����м�飩
	            Toast.makeText(getApplicationContext(), "���ݷ���[ʧ��]!", 2000).show(); // չʾ���յ����ַ���
	            setBiaoZhi(UdpConstant.ReceiveResultNull);
	    		getBiaoZhi();
	            break;
	        case UdpConstant.ReceiveResultTimeOut://���ݽ��ճ�ʱ���ڽ��պ����м�飩
	            Toast.makeText(getApplicationContext(), "�������ݳ�ʱ���޷��ж���Ϣ�Ƿ��ͳɹ���", 2000).show(); // չʾ���յ����ַ���
	            setBiaoZhi(UdpConstant.ReceiveResultTimeOut);
	    		getBiaoZhi();
	            break;
	        default:
	        	Toast.makeText(getApplicationContext(), "���������������飡", 2000).show(); // չʾ���յ����ַ���
	        	setBiaoZhi(UdpConstant.ReceiveResultOther);
	    		getBiaoZhi();
	            break;
	        }
	        
	        super.handleMessage(msg);
	    }
	};
}
