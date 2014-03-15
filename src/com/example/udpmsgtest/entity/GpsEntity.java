package com.example.udpmsgtest.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="Gps")
public class GpsEntity  extends BaseEntity{
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	public int MCC;
	@DatabaseField
	public int MNC;
	@DatabaseField
	public int LAC;
	@DatabaseField
	public int CID;
	@DatabaseField
	public double LNG;
	@DatabaseField
	public double LAT;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMCC() {
		return MCC;
	}
	public void setMCC(int mCC) {
		MCC = mCC;
	}
	public int getMNC() {
		return MNC;
	}
	public void setMNC(int mNC) {
		MNC = mNC;
	}
	public int getLAC() {
		return LAC;
	}
	public void setLAC(int lAC) {
		LAC = lAC;
	}
	public int getCID() {
		return CID;
	}
	public void setCID(int cID) {
		CID = cID;
	}
	public double getLNG() {
		return LNG;
	}
	public void setLNG(double lNG) {
		LNG = lNG;
	}
	public double getLAT() {
		return LAT;
	}
	public void setLAT(double lAT) {
		LAT = lAT;
	}
}
