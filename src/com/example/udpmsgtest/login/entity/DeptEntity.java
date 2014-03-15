package com.example.udpmsgtest.login.entity;

import com.example.udpmsgtest.entity.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName="Dept")
public class DeptEntity  extends BaseEntity{
	private static final long serialVersionUID = 1L;
	@DatabaseField
	private int ID;
	@DatabaseField
	private String DeptName;//段名�?
	@DatabaseField
	private String DeptCode;//段代�?
	@DatabaseField
	private String BureauCode;//段所属局代码
	

	public String getDeptName() {
		return DeptName;
	}

	public void setDeptName(String deptName) {
		DeptName = deptName;
	}

	public String getDeptCode() {
		return DeptCode;
	}

	public void setDeptCode(String deptCode) {
		DeptCode = deptCode;
	}

	public String getBureauCode() {
		return BureauCode;
	}

	public void setBureauCode(String bureauCode) {
		BureauCode = bureauCode;
	}

	public int getId() {
		return ID;
	}

	public void setId(int id) {
		this.ID = id;
	}

}