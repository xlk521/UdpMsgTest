package com.example.udpmsgtest.login.entity;

import com.example.udpmsgtest.entity.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="TeamIDAndTeamName")
public class TeamEntity extends BaseEntity{
	@DatabaseField(id=true)
	private int id;
	@DatabaseField
	private String TeamID;//车队ID
	@DatabaseField
	private String TeamName;//车队名称
	@DatabaseField
	private String DeptCode;//车队�?��客运�?
	
	
	public String getDeptCode() {
		return DeptCode;
	}

	public void setDeptCode(String deptCode) {
		DeptCode = deptCode;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	
	public String getTeam_ID() {
		return TeamID;
	}

	public void setTeam_ID(String TeamID) {
		this.TeamID = TeamID;
	}

	public String getTeam_Name() {
		return TeamName;
	}

	public void setTeam_Name(String TeamName) {
		this.TeamName = TeamName;
	}

}
