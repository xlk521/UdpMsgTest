package com.example.udpmsgtest.login.entity;

import com.example.udpmsgtest.entity.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="TrainTime")
public class TrainLeaderTime  extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@DatabaseField(id=true)
	private int id;
	
	@DatabaseField
	private int StationID;//车站顺序�?
	@DatabaseField
	private String StationName;//停靠站名
	@DatabaseField
	private String ArriveTime;//到站时间
	@DatabaseField
	private String StartTime;//发车时间
	@DatabaseField
	private String Days;//天数
	@DatabaseField
	private String TrainName;//车次
	@DatabaseField
	private String TrainSign;//车次标识
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStationID() {
		return StationID;
	}
	public void setStationID(int stationID) {
		StationID = stationID;
	}
	public String getStationName() {
		return StationName;
	}
	public void setStationName(String stationName) {
		StationName = stationName;
	}
	public String getArriveTime() {
		return ArriveTime;
	}
	public void setArriveTime(String arriveTime) {
		ArriveTime = arriveTime;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getDays() {
		return Days;
	}
	public void setDays(String days) {
		Days = days;
	}
	public String getTrainName() {
		return TrainName;
	}
	public void setTrainName(String trainName) {
		TrainName = trainName;
	}
	public String getTrainSign() {
		return TrainSign;
	}
	public void setTrainSign(String trainSign) {
		TrainSign = trainSign;
	}
}
