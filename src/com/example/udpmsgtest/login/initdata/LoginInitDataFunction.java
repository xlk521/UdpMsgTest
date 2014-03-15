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
	 * 函数功能：将文件内容转换为jsonarray格式的数据
	 * 参数定义：
	 * fileContent: 文件内容
	 * 返回值：
	 * 	dataArray：JsonArray类型的数据
	 * */
	public JSONArray getDataArray(String fileContent){
		JSONArray dataArray = null;
		try {
			//截取符合JSONArray的内容
			String fileJsonStr = fileContent.substring(fileContent.indexOf("["), fileContent.lastIndexOf("]")+1);
			dataArray = new JSONArray(fileJsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataArray;
	}
	/*@author:DerekXie
	 * 函数功能：将数据解析到数据库中:解析路局信息
	 * 参数定义：
	 * filePath: 文件内容
	 * */
	public boolean parseJcBureau(String filePath){
		boolean isOk = false;
		try {
			String fileContent = "";
			LoginPublicFunction lpf = new LoginPublicFunction();
			fileContent = lpf.readFile(filePath);//读取文件内容
			if (fileContent != null && !fileContent.equals("")){

				JSONArray dataArray = getDataArray(fileContent );//将文件转换为json数据格式
				ArrayList<BureauEntity> list = new ArrayList<BureauEntity>();
				for (int i = 0; i < dataArray.length(); i++) {//将数据添加到对应的表格中
					JSONObject jsonObj = dataArray.getJSONObject(i);
					BureauEntity bureau = new BureauEntity();
					bureau.setId(jsonObj.getInt("ID"));
					bureau.setBureauCode(jsonObj.getString("BureauCode"));
					bureau.setBureauName(jsonObj.getString("BureauName"));
					bureau.setOrderID(jsonObj.getString("OrderID"));
					list.add(bureau);
				}
				if(list != null && list.size() > 0){//将数据同步到数据库中
					InitServer<BureauEntity> initServer = new InitServer<BureauEntity>(context, BureauEntity.class);
					boolean initState = initServer.initTrain(list);
					if(initState){
						//将文件进行删除
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
	 * 函数功能：将数据解析到数据库中:解析客运段信息
	 * 参数定义：
	 * filePath: 文件内容
	 * */
	public boolean parseJcDept(String filePath){
		boolean isOk = false;
		try {
			String fileContent = "";
			LoginPublicFunction lpf = new LoginPublicFunction();
			fileContent = lpf.readFile(filePath);//读取文件内容
			if (fileContent != null && !fileContent.equals("")){

				JSONArray dataArray = getDataArray(fileContent);//将文件转换为json数据格式
				ArrayList<DeptEntity> list = new ArrayList<DeptEntity>();
				for (int i = 0; i < dataArray.length(); i++) {//将数据添加到对应的表格中
					JSONObject obj = dataArray.getJSONObject(i);
					DeptEntity bureau = new DeptEntity();
					bureau.setId(obj.getInt("ID"));
					bureau.setBureauCode(obj.getString("BureauCode"));
					bureau.setDeptCode(obj.getString("DeptCode"));
					bureau.setDeptName(obj.getString("DeptName"));
					list.add(bureau);
				}
				if(list != null && list.size() > 0){//将数据同步到数据库中
					InitServer<DeptEntity> initServer = new InitServer<DeptEntity>(context, DeptEntity.class);
					boolean initState = initServer.initTrain(list);
					if(initState){
						//将文件进行删除
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
	 * 函数功能：将数据解析到数据库中:解析人员信息
	 * 参数定义：
	 * filePath: 文件内容
	 * */
	public boolean parseJcEmployee(String filePath){
		boolean isOk = false;
		try {
			String fileContent = "";
			LoginPublicFunction lpf = new LoginPublicFunction();
			fileContent = lpf.readFile(filePath);//读取文件内容
			if (fileContent != null && !fileContent.equals("")){

				JSONArray dataArray = getDataArray(fileContent);//将文件转换为json数据格式
				ArrayList<CrewEntity> list = new ArrayList<CrewEntity>();
				for (int i = 0; i < dataArray.length(); i++) {//将数据添加到对应的表格中
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
				if(list != null && list.size() > 0){//将数据同步到数据库中
					InitServer<CrewEntity> initServer = new InitServer<CrewEntity>(context, CrewEntity.class);
					boolean initState = initServer.initTrain(list);
					if(initState){
						//将文件进行删除
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
	 * 函数功能：将数据解析到数据库中:解析班组信息
	 * 参数定义：
	 * filePath: 文件内容
	 * */
	public boolean parseJcGroup(String filePath){
		boolean isOk = false;
		try {
			String fileContent = "";
			LoginPublicFunction lpf = new LoginPublicFunction();
			fileContent = lpf.readFile(filePath);//读取文件内容
			if (fileContent != null && !fileContent.equals("")){

				JSONArray dataArray = getDataArray(fileContent);//将文件转换为json数据格式
				ArrayList<GroupEntity> list = new ArrayList<GroupEntity>();
				for (int i = 0; i < dataArray.length(); i++) {//将数据添加到对应的表格中
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
				if(list != null && list.size() > 0){//将数据同步到数据库中
					InitServer<GroupEntity> initServer = new InitServer<GroupEntity>(context, GroupEntity.class);
					boolean initState = initServer.initTrain(list);
					if(initState){
						//将文件进行删除
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
	 * 函数功能：将数据解析到数据库中:解析车队信息
	 * 参数定义：
	 * filePath: 文件内容
	 * */
	public boolean parseJcTeam(String filePath){
		boolean isOk = false;
		try {
			String fileContent = "";
			LoginPublicFunction lpf = new LoginPublicFunction();
			fileContent = lpf.readFile(filePath);//读取文件内容
			if (fileContent != null && !fileContent.equals("")){
				JSONArray dataArray = getDataArray(fileContent);//将文件转换为json数据格式
				ArrayList<TeamEntity> list = new ArrayList<TeamEntity>();
				for (int i = 0; i < dataArray.length(); i++) {//将数据添加到对应的表格中
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
				if(list != null && list.size() > 0){//将数据同步到数据库中
					InitServer<TeamEntity> initServer = new InitServer<TeamEntity>(context, TeamEntity.class);
					boolean initState = initServer.initTrain(list);
					if(initState){
						//将文件进行删除
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
	 * 函数功能：将数据解析到数据库中:解析时间信息
	 * 参数定义：
	 * filePath: 文件内容
	 * */
	public boolean parseJcTime(String filePath){
		boolean isOk = false;
		try {
			String fileContent = "";
			LoginPublicFunction lpf = new LoginPublicFunction();
			fileContent = lpf.readFile(filePath);//读取文件内容
			if (fileContent != null && !fileContent.equals("")) {
				JSONArray dataArray = getDataArray(fileContent);//将文件转换为json数据格式
				ArrayList<TrainLeaderTime> list = new ArrayList<TrainLeaderTime>();
				for (int i = 0; i < dataArray.length(); i++) {//将数据添加到对应的表格中
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
				if(list != null && list.size() > 0){//将数据同步到数据库中
					InitServer<TrainLeaderTime> initServer = new InitServer<TrainLeaderTime>(context, TrainLeaderTime.class);
					boolean initState = initServer.initTrain(list);
					if(initState){
						//将文件进行删除
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
