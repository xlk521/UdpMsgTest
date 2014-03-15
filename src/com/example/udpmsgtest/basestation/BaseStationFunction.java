package com.example.udpmsgtest.basestation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.udpmsgtest.entity.GpsEntity;
import com.example.udpmsgtest.http.HttpRequester;
import com.example.udpmsgtest.http.HttpRespons;
import com.example.udpmsgtest.publicfunction.JSONKey;
import com.example.udpmsgtest.server.InitServer;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

/*@author:DerekXie
 * �ඨ�壺�йػ�վ��Ϣ�Ĺ��ܺ���
 * ͨ��TelephonyManager ��ȡlac:mcc:mnc:cell-id����վ��Ϣ���Ľ��ͣ�
	 MCC��Mobile Country Code���ƶ����Ҵ��루�й���Ϊ460����
	 MNC��Mobile Network Code���ƶ�������루�й��ƶ�Ϊ0���й���ͨΪ1���й�����Ϊ2���� 
	 LAC��Location Area Code��λ�������룻
	 CID��Cell Identity����վ��ţ�
	 BSSS��Base station signal strength����վ�ź�ǿ��
 * */
public class BaseStationFunction {

	private String tag = "BaseStationFunction";
	public Context context;
	public BaseStationFunction(Context con){
		this.context = con;
	}
	/*@author:DerekXie
	 * �������壺��ȡ��Ҫ���͵�����
	 * �������壺
	 * biaoshi����ʶλ���趨Ϊʼ�������˳���Ϣ
	 * */
	public String getSendStr(int biaoshi){
		String sendStr = "";
		SCell cell;
		try {
			cell = getBaseStation();
			if (cell == null) {
				Log.i(tag, "getSendStr;cell;null");
			}else{
				JSONObject uploadSqlStr = getSendData(cell, biaoshi);
				if (uploadSqlStr != null) {
					Log.i(tag, "getSendStr;getSendData;uploadSqlStr��"+uploadSqlStr);
					JSONObject obj = new JSONObject();
					try {
						obj.put(JSONKey.FLAG, JSONKey.DW);
						obj.put(JSONKey.DEPTCODE, "SJP"); // ���˶�;
						obj.put(JSONKey.DATA, uploadSqlStr);
						if(uploadSqlStr != null){
							sendStr = obj.toString();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sendStr;
	}
	//
	public SCell getBaseStation() throws Exception{
		SCell cell = new SCell();//���浱ǰ��վ����Ϣ
		TelephonyManager telephoneManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		//����ֵMCC+MNC
		String operator = telephoneManager.getNetworkOperator();
		cell.MCC = Integer.parseInt(operator.substring(0, 3));
		cell.MNC = getSimType(telephoneManager);//Integer.parseInt(operator.substring(3));
		int lac = 0;//λ��������
		int cellId = 0;//��վ���
		if (cell.MNC == 03) {
			//�й�����
			CdmaCellLocation location = (CdmaCellLocation)telephoneManager.getCellLocation();
			if (location == null) {
				cell = null;
				throw new Exception("��ȡ���Ż�վ��Ϣʧ��");
			}
			cell.LAC = location.getNetworkId();
			cell.CID = location.getBaseStationId();
			cell.CID /= 16;
			//���Ż�վ��Ϣ���Ѿ����˾�γ�ȣ�������ȡ
			double lat = (double) location.getBaseStationLatitude() / 14400;
			double lng = (double) location.getBaseStationLongitude() / 14400;
			cell.LAT = lat;
			cell.LNG = lng;
			if(cell.LNG > 136 || cell.LNG < 72 || cell.LAT > 54 || cell.LAT < 2){
				return null;
			}
		}else{
			//�й��ƶ����й���ͨ��ȡLAC/CID�ķ�ʽ
			GsmCellLocation location = (GsmCellLocation)telephoneManager.getCellLocation();
			//��ȡ������վ��Ϣ
			List<NeighboringCellInfo> infos = telephoneManager.getNeighboringCellInfo();
			if (location == null) {//����޷���ȡ��ǰ�Ļ�վ��Ϣ
				if (infos != null && infos.size() > 0) {
					if(infos.get(0).getCid() != -1 && infos.get(0).getCid() != 0){
						cell.CID = infos.get(0).getCid();
						cell.LAC = infos.get(0).getLac();
					}else{
						cell = null;
					}
				}else{
					cell = null;
					throw new Exception("��ȡ�ƶ�����ͨ��վ��Ϣʧ��");
				}
			}else{
				cell.LAC = location.getLac();
				cell.CID = location.getCid();
			}
			//������Ϣ����ȡһ��ȷʵ���õĻ�վ��Ϣ������������������������������������������ɶ��˼
			SCell nearCell = null;//new SCell();
			if(infos != null && infos.size() > 0){
				for(NeighboringCellInfo info: infos){
					if(nearCell==null){
						nearCell = new SCell();
						nearCell.LAC = info.getLac();
						nearCell.CID = info.getCid();
						nearCell.MCC = cell.MCC;
						nearCell.MNC = cell.MNC;
						nearCell.PHONENUM = cell.PHONENUM;
					}else{//��ȡһ����Ÿ�С�Ļ�վ��Ϣ���������Ը��õĴ������л�ȡ�侭γ��
						if(info.getCid() < nearCell.CID){
							nearCell = new SCell();
							nearCell.LAC = info.getLac();
							nearCell.CID = info.getCid();
							nearCell.MCC = cell.MCC;
							nearCell.MNC = cell.MNC;
							nearCell.PHONENUM = cell.PHONENUM;
						}
					}
				}
			}else{
				nearCell = null;
			}
			if(nearCell != null && nearCell.CID != -1 && nearCell.CID < cell.CID){
				cell = nearCell;
			}
			//�ɵ������ݿ��в�ѯ�û�վ��Ӧ�ľ�γ����Ϣ
			InitServer<GpsEntity> server = new InitServer<GpsEntity>(context, GpsEntity.class);
			Map<String, Object> params = new HashMap();
			params.put("MCC", cell.MCC);
			params.put("MNC", cell.MNC);
			params.put("CID", cell.CID);
			params.put("LAC", cell.LAC);
			List<GpsEntity> alreadList = server.getClientInfoList(params);
			if(alreadList != null && alreadList.size() > 0){
				cell.LAT = alreadList.get(0).getLAT();
				cell.LNG = alreadList.get(0).getLNG();
			}else{
				SCell cellHasLng = getLngFromWeb(cell);
				if(cellHasLng != null){
					cell = cellHasLng;
				}
			}
		}
		return cell;
	}
	public SCell getLngFromWeb(SCell cell){//ͨ�������ȡ�û�վ�ľ�γ��
		try {
            HttpRequester request = new HttpRequester();
            HttpRespons hr = request.sendGet("http://www.lbscell.cn/lbsapi.aspx?type="+cell.MNC+"&lac="+cell.LAC+"&cell="+cell.CID);  
            if(hr.content != null){
            	try {
					JSONObject objResult = new JSONObject(hr.content);
					String lat = objResult.getString("lat");
					String lng = objResult.getString("lng");
					if(lat != null && lng != null){
						cell.LAT = Double.valueOf(lat);
						cell.LNG = Double.valueOf(lng);
					}
					//����ȡ�Ļ�վ��Ϣ���浽���ݿ���
					GpsEntity gps = new GpsEntity();
					gps.setCID(cell.CID);
					gps.setMCC(cell.MCC);
					gps.setMNC(cell.MNC);
					gps.setLAC(cell.LAC);
					gps.setLAT(cell.LAT);
					gps.setLNG(cell.LNG);
					InitServer<GpsEntity> server = new InitServer<GpsEntity>(context, GpsEntity.class);
					boolean state = server.insertTrainClient(gps);
				} catch (JSONException e) {
					e.printStackTrace();
					return null;
				}
            }else{
            	return null;
            }
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return cell;
	}
	/*@author��DerekXie
	 * �������ܣ�����ǰ�ֻ��������
	 * 
	 * */
	public int getSimType(TelephonyManager tm){
		int type = 0;
		/**
		 * ��ȡSIM����IMSI�� SIM��Ψһ��ʶ��IMSI �����ƶ��û�ʶ���루IMSI��International Mobile
		 * Subscriber Identification Number���������ƶ��û��ı�־��
		 * ������SIM���У������������ƶ��û�����Ч��Ϣ��IMSI��MCC��MNC��MSIN��ɣ�����MCCΪ�ƶ����Һ��룬��3λ������ɣ�
		 * Ψһ��ʶ���ƶ��ͻ������Ĺ��ң��ҹ�Ϊ460��MNCΪ����id����2λ������ɣ�
		 * ����ʶ���ƶ��ͻ����������ƶ����磬�й��ƶ�Ϊ00���й���ͨΪ01,�й�����Ϊ03��MSINΪ�ƶ��ͻ�ʶ���룬���õȳ�11λ���ֹ��ɡ�
		 * Ψһ��ʶ�����GSM�ƶ�ͨ�������ƶ��ͻ�������Ҫ�������ƶ�������ͨ��ֻ��ȡ��SIM���е�MNC�ֶμ���
		 */
		String imsi = tm.getSubscriberId();
		if (imsi != null) {
			Log.i(tag, "getCellType;imsi=" + imsi);
			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {// ��Ϊ�ƶ�������46000�µ�IMSI�Ѿ����꣬����������һ��46002��ţ�134/159�Ŷ�ʹ���˴˱��
				// �й��ƶ�
				type = 00;
			}else if (imsi.startsWith("46001")){
				// �й���ͨ
				type = 01;
			}else if (imsi.startsWith("46003")){
				// �й�����
				type = 03;
			}
		}
		return type;
	}
	//�ж��ֻ������Ƿ����
	public boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isConnectedOrConnecting();//mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	
	//�ж��ֻ�wifi�Ƿ����
	public boolean isWIFIConnected(Context context){
		if (context != null) {
			 WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			 if(wifiManager.isWifiEnabled()){
				 ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				 NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				 return mWiFiNetworkInfo.isConnected();
			 }else{
				 return false;
			 }
		}
		return false;
	}
	
	/*@author:DerekXie
	 * �������壺��װ��Ҫ���͵�����
	 * �������壺
	 * cell��������վ��Ϣ���ࣨ����
	 * biaoshi�����ڱ�ǳ��ǻ����˳˵ı�ʶ
	 * */
	public JSONObject getSendData(SCell cell, int biaoshi){
		//��ȡ��ǰ������
		SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//�������ڸ�ʽ
		String newTime = simpleDataFormat.format(new Date());
		JSONObject dataObj = null;
		try {
			if(cell.MNC == 3) {//�����ֻ�
				dataObj = new JSONObject();
				dataObj.put(JSONKey.DW_CID, cell.CID);
				dataObj.put(JSONKey.DW_MNC, cell.MNC);
				dataObj.put(JSONKey.DW_LAC, cell.LAC);
				dataObj.put(JSONKey.DW_TYPE, "ZTA");
				dataObj.put(JSONKey.DW_TIME, newTime);
				dataObj.put(JSONKey.DW_CHECI, "0001");//trainNumber
				dataObj.put(JSONKey.DW_TEL,"13720014633");//PhoneNum
				dataObj.put(JSONKey.DW_BANZU,"102");//groupCode
				dataObj.put(JSONKey.DW_BIAOSHI,biaoshi);
				dataObj.put(JSONKey.DW_LNG,cell.LNG);
				dataObj.put(JSONKey.DW_LAT,cell.LAT);
				dataObj.put(JSONKey.DW_BUREAUCODE,"P");//bureauCode
				dataObj.put(JSONKey.DW_DEPTCODE,"SJP");//deptCode
				dataObj.put(JSONKey.DW_TEAMCODE,"7");//teamCode
				dataObj.put(JSONKey.DW_GROUPCODE,"102");//groupCode
				dataObj.put(JSONKey.USER_EID, "8947");//E_ID;
			}else{
				dataObj = new JSONObject();
				dataObj.put(JSONKey.DW_CID, cell.CID);
				dataObj.put(JSONKey.DW_MNC, cell.MNC);
				dataObj.put(JSONKey.DW_LAC, cell.LAC);
				dataObj.put(JSONKey.DW_TYPE, "ZTA");
				dataObj.put(JSONKey.DW_TIME, newTime);
				dataObj.put(JSONKey.DW_CHECI, "0001");
				dataObj.put(JSONKey.DW_TEL,"13720014633");
				dataObj.put(JSONKey.DW_BANZU,"102");
				dataObj.put(JSONKey.DW_BIAOSHI,biaoshi);
				dataObj.put(JSONKey.DW_LNG,cell.LNG);
				dataObj.put(JSONKey.DW_LAT,cell.LAT);
				dataObj.put(JSONKey.DW_BUREAUCODE,"P");
				dataObj.put(JSONKey.DW_DEPTCODE,"SJP");
				dataObj.put(JSONKey.DW_TEAMCODE,"7");
				dataObj.put(JSONKey.DW_GROUPCODE,"102");
				dataObj.put(JSONKey.USER_EID, "8947");//E_ID;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataObj;
	}
	/**
	 * ��վ��Ϣ�ṹ�� MCC��Mobile Country Code���ƶ����Ҵ��루�й���Ϊ460���� MNC��Mobile Network
	 * Code���ƶ�������루�й��ƶ�Ϊ00���й���ͨΪ01���� LAC��Location Area Code��λ�������룻 CID��Cell
	 * Identity����վ��ţ��Ǹ�16λ�����ݣ���Χ��0��65535����
	 * */
	public class SCell {
		public int MCC;//�ƶ����Ҵ���
		public int MNC;//�ƶ��������
		public int LAC;//λ��������
		public int CID;//��վ���
		public double LNG = 0.0;//��γ��
		public double LAT = 0.0;
		public String PHONENUM=null;//�ֻ���
	}
	
}
