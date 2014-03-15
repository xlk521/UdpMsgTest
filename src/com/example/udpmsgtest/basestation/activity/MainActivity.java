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
			//测试定位信息
			@Override
			public void onClick(View v) {
				
				PublicFunctions pubFun = new PublicFunctions(MainActivity.this);
		        String IP = pubFun.getIp();//获取手机当前的ip
		        //判定条件为：是否连接到网络，是否获取到有效的ip地址
		        //(bsFunction.isMobileConnected(getApplicationContext()) || bsFunction.isWIFIConnected(getApplicationContext())) && 
				if(IP != null){
					//获取当前的基站信息并且对其进行发送
					//此处是通过时钟定时启动UDPService服务来进行数据发送的
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
			//测试解压缩
			@Override
			public void onClick(View v) {
				LoginUnzippedFunction luf = new LoginUnzippedFunction(MainActivity.this);
				LoginPublicFunction lpf = new LoginPublicFunction(MainActivity.this);
				boolean isSdExist = lpf.isSdCardExist();
				if (isSdExist) {
					int isUnzipFile = luf.getUnzipFile(LoginContent.initFolder, LoginContent.initFileZip);
					switch (isUnzipFile) {
					/*
					 * 0表示失败，
	 * 				1表示成功，
	 * 				-1表示文件不存在，
	 * 				-2表示文件夹不存在
	 * 				-3表示没有传入有效的Context
					 * */
					case -3:
						Toast.makeText(MainActivity.this, "没有传入有效的Context", 2000).show();
						break;
					case -2:
						Toast.makeText(MainActivity.this, "文件夹不存在", 2000).show();
						break;
					case -1:
						Toast.makeText(MainActivity.this, "文件不存在", 2000).show();
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, LoginActivity.class);
						startActivity(intent);
						break;
					case 0:
						Toast.makeText(MainActivity.this, "解压失败", 2000).show();
						break;
					case 1:
						Toast.makeText(MainActivity.this, "解压成功", 2000).show();
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
						Toast.makeText(MainActivity.this, "未知错误", 2000).show();
						break;
					}
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//创建数据库
		dataFunction.creatDB();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (isBinded) {
			unbindService(mConnection);//解除UdpService绑定
			isBinded = false;
		}
		//判断service是否正在运行
		boolean isServiceRuning = isServiceRun(getApplicationContext());
		//结束service
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
	 * 函数定义：按钮点击事件，用于进行service的绑定以及数据的发送
	 * 参数定义：无参数
	 * */
    public void clickFunction(){
    	if (lock.isHeld()) {
			lock.release();
		}
    	//判断service是否正在运行
		boolean isServiceRuning = isServiceRun(getApplicationContext());
		
		String textStr = text.getText().toString();
		Log.i("MainActivity", textStr);
		//获取当前的基站信息
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
	 * 函数定义：检测是否获取到UdpService的对象
	 * 参数定义：无参数
	 * */
    public void isUdpService(){
    	isBinded = true;
    	if (udpService == null) {
			Log.i(tag, "MainActivity;does not get an udpService:null");
		}else{
			Log.i(tag, "MainActivity;Get an udpService!");
			//开启一个进程，用于监听服务端的信息
			runThread();
		}
    }
    
	/*@author:DerekXie
	 * 函数定义：创建线程，用于检测UdpService发送的数据
	 * 参数定义：无参数
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
								udpService.setBiaoZhi(-10);//将数据初始化
								Message msg = new Message();
								msg.what = biaozhi;
								handler.sendMessage(msg);
							}
							Thread.sleep(1000);//暂停1s
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
	        	showProgress("正在发送数据··· ···");
	        	isUdpService();
	        	break;
	        case UdpConstant.ReceiveResultOk:
	        	dismessProgress();
	        	if (isBinded) {
	        		Log.i(tag, "isBinded:ture");
	    			unbindService(mConnection);//解除UdpService绑定
	    			isBinded = false;
	    		}
	            Toast.makeText(getApplicationContext(), "Sending Succeed!", 2000).show(); // 展示接收到的字符串
	            break;
	        case UdpConstant.ReceiveResultNull:
	        	dismessProgress();
	        	if (isBinded) {
	    			unbindService(mConnection);//解除UdpService绑定
	    			isBinded = false;
	    		}
	        	Toast.makeText(getApplicationContext(), "The data is null!", 2000).show(); // 展示接收到的字符串
	        	break;
	        case UdpConstant.ReceiveResultTimeOut:
	        	dismessProgress();
	        	if (isBinded) {
	    			unbindService(mConnection);//解除UdpService绑定
	    			isBinded = false;
	    		}
	        	Toast.makeText(getApplicationContext(), "Out of time!", 2000).show(); // 展示接收到的字符串
	        	break;
	        case UdpConstant.ReceiveResultOther:
	        	dismessProgress();
	        	if (isBinded) {
	    			unbindService(mConnection);//解除UdpService绑定
	    			isBinded = false;
	    		}
	        	Toast.makeText(getApplicationContext(), "other error!", 2000).show(); // 展示接收到的字符串
	        	break;
	        case UdpConstant.getPhoneIp:
	        	Toast.makeText(getApplicationContext(), "Can not get the ip of my phone!Please check the Web!", 2000).show(); // 展示接收到的字符串
	        	break;
	        default:
	        	dismessProgress();
	        	if (isBinded) {
	    			unbindService(mConnection);//解除UdpService绑定
	    			isBinded = false;
	    		}
	        	Toast.makeText(getApplicationContext(), "rece data:?", 2000).show(); // 展示接收到的字符串
	            break;
	        }
	        super.handleMessage(msg);
	    }
	};
	/*@author:DerekXie
	 * 函数定义：判断service是否正在运行
	 * 参数定义：无参数
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {//设置返回键的功能为后台运行
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
