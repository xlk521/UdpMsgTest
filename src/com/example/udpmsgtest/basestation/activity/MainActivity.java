package com.example.udpmsgtest.basestation.activity;

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
import java.util.List;

import com.example.udpmsgtest.BaseActivity;
import com.example.udpmsgtest.R;
import com.example.udpmsgtest.R.id;
import com.example.udpmsgtest.R.layout;
import com.example.udpmsgtest.basestation.BaseStationFunction;
import com.example.udpmsgtest.basestation.BaseStationFunction.SCell;
import com.example.udpmsgtest.login.LoginActivity;
import com.example.udpmsgtest.login.publics.LoginContent;
import com.example.udpmsgtest.login.publics.LoginPublicFunction;
import com.example.udpmsgtest.login.publics.LoginUnzippedFunction;
import com.example.udpmsgtest.publicfunction.Constant;
import com.example.udpmsgtest.publicfunction.JSONKey;
import com.example.udpmsgtest.publicfunction.PublicFunctions;
import com.example.udpmsgtest.publicfunction.Tools;
import com.example.udpmsgtest.publicfunction.UdpConstant;
import com.example.udpmsgtest.service.MessageSendService;
import com.example.udpmsgtest.service.TestService;
import com.example.udpmsgtest.service.UdpService;
import com.example.udpmsgtest.service.UdpService.UdpBinder;
import com.example.udpmsgtest.wenjian.DataFunction;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.KeyEvent;
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
	private DataFunction dataFunction = null;
	private UdpService udpService;
	private boolean isBinded = false;
	private BaseStationFunction bsFunction = null;
	private Button unZippedBtn = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		manager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        lock= manager.createMulticastLock("lockwifi");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dataFunction = new DataFunction(this);
		
		button = (Button)this.findViewById(R.id.button1);
		unZippedBtn = (Button)this.findViewById(R.id.button2);
		text = (EditText)this.findViewById(R.id.text);
		button.setOnClickListener(new OnClickListener() {
			//���Զ�λ��Ϣ
			@Override
			public void onClick(View v) {
				
				PublicFunctions pubFun = new PublicFunctions(MainActivity.this);
		        String IP = pubFun.getIp();//��ȡ�ֻ���ǰ��ip
		        //�ж�����Ϊ���Ƿ����ӵ����磬�Ƿ��ȡ����Ч��ip��ַ
		        //(bsFunction.isMobileConnected(getApplicationContext()) || bsFunction.isWIFIConnected(getApplicationContext())) && 
				if(IP != null){
					//��ȡ��ǰ�Ļ�վ��Ϣ���Ҷ�����з���
					//�˴���ͨ��ʱ�Ӷ�ʱ����UDPService�������������ݷ��͵�
					pubFun.sendDataOnClick();
					Log.i("IP", "IP="+IP);
				}else{
					Message msg = new Message();
					msg.what = UdpConstant.getPhoneIp;
					handler.sendMessage(msg);
				}
			}
		});
		unZippedBtn.setOnClickListener(new OnClickListener() {
			//���Խ�ѹ��
			@Override
			public void onClick(View v) {
				LoginUnzippedFunction luf = new LoginUnzippedFunction(MainActivity.this);
				LoginPublicFunction lpf = new LoginPublicFunction(MainActivity.this);
				boolean isSdExist = lpf.isSdCardExist();
				if (isSdExist) {
					int isUnzipFile = luf.getUnzipFile(LoginContent.initFolder, LoginContent.initFileZip);
					switch (isUnzipFile) {
					/*
					 * 0��ʾʧ�ܣ�
	 * 				1��ʾ�ɹ���
	 * 				-1��ʾ�ļ������ڣ�
	 * 				-2��ʾ�ļ��в�����
	 * 				-3��ʾû�д�����Ч��Context
					 * */
					case -3:
						Toast.makeText(MainActivity.this, "û�д�����Ч��Context", 2000).show();
						break;
					case -2:
						Toast.makeText(MainActivity.this, "�ļ��в�����", 2000).show();
						break;
					case -1:
						Toast.makeText(MainActivity.this, "�ļ�������", 2000).show();
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, LoginActivity.class);
						startActivity(intent);
						break;
					case 0:
						Toast.makeText(MainActivity.this, "��ѹʧ��", 2000).show();
						break;
					case 1:
						Toast.makeText(MainActivity.this, "��ѹ�ɹ�", 2000).show();
						boolean isDelFile = lpf.isDeleteFile(LoginContent.initFileZip);
						if (isDelFile) {
							Log.i(tag, "unZippedBtn.setOnClickListener;case 1;Success in delete zipFile!");
						}else{
							Log.i(tag, "unZippedBtn.setOnClickListener;case 1;Fail in delete zipFile!");
						}
						Intent intent2 = new Intent();
						intent2.setClass(MainActivity.this, LoginActivity.class);
						startActivity(intent2);
						break;
					default:
						Toast.makeText(MainActivity.this, "δ֪����", 2000).show();
						break;
					}
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//�������ݿ�
		dataFunction.creatDB();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (isBinded) {
			unbindService(mConnection);//���UdpService��
			isBinded = false;
		}
		//�ж�service�Ƿ���������
		boolean isServiceRuning = isServiceRun(getApplicationContext());
		//����service
		if (isServiceRuning) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), UdpService.class);
			stopService(intent);
		}
	}

	private ServiceConnection mConnection = new ServiceConnection() {  
		@Override
		public void onServiceConnected(ComponentName className,IBinder udpBinder) {  
			udpService = ((UdpBinder) udpBinder).getService();
			isUdpService();
        }

		@Override
        public void onServiceDisconnected(ComponentName arg0) {  
            udpService = null;  
        }
    };

    /*@author:DerekXie
	 * �������壺��ť����¼������ڽ���service�İ��Լ����ݵķ���
	 * �������壺�޲���
	 * */
    public void clickFunction(){
    	if (lock.isHeld()) {
			lock.release();
		}
    	//�ж�service�Ƿ���������
		boolean isServiceRuning = isServiceRun(getApplicationContext());
		
		String textStr = text.getText().toString();
		Log.i("MainActivity", textStr);
		//��ȡ��ǰ�Ļ�վ��Ϣ
		BaseStationFunction bsFunction = new BaseStationFunction(this);
		String sendStr = bsFunction.getSendStr(0);

		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), UdpService.class);
		intent.putExtra(UdpConstant.SbMsg, sendStr);
		intent.putExtra(UdpConstant.SbMsgType, UdpConstant.SbType);
		intent.putExtra(UdpConstant.SbSaveDbStr, sendStr);
		intent.putExtra(UdpConstant.SbIsNeedSave, 0);
		startService(intent);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
    
	/*@author:DerekXie
	 * �������壺����Ƿ��ȡ��UdpService�Ķ���
	 * �������壺�޲���
	 * */
    public void isUdpService(){
    	isBinded = true;
    	if (udpService == null) {
			Log.i(tag, "MainActivity;does not get an udpService:null");
		}else{
			Log.i(tag, "MainActivity;Get an udpService!");
			//����һ�����̣����ڼ�������˵���Ϣ
			runThread();
		}
    }
    
	/*@author:DerekXie
	 * �������壺�����̣߳����ڼ��UdpService���͵�����
	 * �������壺�޲���
	 * 
	 * */
	public void runThread(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					boolean isOk = true;
					while(isOk){
						try {
							int biaozhi = udpService.getBiaoZhi();
							Log.i(tag, "biaozhi:"+biaozhi);
							if (biaozhi == -10) {
								
							}else{
								isOk = false;
								udpService.setBiaoZhi(-10);//�����ݳ�ʼ��
								Message msg = new Message();
								msg.what = biaozhi;
								handler.sendMessage(msg);
							}
							Thread.sleep(1000);//��ͣ1s
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					Log.i(tag, "thread interupt! error!");
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	Handler handler = new Handler() {
		@Override
	    public void handleMessage(Message msg) {
	        switch (msg.what) {
	        case UdpConstant.UdpSending:
	        	showProgress("���ڷ������ݡ����� ������");
	        	isUdpService();
	        	break;
	        case UdpConstant.ReceiveResultOk:
	        	dismessProgress();
	        	if (isBinded) {
	        		Log.i(tag, "isBinded:ture");
	    			unbindService(mConnection);//���UdpService��
	    			isBinded = false;
	    		}
	            Toast.makeText(getApplicationContext(), "Sending Succeed!", 2000).show(); // չʾ���յ����ַ���
	            break;
	        case UdpConstant.ReceiveResultNull:
	        	dismessProgress();
	        	if (isBinded) {
	    			unbindService(mConnection);//���UdpService��
	    			isBinded = false;
	    		}
	        	Toast.makeText(getApplicationContext(), "The data is null!", 2000).show(); // չʾ���յ����ַ���
	        	break;
	        case UdpConstant.ReceiveResultTimeOut:
	        	dismessProgress();
	        	if (isBinded) {
	    			unbindService(mConnection);//���UdpService��
	    			isBinded = false;
	    		}
	        	Toast.makeText(getApplicationContext(), "Out of time!", 2000).show(); // չʾ���յ����ַ���
	        	break;
	        case UdpConstant.ReceiveResultOther:
	        	dismessProgress();
	        	if (isBinded) {
	    			unbindService(mConnection);//���UdpService��
	    			isBinded = false;
	    		}
	        	Toast.makeText(getApplicationContext(), "other error!", 2000).show(); // չʾ���յ����ַ���
	        	break;
	        case UdpConstant.getPhoneIp:
	        	Toast.makeText(getApplicationContext(), "Can not get the ip of my phone!Please check the Web!", 2000).show(); // չʾ���յ����ַ���
	        	break;
	        default:
	        	dismessProgress();
	        	if (isBinded) {
	    			unbindService(mConnection);//���UdpService��
	    			isBinded = false;
	    		}
	        	Toast.makeText(getApplicationContext(), "rece data:?", 2000).show(); // չʾ���յ����ַ���
	            break;
	        }
	        super.handleMessage(msg);
	    }
	};
	/*@author:DerekXie
	 * �������壺�ж�service�Ƿ���������
	 * �������壺�޲���
	 * */
	public boolean isServiceRun(Context context){
	     ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
	     List<RunningServiceInfo> list = am.getRunningServices(30);
	     for(RunningServiceInfo info : list){
	         if(info.service.getClassName().equals("com.example.udpmsgtest.service.UdpService")){
	        	 return true;
	         }
	    }
	    return false;
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//���÷��ؼ��Ĺ���Ϊ��̨����
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
