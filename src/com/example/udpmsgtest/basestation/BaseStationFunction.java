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
 * 类定义：有关基站信息的功能函数
 * 通过TelephonyManager 获取lac:mcc:mnc:cell-id（基站信息）的解释：
	 MCC，Mobile Country Code，移动国家代码（中国的为460）；
	 MNC，Mobile Network Code，移动网络号码（中国移动为0，中国联通为1，中国电信为2）； 
	 LAC，Location Area Code，位置区域码；
	 CID，Cell Identity，基站编号；
	 BSSS，Base station signal strength，基站信号强度
 * */
public class BaseStationFunction {

	private String tag = "BaseStationFunction";
	public Context context;
	public BaseStationFunction(Context con){
		this.context = con;
	}
	/*@author:DerekXie
	 * 函数定义：获取将要发送的数据
	 * 参数定义：
	 * biaoshi：标识位，设定为始发还是退乘信息
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
					Log.i(tag, "getSendStr;getSendData;uploadSqlStr："+uploadSqlStr);
					JSONObject obj = new JSONObject();
					try {
						obj.put(JSONKey.FLAG, JSONKey.DW);
						obj.put(JSONKey.DEPTCODE, "SJP"); // 客运段;
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
		SCell cell = new SCell();//储存当前基站的信息
		TelephonyManager telephoneManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		//返回值MCC+MNC
		String operator = telephoneManager.getNetworkOperator();
		cell.MCC = Integer.parseInt(operator.substring(0, 3));
		cell.MNC = getSimType(telephoneManager);//Integer.parseInt(operator.substring(3));
		int lac = 0;//位置区域码
		int cellId = 0;//基站编号
		if (cell.MNC == 03) {
			//中国电信
			CdmaCellLocation location = (CdmaCellLocation)telephoneManager.getCellLocation();
			if (location == null) {
				cell = null;
				throw new Exception("获取电信基站信息失败");
			}
			cell.LAC = location.getNetworkId();
			cell.CID = location.getBaseStationId();
			cell.CID /= 16;
			//电信基站信息中已经给了经纬度，不必再取
			double lat = (double) location.getBaseStationLatitude() / 14400;
			double lng = (double) location.getBaseStationLongitude() / 14400;
			cell.LAT = lat;
			cell.LNG = lng;
			if(cell.LNG > 136 || cell.LNG < 72 || cell.LAT > 54 || cell.LAT < 2){
				return null;
			}
		}else{
			//中国移动和中国联通获取LAC/CID的方式
			GsmCellLocation location = (GsmCellLocation)telephoneManager.getCellLocation();
			//获取临区基站信息
			List<NeighboringCellInfo> infos = telephoneManager.getNeighboringCellInfo();
			if (location == null) {//如果无法获取当前的基站信息
				if (infos != null && infos.size() > 0) {
					if(infos.get(0).getCid() != -1 && infos.get(0).getCid() != 0){
						cell.CID = infos.get(0).getCid();
						cell.LAC = infos.get(0).getLac();
					}else{
						cell = null;
					}
				}else{
					cell = null;
					throw new Exception("获取移动或联通基站信息失败");
				}
			}else{
				cell.LAC = location.getLac();
				cell.CID = location.getCid();
			}
			//整合信息，获取一个确实可用的基站信息？？？？？？？？？？？？？？？？？不明白是啥意思
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
					}else{//获取一个编号更小的基站信息，这样可以更好的从网络中获取其经纬度
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
			//由当地数据库中查询该基站对应的经纬度信息
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
	public SCell getLngFromWeb(SCell cell){//通过网络获取该基站的经纬度
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
					//将获取的基站信息保存到数据库中
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
	/*@author：DerekXie
	 * 函数功能：鉴别当前手机卡的类别
	 * 
	 * */
	public int getSimType(TelephonyManager tm){
		int type = 0;
		/**
		 * 获取SIM卡的IMSI码 SIM卡唯一标识：IMSI 国际移动用户识别码（IMSI：International Mobile
		 * Subscriber Identification Number）是区别移动用户的标志，
		 * 储存在SIM卡中，可用于区别移动用户的有效信息。IMSI由MCC、MNC、MSIN组成，其中MCC为移动国家号码，由3位数字组成，
		 * 唯一地识别移动客户所属的国家，我国为460；MNC为网络id，由2位数字组成，
		 * 用于识别移动客户所归属的移动网络，中国移动为00，中国联通为01,中国电信为03；MSIN为移动客户识别码，采用等长11位数字构成。
		 * 唯一地识别国内GSM移动通信网中移动客户。所以要区分是移动还是联通，只需取得SIM卡中的MNC字段即可
		 */
		String imsi = tm.getSubscriberId();
		if (imsi != null) {
			Log.i(tag, "getCellType;imsi=" + imsi);
			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {// 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
				// 中国移动
				type = 00;
			}else if (imsi.startsWith("46001")){
				// 中国联通
				type = 01;
			}else if (imsi.startsWith("46003")){
				// 中国电信
				type = 03;
			}
		}
		return type;
	}
	//判断手机网络是否可用
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
	
	//判断手机wifi是否可用
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
	 * 函数定义：组装需要发送的数据
	 * 参数定义：
	 * cell：包含基站信息的类（包）
	 * biaoshi：用于标记出城还是退乘的标识
	 * */
	public JSONObject getSendData(SCell cell, int biaoshi){
		//获取当前的日期
		SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String newTime = simpleDataFormat.format(new Date());
		JSONObject dataObj = null;
		try {
			if(cell.MNC == 3) {//电信手机
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
	 * 基站信息结构体 MCC，Mobile Country Code，移动国家代码（中国的为460）； MNC，Mobile Network
	 * Code，移动网络号码（中国移动为00，中国联通为01）； LAC，Location Area Code，位置区域码； CID，Cell
	 * Identity，基站编号，是个16位的数据（范围是0到65535）。
	 * */
	public class SCell {
		public int MCC;//移动国家代码
		public int MNC;//移动网络号码
		public int LAC;//位置区域码
		public int CID;//基站编号
		public double LNG = 0.0;//经纬度
		public double LAT = 0.0;
		public String PHONENUM=null;//手机号
	}
	
}
