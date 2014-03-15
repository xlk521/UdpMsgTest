package com.example.udpmsgtest.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;

import com.example.udpmsgtest.basestation.activity.MainActivity;
import com.example.udpmsgtest.publicfunction.Constant;
import com.example.udpmsgtest.publicfunction.PublicFunctions;
import com.example.udpmsgtest.publicfunction.Tools;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class TestService extends Service{
	//UDP
	private WifiManager.MulticastLock lock;
	private WifiManager manager;
	private String tag = "xlk";
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		manager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
	    lock= manager.createMulticastLock("lockwifi");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		UdpTest();
	}
	public void UdpTest(){
		//申请广播开启  
		lock.acquire();
		//udp网络通讯及线程
		DatagramSocket datagramSocket = null;
		//获取当前的ip
		PublicFunctions pubFun = new PublicFunctions();
		String ip = pubFun.getIp();
		Toast.makeText(this, "ip="+pubFun.getIp(), 2000).show();
		if (ip == null) {
			return;
		}
		//建立特定的套接字地址
		InetSocketAddress socketAddress = new InetSocketAddress(ip,8000);
		Toast.makeText(this, "01", 2000).show();
		//初始化udp socket，绑定到特定的套接字地址
		try {
			datagramSocket = new DatagramSocket(socketAddress);
			String message = "xlk111111";
			try {
				//建立远程服务器地址
				InetAddress address = (InetAddress)InetAddress.getByName(Constant.BASE_IP);
				Toast.makeText(this, "01", 2000).show();
				//创建数据包
				byte[] data;
				try {
					Log.i(tag, "UdpTest;message:"+message);
					data = message.getBytes("UTF-8");
					DatagramPacket packet = new DatagramPacket(data, data.length, address, Constant.UDP_SERVER_PORT);
					
					//发送数据包
					Log.i(tag, "UdpTest;sending;");
					Toast.makeText(this, "UdpTest;sending;", 2000).show();
					datagramSocket.send(packet);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
			// 接收数据handler (线程)
			reseveMsg(datagramSocket);
			lock.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void reseveMsg(final DatagramSocket datagramSocket){
		Tools.Log(tag, "准备接受");
		Thread thread = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	byte[] buf_recv; // receive buffer
		        buf_recv = new byte[1024];
		        while (true) {
		            try {
		                DatagramPacket pack = new DatagramPacket(buf_recv,buf_recv.length);
		                Message msg = new Message();
		                msg.what = 1;//Constants.RECV_DATA; // 消息id
		                datagramSocket.receive(pack); // 接收消息
		                CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder(); // gb2312
		                msg.obj = decoder.decode(ByteBuffer.wrap(buf_recv)).toString();  // 转换的字符串附加到消息上
		                Log.i(tag, "UdpTest;msg.obj:"+msg.obj);
		                
		                handler.sendMessage(msg); // 发送消息
		                Arrays.fill(buf_recv, (byte) 0); // 清零
		            } catch (IOException e) {
//		                Toast.makeText(MainActivity.this, e.getMessage(), 0).show();
		            }
		        }
		    }
		});
	}
	Handler handler = new Handler() {
		@Override
	    public void handleMessage(Message msg) {
	        switch (msg.what) {
	        case 1:
	            Toast.makeText(getApplicationContext(), "rece data:" + (String) msg.obj, 2000).show(); // 展示接收到的字符串
	            break;
	        default:
	        	Toast.makeText(getApplicationContext(), "rece data:?", 2000).show(); // 展示接收到的字符串
	            break;
	        }

	        super.handleMessage(msg);
	    }
	};
}
