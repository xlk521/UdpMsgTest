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
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends Activity {

	private String tag = "MainActivity";
	private Button button = null;
	private EditText text = null;
	//UDP
	private WifiManager.MulticastLock lock;
	private WifiManager manager;
	public static final int androidPort = 8088;
	public static final int ServicePort = 8088;
	private PublicFunctions pubFun = null;
	private String host = "192.168.1.111";//�������˵�ip��ַ
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		manager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        lock= manager.createMulticastLock("lockwifi");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button = (Button)this.findViewById(R.id.button1);
		text = (EditText)this.findViewById(R.id.text);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
////				Intent intent = new Intent(getApplicationContext(),TestService.class);
//				Intent intent = new Intent(getApplicationContext(),MessageSendService.class);
//				String sendStr = "{'upload_data':{'DeptCode':'JNK','GroupName':'�þ�����','devno':'COM1','recvdatetime':'2014-03-03 14:41:33','tpa':'13964183328','msg':'CGSF+2073+��ɽ(0)�����ڳ���0�ˡ����ԣ��þ������г����Ŷ�÷','smid':'1','sign':'0','mmsfilesid':'','smstype':'S','BureauName':'������·��','BureauCode':'K','E_ID':'���Ͽ��˶�','Deptname':'88024'},'DeptCode':'JNK','flag':'SB'}";
//				intent.putExtra("smsMessage", sendStr);
//				intent.putExtra("messageType", JSONKey.LINGDAOJIANCHA);
//				intent.putExtra("saveDBStr", "tipStr");
//				startService(intent);
//				UdpMain();
//				UdpTest();
//				send("123321");
				
				new Thread(runnable).start();
			}
		});
	}
	Handler handler2 = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String val = data.getString("value");
	        Log.e("mylog","������-->" + val);
	    }
	};

	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
	        //
	        // TODO: http request.
	        //
	    	String textStr = "xlk123";
	    	textStr = text.getText().toString();
	    	if (textStr == null) {
				textStr = "text";
			}
	    	send(textStr);
	        Message msg = new Message();
	        Bundle data = new Bundle();
	        data.putString("value","������!");
	        msg.setData(data);
	        handler2.sendMessage(msg);
	    }
	};
	public static void send(String message){  
        message = (message == null ? "Hello IdeasAndroid!" : message);  
        int server_port = 8088;  
        DatagramSocket s = null;  
        try {
            s = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress local = null;
        try {
            // ���ɷ�������IP  
            local = InetAddress.getByName("192.168.1.111"); 
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        }  
        int msg_length = message.length();
        byte[] messageByte;
//		try {
			messageByte = message.getBytes();
			DatagramPacket p = new DatagramPacket(messageByte, msg_length, local, server_port);  
	        try {
	            s.send(p);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
    } 
	public void UdpMain() {
	    try {
	      //�������ͷ����׽��֣�IPĬ��Ϊ���أ��˿ں����
	      DatagramSocket sendSocket = new DatagramSocket();
	      
	      //ȷ��Ҫ���͵���Ϣ��
	      String mes = "hello xlk1111";
	      
	      //�������ݱ������������ַ����鴫����ʽ�洢�ģ����Դ�ת����
	      byte[] buf = mes.getBytes();
	      
	      //ȷ�����ͷ���IP��ַ���˿ںţ���ַΪ���ػ�����ַ
	      int port = 8000;
	      InetAddress ip = InetAddress.getByName("192.168.1.111");//InetAddress.getLocalHost();
	      
	      //�����������͵����ݱ���
	      DatagramPacket sendPacket = new DatagramPacket(buf,buf.length,ip,port);
	      
	      //ͨ���׽��ַ������ݣ�
	      sendSocket.send(sendPacket);
	      
	      //ȷ�����ܷ������ݵĻ���洢�������洢���ݵ��ֽ�����
	      byte[] getBuf = new byte[1024];
	      
	      //�����������͵����ݱ�
	      DatagramPacket getPacket = new DatagramPacket(getBuf,getBuf.length);
	      
	      //ͨ���׽��ֽ�������
	      sendSocket.receive(getPacket);
	      
	      //������������Ϣ������ӡ
	      String backMes = new String(getBuf,0,getPacket.getLength());
	      System.out.println("���ܷ����ص���Ϣ��"+backMes);
	      
	      //�ر��׽���
	      sendSocket.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}

	/*author:DerekXie
	 * �������壺
	 * 
	 * 
	 * */
	public void UdpTest(){
		//udp����ͨѶ���߳�
		DatagramSocket datagramSocket = null;
		//��ȡ��ǰ��ip
		String ip = pubFun.getIp();
		Toast.makeText(this, "ip="+pubFun.getIp(), 2000).show();
		if (ip == null) {
			return;
		}
		//�����ض����׽��ֵ�ַ
		InetSocketAddress socketAddress = new InetSocketAddress(ip,androidPort);
		Toast.makeText(this, "01", 2000).show();
		//��ʼ��udp socket���󶨵��ض����׽��ֵ�ַ
		try {
			datagramSocket = new DatagramSocket(socketAddress);
			String message = "xlk";
			try {
				//����Զ�̷�������ַ
				Inet4Address address = (Inet4Address)Inet4Address.getByName(host);
				Toast.makeText(this, "01", 2000).show();
				//�������ݰ�
				byte[] data;
				try {
					Log.i(tag, "UdpTest;message:"+message);
					data = message.getBytes("UTF-8");
					DatagramPacket packet = new DatagramPacket(data, data.length, address, ServicePort);
					//����㲥����  
					lock.acquire();
					//�������ݰ�
					Log.i(tag, "UdpTest;sending;");
					Toast.makeText(this, "UdpTest;sending;", 2000).show();
					datagramSocket.send(packet);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
			// ��������handler (�߳�)
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
		                msg.what = 1;//Constants.RECV_DATA; // ��Ϣid
		                datagramSocket.receive(pack); // ������Ϣ
		                CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder(); // gb2312
		                msg.obj = decoder.decode(ByteBuffer.wrap(buf_recv)).toString();  // ת�����ַ������ӵ���Ϣ��
		                Log.i(tag, "UdpTest;msg.obj:"+msg.obj);
		                
		                Toast.makeText(MainActivity.this, "UdpTest;msg.obj:"+msg.obj, 2000).show();
		                handler.sendMessage(msg); // ������Ϣ
		                Arrays.fill(buf_recv, (byte) 0); // ����
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
	            Toast.makeText(MainActivity.this, "rece data:" + (String) msg.obj, 2000).show(); // չʾ���յ����ַ���
	            break;
	        default:
	        	Toast.makeText(MainActivity.this, "rece data:?", 2000).show(); // չʾ���յ����ַ���
	            break;
	        }

	        super.handleMessage(msg);
	    }
	};
}
