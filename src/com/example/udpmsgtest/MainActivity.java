package com.example.udpmsgtest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;

import com.example.udpmsgtest.publicfunction.JSONKey;
import com.example.udpmsgtest.publicfunction.PublicFunctions;
import com.example.udpmsgtest.service.MessageSendService;
import com.example.udpmsgtest.service.TestService;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends Activity {

	private String tag = "MainActivity";
	private Button button = null;
	//UDP
	private WifiManager.MulticastLock lock;
	private WifiManager manager;
	public static final int androidPort = 8088;
	public static final int ServicePort = 8088;
	private PublicFunctions pubFun = null;
	private String host = "192.168.42.25";//服务器端的ip地址
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button = (Button)this.findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(getApplicationContext(),TestService.class);
				Intent intent = new Intent(getApplicationContext(),MessageSendService.class);
				String sendStr = "{'upload_data':{'DeptCode':'JNK','GroupName':'济京五组','devno':'COM1','recvdatetime':'2014-03-03 14:41:33','tpa':'13964183328','msg':'CGSF+2073+梁山(0)，现在车内0人。测试，济京五组列车长张冬梅','smid':'1','sign':'0','mmsfilesid':'','smstype':'S','BureauName':'济南铁路局','BureauCode':'K','E_ID':'济南客运段','Deptname':'88024'},'DeptCode':'JNK','flag':'SB'}";
				intent.putExtra("smsMessage", sendStr);
				intent.putExtra("messageType", JSONKey.LINGDAOJIANCHA);
				intent.putExtra("saveDBStr", "tipStr");
				startService(intent);
			}
		});
	}

	/*author:DerekXie
	 * 参数含义：
	 * 
	 * 
	 * */
	public void UdpTest(){
		//udp网络通讯及线程
		DatagramSocket datagramSocket = null;
		//获取当前的ip
		String ip = pubFun.getIp();
		Toast.makeText(this, "ip="+pubFun.getIp(), 2000).show();
		if (ip == null) {
			return;
		}
		//建立特定的套接字地址
		InetSocketAddress socketAddress = new InetSocketAddress(ip,androidPort);
		Toast.makeText(this, "01", 2000).show();
		//初始化udp socket，绑定到特定的套接字地址
		try {
			datagramSocket = new DatagramSocket(socketAddress);
			String message = "xlk";
			try {
				//建立远程服务器地址
				Inet4Address address = (Inet4Address)Inet4Address.getByName(host);
				Toast.makeText(this, "01", 2000).show();
				//创建数据包
				byte[] data;
				try {
					Log.i(tag, "UdpTest;message:"+message);
					data = message.getBytes("UTF-8");
					DatagramPacket packet = new DatagramPacket(data, data.length, address, ServicePort);
					//申请广播开启  
					lock.acquire();
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
		                
		                Toast.makeText(MainActivity.this, "UdpTest;msg.obj:"+msg.obj, 2000).show();
		                handler.sendMessage(msg); // 发送消息
		                Arrays.fill(buf_recv, (byte) 0); // 清零
		            } catch (IOException e) {
		                Toast.makeText(MainActivity.this, e.getMessage(), 0).show();
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
	            Toast.makeText(MainActivity.this, "rece data:" + (String) msg.obj, 2000).show(); // 展示接收到的字符串
	            break;
	        default:
	        	Toast.makeText(MainActivity.this, "rece data:?", 2000).show(); // 展示接收到的字符串
	            break;
	        }

	        super.handleMessage(msg);
	    }
	};
}
