package com.example.udpmsgtest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;

import com.example.udpmsgtest.publicfunction.Constant;
import com.example.udpmsgtest.publicfunction.JSONKey;
import com.example.udpmsgtest.publicfunction.PublicFunctions;
import com.example.udpmsgtest.publicfunction.Tools;
import com.example.udpmsgtest.publicfunction.UdpConstant;
import com.example.udpmsgtest.service.MessageSendService;
import com.example.udpmsgtest.service.TestService;
import com.example.udpmsgtest.service.UdpService;

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
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends BaseActivity {

	private String tag = "MainActivity";
	private Button button = null;
	private EditText text = null;
	//UDP
	private WifiManager.MulticastLock lock;
	private WifiManager manager;
	public static final int androidPort = 8088;
	public static final int ServicePort = 8088;
	private PublicFunctions pubFun = null;
	private String host = "192.168.1.111";//服务器端的ip地址
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		manager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
//        lock= manager.createMulticastLock("lockwifi");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button = (Button)this.findViewById(R.id.button1);
		text = (EditText)this.findViewById(R.id.text);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if (lock.isHeld()) {
//					lock.release();
//				}
//				new Thread(runnable).start();//等到线程的主要功能运行结束后，需要结束进程
				showProgress("正在发送数据··· ···");
				String textStr = text.getText().toString();
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), UdpService.class);
				intent.putExtra(UdpConstant.SbMsg, textStr);
				intent.putExtra(UdpConstant.SbMsgType, UdpConstant.SbType);
				intent.putExtra(UdpConstant.SbSaveDbStr, textStr);
				intent.putExtra(UdpConstant.SbIsNeedSave, 0);
				startService(intent);
			}
		});
	}
	Handler handler2 = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String val = data.getString("value");
	        Log.e("mylog","请求结果-->" + val);
	        
	    }
	};

	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
	        //
	        // 通过UDP协议发送数据，并且显示发送结果
	        //
	    	String textStr = "xlk123";
	    	textStr = text.getText().toString();
	    	if (textStr == null) {
				textStr = "text";
			}
	    	String newStr = "Derek+"+textStr+"+Xie";
	    	send(newStr);
	        Message msg = new Message();
	        Bundle data = new Bundle();
	        data.putString("value","请求结果!");
	        msg.setData(data);
	        handler2.sendMessage(msg);
	    }
	};
	/*author:DerekXie
	 * 函数定义：按照UDP协议发送数据
	 * 参数含义：
	 * message：所需要发送的文字信息
	 * 
	 * */
	public void send(String message){
		//------------------------------------------------------------------------------//
        message = (message == null ? "Hello IdeasAndroid!" : message);
        int server_port = 8088;
        PublicFunctions pubFun = new PublicFunctions();
        String IP = pubFun.getIp();//获取手机当前的ip
		if(IP == null){
			return;
		}
		DatagramSocket s = null;
		InetSocketAddress socketAddress = new InetSocketAddress(IP, Constant.UDP_CLIENT_PORT); // 绑定本地发送端口
		try {
			s = new DatagramSocket(socketAddress);
		} catch (SocketException e) {
			e.printStackTrace();
		}
        InetAddress local = null;
        try {
            // 换成服务器端IP
            local = InetAddress.getByName("192.168.1.111");
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
            datagramSocket.receive(pack); // 接收消息
            byte[] packData = pack.getData();//获取接收到的数据
            String strMsg=new String(packData).trim();
            Log.i(tag, "UdpTest;msg.obj:"+strMsg);
            Message msg = new Message();
            msg.what = 1;
            msg.obj = strMsg;
            handler.sendMessage(msg);
            Arrays.fill(buf_recv, (byte) 0); // 清零
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), 0).show();
        }
	}
	Handler handler = new Handler() {
		@Override
	    public void handleMessage(Message msg) {
	        switch (msg.what) {
	        case 1:
	            Toast.makeText(getApplicationContext(), "数据发送成功:" + (String) msg.obj, 2000).show(); // 展示接收到的字符串
	            break;
	        default:
	        	Toast.makeText(getApplicationContext(), "rece data:?", 2000).show(); // 展示接收到的字符串
	            break;
	        }
	        super.handleMessage(msg);
	    }
	};
}
