package com.example.udpmsgtest.login.entity;

import com.example.udpmsgtest.entity.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="Group")
public class GroupEntity extends BaseEntity{
	@DatabaseField(id=true)
	private String GroupID;//班组ID
	@DatabaseField
	private String GroupName;//班组名称
	@DatabaseField
	private String TeamName;//班组�?��车队名称
	public String getGroupID() {
		return GroupID;
	}
	public void setGroupID(String groupID) {
		GroupID = groupID;
	}
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
}
