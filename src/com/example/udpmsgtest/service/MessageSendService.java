package com.example.udpmsgtest.service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.udpmsgtest.BaseActivity;
import com.example.udpmsgtest.publicfunction.Constant;
import com.example.udpmsgtest.publicfunction.JSONKey;
import com.example.udpmsgtest.publicfunction.PublicFunctions;
import com.example.udpmsgtest.publicfunction.Tools;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class MessageSendService extends Service {
	private final static String TAG = "MessageSendService";
//	private PublicFunctions pubFun = null;
	public final static int SMS_SEND_FAILED = 0;
	public final static int SMS_SEND_SUCCESS = 1;
	private final static int SAVEMESSAGEVALUE = 3;
	//UDP
	private WifiManager.MulticastLock lock;
	private WifiManager manager;
	private final static int RECEIVELONGESTTIIME = 30000;//���շ�����Ϣ��ȴ�ʱ��;30����
	private Handler handlerFromActivity = null;
	private final static int MOSTTRYCOUNT = 20; //��ೢ��20�Σ���10�����ڷ��Ͳ��ɹ�����ʾ�û�
	
	@Override
	public void onCreate() {//���ڴ����߳�
        manager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        lock= manager.createMulticastLock("lockwifi");
        super.onCreate();
        
    }
	
	 @Override
	 public void onStart(Intent intent, int startId){
		 super.onStart(intent, startId);
		Tools.Log(TAG, "onStart");
		try{
			Intent getIntent = intent;
			String message = getIntent.getStringExtra("smsMessage");
			boolean needSave = false;
			String saveDBStr = getIntent.getStringExtra("saveDBStr");
			String messageType = getIntent.getStringExtra("messageType");
			if (messageType != null && messageType.equalsIgnoreCase(JSONKey.MESSAGE)) {
				int state = getIntent.getIntExtra("isNeedSave", 0);
				if (state == 1) {
					needSave = true;
				}
			}
			sendBYUDP(0, message, needSave, messageType, saveDBStr);
		}catch(Exception e){
			e.printStackTrace();
		}
	 }
	 

	@Override
	public void onDestroy() {
		super.onDestroy();
		Tools.Log(TAG, "onDestroy");
		// Service����ֹ��ͬʱҲֹͣ��ʱ����������
	}

	private void sendBYUDP(final int tryTime,final String sendStr,final boolean needSave,final String messageType,final String saveDBStr){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int tryTim = addMyTryTime(tryTime);
				PublicFunctions pubFun = new PublicFunctions();
				String IP = pubFun.getIp();//��ȡ�ֻ���ǰ��ip
				Tools.Log(TAG, "ClientApp.instance.getIp()=" + IP);
				if(IP == null){
					return;
				}
				DatagramSocket s = null;
				InetSocketAddress socketAddress = new InetSocketAddress(IP, Constant.UDP_CLIENT_PORT); // �󶨱��ط��Ͷ˿�
				try {
					s = new DatagramSocket(socketAddress);
				} catch (SocketException e) {
					e.printStackTrace();
				}
				
				Tools.Log(TAG, "s.getIp()=" + s.getLocalAddress().toString());
				
				lock.acquire(); //����㲥����  
		        send(sendStr,s);
				
				//To receive 
			    listener(tryTim,sendStr,needSave,messageType,saveDBStr,s);
			    lock.release();
			    try {
					s.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SMS_SEND_FAILED:
				final int nowTryTime = msg.arg1;
				final String sendStr = msg.getData().getString("data");
				final String saveDBStr = msg.getData().getString("saveDBStr");
				if(nowTryTime < MOSTTRYCOUNT){ //���ٴγ���
					final boolean isSave = msg.getData().getBoolean("needSave");
					final String messageType = msg.getData().getString("messageType");
					if(sendStr != null){
						new Thread(new Runnable() {
							@Override
							public void run() {
								sendBYUDP(nowTryTime,sendStr,isSave,messageType,saveDBStr);
							}
						}).start();
					}
				}else{//????????????????????????????????????????
//					Intent intent = new Intent(BaseActivity.sendUDPState);  
//					intent.putExtra("sendState",SMS_SEND_FAILED);
//					intent.putExtra("messageType",msg.getData().getString("messageType"));
//					intent.putExtra("saveDBStr",msg.getData().getString("saveDBStr"));
//					intent.putExtra("needSave",msg.getData().getBoolean("needSave"));
//					sendBroadcast(intent);  
				}
				break;
			case SMS_SEND_SUCCESS:
				String messageType1 = msg.getData().getString("messageType");
				if(messageType1 != null){
					String type = null;
					if(messageType1.equalsIgnoreCase(JSONKey.MESSAGE)){
						type = "�ٱ�";
					}else if(messageType1.equalsIgnoreCase(JSONKey.LINGDAOJIANCHA)){
						type = "�쵼���";
					}
					Toast.makeText(getApplicationContext(), type+"��Ϣ���ͳɹ�", Toast.LENGTH_LONG).show();
				}
				break;
			case SAVEMESSAGEVALUE://��Ϣ���ͳɹ���������Ϣ�������ݿ�
				Bundle data = msg.getData();
				final String messageValue = data.getString("saveDBStr");
				final String messageType = data.getString("messageType");
				final int saveType = msg.arg1;
				new Thread(new Runnable() {
					
					@Override
					public void run() {
//						SMSEntity sms = new SMSEntity();
//						sms.setContent(messageValue);
//						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
//						String nowDate = df.format(new Date());
//						sms.setDate(nowDate);
//						SharedPreferences preference = getSharedPreferences(Constant.sharePerferceStr, 0);
//			    		String E_ID = preference.getString(Constant.NOW_USER_E_ID, null);
//			    		if(E_ID != null){
//			    			sms.setPhoneNumber(E_ID);
//			    		}
//						InitServer<SMSEntity> smsServer = new InitServer<SMSEntity>(getApplicationContext(), SMSEntity.class);
//						smsServer.insertTrainClient(sms);
//						if(saveType == 1){ //������������־
//							InitServer<CwRiZhiName> server = new InitServer<CwRiZhiName>(getApplicationContext(), CwRiZhiName.class);
//							Map<String, Object> params = new HashMap();
//							params.put("Dangtang", true);
//							String GroupCode = null;
//							if(ClientApp.instance.mUser != null ){
//								GroupCode = ClientApp.instance.mUser.getGroupNameOut();
//							}else{
//								ClientApp.instance.mUser = ClientApp.instance.getUser();
//								if(ClientApp.instance.mUser != null){
//									GroupCode = ClientApp.instance.mUser.getGroupNameOut();
//								}
//							}
//							if(GroupCode != null){
//								params.put("GroupCode", GroupCode);
//								Tools.Log(TAG, "GroupCode="+GroupCode);
//							}
//							List<CwRiZhiName> nameList = server.getClientInfoList(params);
//							if(nameList != null && nameList.size() > 0){
//								InitServer<CwReport16> server1 = new InitServer<CwReport16>(getApplicationContext(), CwReport16.class);
//								Map<String, Object> params1 = new HashMap();
//								params1.put("ReportCode", nameList.get(0).getReportCode());
//								List<CwReport16> reportList = server1.getClientInfoList(params1);
//								//JC;KS;AQ;SB;QT;
//								String saveMessageStr = "";
//								if(messageValue.startsWith("LSTC+")){
//									saveMessageStr = messageValue.substring(5);
//								}else{
//									saveMessageStr = messageValue.substring(3);
//								}
//								
//								if(reportList != null && reportList.size() > 0){
//									CwReport16 report = reportList.get(0);
//									String oldQiTa = report.getQiTa();
//									if(oldQiTa == null){
//										oldQiTa = saveMessageStr;
//									}else{
//										oldQiTa = oldQiTa +"\n\r\n\r"+ saveMessageStr;
//									}
//									report.setQiTa(oldQiTa);
//									boolean updateState = server1.updateObject(report);
//								}else{
//									CwReport16 report = new CwReport16();
//									report.setReportCode(nameList.get(0).getReportCode());
//									GetJsonFromDB getUUID = new GetJsonFromDB();
//									String uuid = getUUID.creatNewCode_uuid();
//									report.setCode(uuid);
//									report.setBiaoZhi(0);
//									report.setQiTa(saveMessageStr);
//									boolean updateState = server1.insertTrainClient(report);
//									Tools.Log(TAG,"updateState="+updateState);
//								}
//							}
//						}
//						brocastSuccess(messageType);
					}
				}).start();
				
				break;
			}
		}
	};
	
	/*
	 * �������ܣ�����udpЭ���Ƿ񷵻�������
	 * 
	 * */
	//UDP
	public void listener(int nowTryTime,String sendStr,boolean needSave,String messageType,String saveDBStr,DatagramSocket datagramSocket)  {
        // ���յ��ֽڴ�С����xlk������ˣ��ͻ��˷��͵����ݲ��ܳ��������С
        byte[] message = new byte[Constant.UDPRETURNSUCCESS.length];
        try {
            	DatagramPacket datagramPacket = new DatagramPacket(message,message.length);//׼���ý�������ʹ�õ����ݰ�
			try {
				// ׼����������
				Tools.Log(TAG, "׼������");
				datagramSocket.receive(datagramPacket);//��������
				byte[] packData = datagramPacket.getData();//��ȡ���յ�������
				if (mHandler != null && packData != null) {//�ж������Ƿ���յ����Լ��߳��Ƿ����ɴ���
					boolean returnSuccess = false;//���ڱ�ʶ�Ƿ���ճɹ�
					try{
						PublicFunctions pf = new PublicFunctions();
						
						int result = pf.byteArrayToInt(packData);//�����յ�������ת����Ϊָ����int��
						int successResult = pf.byteArrayToInt(Constant.UDPRETURNSUCCESS);//��ԭ��ָ��������ת����int����
						if(result == successResult){//�ж����������Ƿ���ͬ�������ͬ�����ʾ���ͳɹ�
							returnSuccess = true;
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					if (returnSuccess) { // ���ͳɹ�
						Tools.Log(TAG, "���ͳɹ���");
						if(messageType != null && messageType.equalsIgnoreCase(JSONKey.MESSAGE)){//�ж���Ϣ�������Ƿ��������ٱ���Ϣ
							Message msg = new Message();
							msg.what = SAVEMESSAGEVALUE;
							try{
								if(needSave){//�ж��Ƿ�����Ҫ���浽���ݿ���
									msg.arg1 = 1;
								}else{
									msg.arg1 = 0;
								}
								Bundle data = new Bundle();
								data.putString("data", sendStr);//�����͵���Ϣ����
								data.putString("messageType", messageType);//���͵���Ϣ����
								data.putString("saveDBStr", saveDBStr);
								msg.setData(data);//����Ϣ���
								mHandler.sendMessage(msg);
							}catch(Exception e){
								e.printStackTrace();
							}
						}else{//���������Ϣ�����Ͳ����ڻ��߲�������Ҫ����Ϣ
							Message msg = new Message();
							msg.what = SMS_SEND_SUCCESS;
							Bundle data = new Bundle();
							data.putString("data", sendStr);
							data.putString("messageType", messageType);
							data.putString("saveDBStr", saveDBStr);
							msg.setData(data);
							mHandler.sendMessage(msg);
						}
					} 
				}
			} catch (IOException e) {// IOException
				sendHandlerFailed(nowTryTime, sendStr, needSave, messageType, saveDBStr);
                e.printStackTrace();
            }finally{
//            	this.lock.release();
//                datagramSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendHandlerFailed(nowTryTime, sendStr, needSave, messageType, saveDBStr);
        }
    }
    /*
     * �������ܣ�������Ϣ��ָ���ķ����
     * 
     * */
    public static void send(String message,DatagramSocket s) {
    	Tools.Log(TAG, "send:"+message);
        message = (message == null ? "Hello tky" : message);//��Ϣ��������ڣ���ֱ�ӷ���Ĭ����Ϣ
        Tools.Log(TAG, "UDP��������:"+message);
        
        //��ȡ����˵�ip
        InetAddress local = null;
        try {
    		local = InetAddress.getByName(Constant.BASE_IP);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //-------------------------------------------------------------------------
        PublicFunctions pf = new PublicFunctions();
        String imei = pf.getDeviceMeesage();//��ȡ�豸�ı�ʶ��
        int msg_length = (imei+message).length();
        byte[] messageByte;
		try {
			messageByte = (imei+message).getBytes("UTF-8");//���豸��ʶ����Ϣ�ϲ���һ��ת����byte����
			DatagramPacket p = new DatagramPacket(messageByte, messageByte.length, local,Constant.UDP_SERVER_PORT);//������Ҫ���͵����ݰ�
			try {
				s.send(p);//�������ݰ�
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
    }

    
    private void brocastSuccess(String messageType){
    	Message msg = new Message();
    	msg.what = SMS_SEND_SUCCESS;
    	Bundle data = new Bundle();
		data.putString("messageType", messageType);
		msg.setData(data);
		mHandler.sendMessage(msg);
    }
    
    private void sendHandlerFailed(int nowTryTime,String sendStr,boolean needSave,String messageType,String saveDBStr){
    	 Message msg = new Message();
			msg.what = SMS_SEND_FAILED; //��ʱ����ʧ�ܣ������·��ͣ������ѷ���ʧ��
			msg.arg1 = nowTryTime;
			Bundle data = new Bundle();
			data.putString("data", sendStr);
			data.putBoolean("saveMessage", needSave);
			data.putString("messageType", messageType);
			data.putString("saveDBStr", saveDBStr);
			msg.setData(data);
			mHandler.sendMessage(msg);
    }
    
    private int addMyTryTime(int tryTime){
    	return tryTime + 1;
    }
}