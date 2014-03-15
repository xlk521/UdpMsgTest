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
		//设置设备UDP功能的功能锁
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
		//接收传入的数据
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
			//发送数据到服务器
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
	/*@author：DerekXie
	 * 函数定义：返回当前的数据标识，biaozhi是用于判断接收服务器端数据的情况
	 * 参数定义：无参数
	 * */
	public int getBiaoZhi(){
		return biaozhi;
	}
	/*@author：DerekXie
	 * 函数定义：获取当前的数据标识，biaozhi是用于判断接收服务器端数据的情况
	 * 参数定义：无参数
	 * */
	public void setBiaoZhi(int num){
		Log.i(tag, "biaozhi=num="+num);
		biaozhi = num;
	}
	
	/*@author：DerekXie
	 * 函数定义：通过线程发送数据
	 * 参数定义：
	 * testTime：重复发送数据的次数
	 * msg：需要发送的数据
	 * isNeedSave：本地数据库是否需要保存
	 * msgType：信息类型
	 * dbSavemsg：？
	 * */
	public void sendThread(final int testTime ,final String msg, final boolean isNeedSave, final String msgType, final String dbSaveMsg){
		//启动一个线程，用于发送数据、监听数据的返回信息
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
	 * 函数定义：按照UDP协议发送数据
	 * 参数含义：
	 * message：所需要发送的文字信息
	 * 
	 * */
	public void send(String message){
		//------------------------------------------------------------------------------//
        message = (message == null ? "Hello IdeasAndroid!" : message);
        int server_port = Constant.UDP_SERVER_PORT;
        PublicFunctions pubFun = new PublicFunctions();
        String IP = pubFun.getIp();//获取手机当前的ip
		if(IP == null){
			return;
		}
		Log.i("IP", "IP="+IP);
		DatagramSocket s = null;
		InetSocketAddress socketAddress = new InetSocketAddress(IP, Constant.UDP_CLIENT_PORT); // 绑定本地发送端口
		try {
			s = new DatagramSocket(socketAddress);
			s.setSoTimeout(0);
		} catch (SocketException e) {
			e.printStackTrace();
		}
        InetAddress local = null;
        try {
            // 换成服务器端IP
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
	 * 函数定义：接收服务端通过UDP协议返回的信息
	 * 参数含义：
	 * datagramSocket：发送数据时设置的与服务端所建立的连接
	 * 
	 * */
	public void reseveMsg(final DatagramSocket datagramSocket){
    	byte[] buf_recv;
        buf_recv = new byte[1024];
        try {
            DatagramPacket pack = new DatagramPacket(buf_recv,buf_recv.length);
            datagramSocket.setSoTimeout(6000);//设置超时，超过此时间未接到信息，socket自动解除阻塞，继续运行下面的函数
            datagramSocket.receive(pack); // 接收消息
            byte[] packData = pack.getData();//获取接收到的数据
            String strMsg=new String(packData).trim();
            Log.i(tag, "UdpTest;msg.obj:"+strMsg);
            Message msg = new Message();
            if (strMsg.equalsIgnoreCase(UdpConstant.returnResoutOk) || strMsg.endsWith(UdpConstant.returnResoutOk)) {
            	msg.what = UdpConstant.ReceiveResultOk;//成功接收数据
			}else if (strMsg.equalsIgnoreCase(UdpConstant.returnResoutNull)) {
				msg.what = UdpConstant.ReceiveResultNull;//发送的数据为空值，但也返回了接收成功的信息
			}else if (strMsg.equalsIgnoreCase(UdpConstant.returnResoutError)) {
				msg.what = UdpConstant.ReceiveResultError;//发送的数据包丢失部分数据
			}else{
				msg.what = UdpConstant.ReceiveResultOther;//其他未知的反馈信息
			}
            msg.obj = strMsg;
            handler.sendMessage(msg);
            Arrays.fill(buf_recv, (byte) 0); // 清零
        }catch(IOException e){
        	Message msg = new Message();
        	msg.what = UdpConstant.ReceiveResultTimeOut;//其他未知的反馈信息
        	msg.obj = "";
            handler.sendMessage(msg);
        }
	}
	
	Handler handler = new Handler() {
		@Override
	    public void handleMessage(Message msg) {

	        switch (msg.what) {
	        case UdpConstant.UdpSending://正在发送数据
	            Toast.makeText(getApplicationContext(), "正在发送数据:" + (String) msg.obj, 2000).show(); // 展示接收到的字符串
	            setBiaoZhi(UdpConstant.UdpSending);
	    		getBiaoZhi();
	            break;
	        case UdpConstant.ReceiveResultOk://数据发送成功（在接收函数中检查）
	            Toast.makeText(getApplicationContext(), "数据发送[成功]!", 2000).show(); // 展示接收到的字符串
	            setBiaoZhi(UdpConstant.ReceiveResultOk);
	    		getBiaoZhi();
	            break;
	        case UdpConstant.ReceiveResultNull://数据发送失败（在接收函数中检查）
	            Toast.makeText(getApplicationContext(), "数据发送[失败]!", 2000).show(); // 展示接收到的字符串
	            setBiaoZhi(UdpConstant.ReceiveResultNull);
	    		getBiaoZhi();
	            break;
	        case UdpConstant.ReceiveResultTimeOut://数据接收超时（在接收函数中检查）
	            Toast.makeText(getApplicationContext(), "接收数据超时，无法判断信息是否发送成功！", 2000).show(); // 展示接收到的字符串
	            setBiaoZhi(UdpConstant.ReceiveResultTimeOut);
	    		getBiaoZhi();
	            break;
	        default:
	        	Toast.makeText(getApplicationContext(), "存在其他错误，请检查！", 2000).show(); // 展示接收到的字符串
	        	setBiaoZhi(UdpConstant.ReceiveResultOther);
	    		getBiaoZhi();
	            break;
	        }
	        
	        super.handleMessage(msg);
	    }
	};
}
