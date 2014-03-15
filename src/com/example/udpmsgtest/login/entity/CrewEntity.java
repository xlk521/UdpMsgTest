package com.example.udpmsgtest.login.entity;
/**
 *  列车长个人信息（Train_Leader_info�?
 */
import com.example.udpmsgtest.entity.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="JcEmployee")
public class CrewEntity extends BaseEntity{
	private static final long serialVersionUID = 1L;
	@DatabaseField(id=true)
	private String E_ID;//用户�?
	@DatabaseField
	private String E_Name;//姓名
	@DatabaseField
	private String E_PNumber;//手机号码
	@DatabaseField
	private String E_Position;//职位
	@DatabaseField
	private String TeamNameOut;//出乘车队
	@DatabaseField
	private String GroupNameOut;//出乘班组
	@DatabaseField
	private String TrainSign;//车次标识
	@DatabaseField
	private String BureauName;//�?��路局
	@DatabaseField
	private String Deptname;//�?��客运�?
	@DatabaseField
	private String Password;//登录密码
//	@DatabaseField
//	private String TeamID;//�?��车队ID
	
//	public String getTeamID() {
//		return TeamID;
//	}
//
//	public void setTeamID(String teamID) {
//		TeamID = teamID;
//	}
	
	
	//初始化数据使用DB文件时添加的字段
	@DatabaseField
	private String GroupName;
	@DatabaseField
	private String TeamName;
	public String getGroupName() {
		return GroupName;
	}

	public void setGroupName(String groupName) {
		GroupName = groupName;
	}

	public String getTeamName() {
		return TeamName;
	}

	public void setTeamName(String teamName) {
		TeamName = teamName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getE_ID() {
		return E_ID;
	}

	public void setE_ID(String e_ID) {
		E_ID = e_ID;
	}

	public String getE_Name() {
		return E_Name;
	}

	public void setE_Name(String e_Name) {
		E_Name = e_Name;
	}

	public String getE_PNumber() {
		return E_PNumber;
	}

	public void setE_PNumber(String e_PNumber) {
		E_PNumber = e_PNumber;
	}

	public String getE_Position() {
		return E_Position;
	}

	public void setE_Position(String e_Position) {
		E_Position = e_Position;
	}

	public String getTeamNameOut() {
		return TeamNameOut;
	}

	public void setTeamNameOut(String teamNameOut) {
		TeamNameOut = teamNameOut;
	}

	public String getGroupNameOut() {
		return GroupNameOut;
	}

	public void setGroupNameOut(String groupNameOut) {
		GroupNameOut = groupNameOut;
	}

	public String getTrainSign() {
		return TrainSign;
	}

	public void setTrainSign(String trainSign) {
		TrainSign = trainSign;
	}

	public String getBureauName() {
		return BureauName;
	}

	public void setBureauName(String bureauName) {
		BureauName = bureauName;
	}

	public String getDeptname() {
		return Deptname;
	}

	public void setDeptname(String deptname) {
		Deptname = deptname;
	}
}
