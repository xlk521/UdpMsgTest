package com.example.udpmsgtest.login.initdata;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.udpmsgtest.login.entity.BureauEntity;
import com.example.udpmsgtest.login.entity.CrewEntity;
import com.example.udpmsgtest.login.entity.DeptEntity;
import com.example.udpmsgtest.login.entity.GroupEntity;
import com.example.udpmsgtest.login.entity.TeamEntity;
import com.example.udpmsgtest.login.entity.TrainLeaderTime;
import com.example.udpmsgtest.login.publics.LoginPublicFunction;
import com.example.udpmsgtest.server.InitServer;

import android.content.Context;

public class LoginInitDataFunction {

	public Context context = null;
	public LoginInitDataFunction(){
		
	}
	public LoginInitDataFunction(Context con){
		this.context = con;
	}
	/*@author:DerekXie
	 * �������ܣ����ļ�����ת��Ϊjsonarray��ʽ������
	 * �������壺
	 * fileContent: �ļ�����
	 * ����ֵ��
	 * 	dataArray��JsonArray���͵�����
	 * */
	public JSONArray getDataArray(String fileContent){
		JSONArray dataArray = null;
		try {
			//��ȡ����JSONArray������
			String fileJsonStr = fileContent.substring(fileContent.indexOf("["), fileContent.lastIndexOf("]")+1);
			dataArray = new JSONArray(fileJsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataArray;
	}
	/*@author:DerekXie
	 * �������ܣ������ݽ��������ݿ���:����·����Ϣ
	 * �������壺
	 * filePath: �ļ�����
	 * */
	public boolean parseJcBureau(String filePath){
		boolean isOk = false;
		try {
			String fileContent = "";
			LoginPublicFunction lpf = new LoginPublicFunction();
			fileContent = lpf.readFile(filePath);//��ȡ�ļ�����
			if (fileContent != null && !fileContent.equals("")){

				JSONArray dataArray = getDataArray(fileContent );//���ļ�ת��Ϊjson���ݸ�ʽ
				ArrayList<BureauEntity> list = new ArrayList<BureauEntity>();
				for (int i = 0; i < dataArray.length(); i++) {//��������ӵ���Ӧ�ı����
					JSONObject jsonObj = dataArray.getJSONObject(i);
					BureauEntity bureau = new BureauEntity();
					bureau.setId(jsonObj.getInt("ID"));
					bureau.setBureauCode(jsonObj.getString("BureauCode"));
					bureau.setBureauName(jsonObj.getString("BureauName"));
					bureau.setOrderID(jsonObj.getString("OrderID"));
					list.add(bureau);
				}
				if(list != null && list.size() > 0){//������ͬ�������ݿ���
					InitServer<BureauEntity> initServer = new InitServer<BureauEntity>(context, BureauEntity.class);
					boolean initState = initServer.initTrain(list);
					if(initState){
						//���ļ�����ɾ��
						isOk = lpf.isDeleteFile(filePath);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return isOk;
	}
	/*@author:DerekXie
	 * �������ܣ������ݽ��������ݿ���:�������˶���Ϣ
	 * �������壺
	 * filePath: �ļ�����
	 * */
	public boolean parseJcDept(String filePath){
		boolean isOk = false;
		try {
			String fileContent = "";
			LoginPublicFunction lpf = new LoginPublicFunction();
			fileContent = lpf.readFile(filePath);//��ȡ�ļ�����
			if (fileContent != null && !fileContent.equals("")){

				JSONArray dataArray = getDataArray(fileContent);//���ļ�ת��Ϊjson���ݸ�ʽ
				ArrayList<DeptEntity> list = new ArrayList<DeptEntity>();
				for (int i = 0; i < dataArray.length(); i++) {//��������ӵ���Ӧ�ı����
					JSONObject obj = dataArray.getJSONObject(i);
					DeptEntity bureau = new DeptEntity();
					bureau.setId(obj.getInt("ID"));
					bureau.setBureauCode(obj.getString("BureauCode"));
					bureau.setDeptCode(obj.getString("DeptCode"));
					bureau.setDeptName(obj.getString("DeptName"));
					list.add(bureau);
				}
				if(list != null && list.size() > 0){//������ͬ�������ݿ���
					InitServer<DeptEntity> initServer = new InitServer<DeptEntity>(context, DeptEntity.class);
					boolean initState = initServer.initTrain(list);
					if(initState){
						//���ļ�����ɾ��
						isOk = lpf.isDeleteFile(filePath);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return isOk;
	}
	/*@author:DerekXie
	 * �������ܣ������ݽ��������ݿ���:������Ա��Ϣ
	 * �������壺
	 * filePath: �ļ�����
	 * */
	public boolean parseJcEmployee(String filePath){
		boolean isOk = false;
		try {
			String fileContent = "";
			LoginPublicFunction lpf = new LoginPublicFunction();
			fileContent = lpf.readFile(filePath);//��ȡ�ļ�����
			if (fileContent != null && !fileContent.equals("")){

				JSONArray dataArray = getDataArray(fileContent);//���ļ�ת��Ϊjson���ݸ�ʽ
				ArrayList<CrewEntity> list = new ArrayList<CrewEntity>();
				for (int i = 0; i < dataArray.length(); i++) {//��������ӵ���Ӧ�ı����
					JSONObject obj = dataArray.getJSONObject(i);
					CrewEntity crew = new CrewEntity();
					crew = new CrewEntity();
					crew.setE_ID(obj.getString("E_ID"));
					crew.setE_Name(obj.getString("E_Name")); 
					crew.setTeamNameOut(obj.getString("TeamNameOut")); 
					crew.setE_PNumber(obj.getString("E_PNumber")); 
					crew.setTrainSign((obj.getString("TrainSign")).toUpperCase()); 
					crew.setGroupNameOut(obj.getString("GroupNameOut"));
					crew.setE_Position(obj.getString("E_Position"));
//					crew.setBureauName(obj.getString("BureauName"));
//					crew.setDeptname(obj.getString("Deptname"));
					crew.setPassword(obj.getString("Password"));
//					crew.setTeamID(obj.getString("TeamID"));
					list.add(crew);
				}
				if(list != null && list.size() > 0){//������ͬ�������ݿ���
					InitServer<CrewEntity> initServer = new InitServer<CrewEntity>(context, CrewEntity.class);
					boolean initState = initServer.initTrain(list);
					if(initState){
						//���ļ�����ɾ��
						isOk = lpf.isDeleteFile(filePath);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return isOk;
	}
	/*@author:DerekXie
	 * �������ܣ������ݽ��������ݿ���:����������Ϣ
	 * �������壺
	 * filePath: �ļ�����
	 * */
	public boolean parseJcGroup(String filePath){
		boolean isOk = false;
		try {
			String fileContent = "";
			LoginPublicFunction lpf = new LoginPublicFunction();
			fileContent = lpf.readFile(filePath);//��ȡ�ļ�����
			if (fileContent != null && !fileContent.equals("")){

				JSONArray dataArray = getDataArray(fileContent);//���ļ�ת��Ϊjson���ݸ�ʽ
				ArrayList<GroupEntity> list = new ArrayList<GroupEntity>();
				for (int i = 0; i < dataArray.length(); i++) {//��������ӵ���Ӧ�ı����
					JSONObject obj = dataArray.getJSONObject(i);
					GroupEntity group = null;
					try {
						group = new GroupEntity();
						group.setGroupID(obj.getString("GroupID"));
						group.setGroupName(obj.getString("GroupName"));
						group.setTeamName(obj.getString("TeamName"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if(group != null){
						list.add(group);
					}
				}
				if(list != null && list.size() > 0){//������ͬ�������ݿ���
					InitServer<GroupEntity> initServer = new InitServer<GroupEntity>(context, GroupEntity.class);
					boolean initState = initServer.initTrain(list);
					if(initState){
						//���ļ�����ɾ��
						isOk = lpf.isDeleteFile(filePath);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return isOk;
	}
	/*@author:DerekXie
	 * �������ܣ������ݽ��������ݿ���:����������Ϣ
	 * �������壺
	 * filePath: �ļ�����
	 * */
	public boolean parseJcTeam(String filePath){
		boolean isOk = false;
		try {
			String fileContent = "";
			LoginPublicFunction lpf = new LoginPublicFunction();
			fileContent = lpf.readFile(filePath);//��ȡ�ļ�����
			if (fileContent != null && !fileContent.equals("")){
				JSONArray dataArray = getDataArray(fileContent);//���ļ�ת��Ϊjson���ݸ�ʽ
				ArrayList<TeamEntity> list = new ArrayList<TeamEntity>();
				for (int i = 0; i < dataArray.length(); i++) {//��������ӵ���Ӧ�ı����
					JSONObject obj = dataArray.getJSONObject(i);
					TeamEntity team = null;
					try {
						team = new TeamEntity();
						team.setId(Integer.parseInt(obj.getString("TeamID")));
						team.setTeam_ID(obj.getString("TeamID"));
						team.setDeptCode(obj.getString("DeptCode"));
						team.setTeam_Name(obj.getString("TeamName"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(team != null){
						list.add(team);
					}
				}
				if(list != null && list.size() > 0){//������ͬ�������ݿ���
					InitServer<TeamEntity> initServer = new InitServer<TeamEntity>(context, TeamEntity.class);
					boolean initState = initServer.initTrain(list);
					if(initState){
						//���ļ�����ɾ��
						isOk = lpf.isDeleteFile(filePath);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return isOk;
	}
	/*@author:DerekXie
	 * �������ܣ������ݽ��������ݿ���:����ʱ����Ϣ
	 * �������壺
	 * filePath: �ļ�����
	 * */
	public boolean parseJcTime(String filePath){
		boolean isOk = false;
		try {
			String fileContent = "";
			LoginPublicFunction lpf = new LoginPublicFunction();
			fileContent = lpf.readFile(filePath);//��ȡ�ļ�����
			if (fileContent != null && !fileContent.equals("")) {
				JSONArray dataArray = getDataArray(fileContent);//���ļ�ת��Ϊjson���ݸ�ʽ
				ArrayList<TrainLeaderTime> list = new ArrayList<TrainLeaderTime>();
				for (int i = 0; i < dataArray.length(); i++) {//��������ӵ���Ӧ�ı����
					JSONObject obj = dataArray.getJSONObject(i);
					TrainLeaderTime time = null;
					try {
						time = new TrainLeaderTime();
						time.setId(obj.getInt("ID"));
						time.setTrainName(obj.getString("TrainName"));
						time.setStationID(obj.getInt("StationID"));
						time.setStationName(obj.getString("StationName"));
						time.setArriveTime(obj.getString("ArriveTime"));
						time.setStartTime(obj.getString("StartTime"));
						time.setDays(obj.getString("Days"));
						time.setTrainSign((obj.getString("TrainSign")).toUpperCase());
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if(time != null){
						list.add(time);
					}
				}
				if(list != null && list.size() > 0){//������ͬ�������ݿ���
					InitServer<TrainLeaderTime> initServer = new InitServer<TrainLeaderTime>(context, TrainLeaderTime.class);
					boolean initState = initServer.initTrain(list);
					if(initState){
						//���ļ�����ɾ��
						isOk = lpf.isDeleteFile(filePath);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return isOk;
	}
}
