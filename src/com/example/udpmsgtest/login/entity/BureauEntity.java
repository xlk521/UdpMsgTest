package com.example.udpmsgtest.login.entity;

import com.example.udpmsgtest.entity.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName="JcBureau")
public class BureauEntity extends BaseEntity{
	private static final long serialVersionUID = 1L;
	@DatabaseField
	private int id;
	@DatabaseField
	private String BureauName;//Â±?êçÁß?
	@DatabaseField
	private String BureauCode;//Â±?ª£Á†?
	@DatabaseField
	private String OrderID;//ÊéíÂ∫èID
	
	public String getBureauName() {
		return BureauName;
	}

	public void setBureauName(String bureauName) {
		BureauName = bureauName;
	}

	public String getBureauCode() {
		return BureauCode;
	}

	public void setBureauCode(String bureauCode) {
		BureauCode = bureauCode;
	}

	public String getOrderID() {
		return OrderID;
	}

	public void setOrderID(String orderID) {
		OrderID = orderID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
