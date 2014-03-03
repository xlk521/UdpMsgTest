package com.example.udpmsgtest.publicfunction;
public final class JSONKey {

	// ================================================
	// Result
	// ================================================
	public final static String FLAG_RESULT = "result";
	public final static String FLAG_RESULT_OK = "0000";
	public final static String FLAG_RESULT_ERROR = "4000";
	public final static String ERROR_INFO = "error";
	public final static String IMSI_NO_REGIST = "1000";
	public final static String UDPRETURNOK = "ACK";
	// ================================================
	// Flag
	// ================================================
	public final static String FLAG = "flag";
	public final static String FLAG_LOGIN = "flag_login";
	public final static String FLAG_RULES = "flag_rules";
	public final static String FLAG_TELEGRAPH = "flag_telegraph";
	public final static String FLAG_MESSAGE = "flag_message";
	public final static String FLAG_RECMESSAGE = "flag_recmessage";
	public final static String FLAG_WATER="flag_water";                 //新添加上水项
	public final static String FLAG_WATER_INSERT="water_insert";
	public final static String FLAG_UPLOAD_VIDEO = "flag_upload_video"; //视频上传项
	public final static String FLAG_VIDEO = "flag_video_upload";
	public final static String FLAG_CHAT = "flag_chat";
	public final static String FLAG_WATER_STATION="water_station";
	public final static String FLAG_WATER_TRAINCODE="water_traincode";
	public final static String FLAG_WATER_WORNING="water_worning";
	public final static String FLAG_MMS = "mms";
	public final static String FLAG_LOCATION_COLLECTION = "flag_location_collection";
	public final static String FLAG_LOCATION = "flag_location";
	public final static String FLAG_SQL = "flag_sql";
	public final static String FLAG_REFRESH = "flag_refresh";
	public final static String FLAG_APK = "flag_apk";
	public final static String FLAG_PACKAGE = "package";
	public final static String FLAG_CWBAOGAO = "flag_cwbaogao";
	public final static String FLAG_CWRIZHI = "flag_cwrizhi";
	//============
	//版本更新
	//===================
	public final static String APK_VERSION = "apk_version";
	public final static String APK_PACKAGE_NAME = "apk_pagecage_name";
	public final static String APK_DOWN_LOADURL = "apk_download_url";
	// ================================================
	// User
	// ================================================
	public final static String USER_FLAG = "user_flag";
	public final static String USER_LOGIN = "login";
	
	public final static String USER_ID = "ID";

	public final static String USER_NAME = "E_Name";

	public final static String USER_PASSWORD = "Password";

	public final static String USER_EID = "E_ID";

	public final static String USER_TYPE = "Type";

	public final static String USER_SEX = "E_Sex";

	public final static String USER_POSITION = "E_Position";

	public final static String USER_GROUPNAME = "GroupNameOut";

	public final static String USER_TEAMNAME = "TeamNameOut";
	public final static String USER_TEAMNAMEID = "TeamID";

	public final static String USER_BUREAUNAME = "BureauName";

	public final static String USER_DEPTNAME = "Deptname";
	public final static String USER_PHONENUMBER = "E_PNumber";
	public final static String USER_TRAINSIGN = "TrainSign";
	public final static String LAST_DATA_TIME = "last_data_time";
	public final static String UPLOAD_INIT_DATA_URL = "upload_initdata_url";
	// ================================================
	// Telegraph
	// ================================================
	public final static String TELEGRAPH_TID = "tid";

	public final static String TELEGRAPH_AUTHOR = "author";

	public final static String TELEGRAPH_AUTHOR_ID = "authorID";

	public final static String TELEGRAPH_REVIEWER = "reviewer";

	public final static String TELEGRAPH_SINGER = "signer";
	
	public final static String TELEGRAPH_SINGER_ID = "signerID";

	public final static String TELEGRAPH_TITLE = "title";

	public final static String TELEGRAPH_MESSAGE = "message";

	public final static String TELEGRAPH_DATE = "date";

	public final static String TELEGRAPH_TYPE = "type";

	public final static String TELEGRAPH_RECEIVERID = "receiverId";

	public final static String TELEGRAPH_COPYTOID = "copytoId";

	public final static String TELEGRAPH_READ = "read";

	public final static String TELEGRAPH_RECEIVERS = "receivers";

	public final static String TELEGRAPH_RECEIVER_NAME = "receiver_name";

	public final static String TELEGRAPH_SENDUNIT = "sendunit";

	public final static String TELEGRAPH_COPYUNIT = "copyunit";
	// MMS================================================
	public final static String MMS_ID = "id";
	public final static String MMS_CLASS = "class";
	public final static String MMS_NUM = "num";
	public final static String MMS_IMGPATH = "imgpath";
	public final static String MMS_TEXT = "text";
	public final static String MMS_PHOTO_LOCAL = "photo_local";
	// ================================================
	// Water                                                  //以下为新添加上水项
	// ================================================
    public final static String WATER_CHEMEID="chemeid";
			
	public final static String WATER_SHUNNO="shunno";
			
	public final static String WATER_TRAIN_CODE="code";
			
	public final static String WATER_GROUPNAME="groupname";
			
	public final static String WATER_START_STATION_NAME="start_station_name";
			
	public final static String WATER_END_STATION_NAME="end_station_name";
			
	public final static String WATER_ARRIVETIME="arrivetime";
			
	public final static String WATER_STARTTIME="starttime";  //·½°¸¿ªÊ¼Ê±¼ä
			
	public final static String WATER_ENDTIME="endtime";       //·½°¸½áÊøÊ±¼ä
			
	public final static String WATER_TRAIN_STARTTIME="train_starttime";	

	public final static String WATER_DISTANCETIME="distancetime";
			
	public final static String WATER_STOPTIME="stoptime";
			
	public final static String WATER_BUREAU_NAME="bureau_name";
			
	public final static String WATER_STATION="station";
			
	public final static String WATER_COMPILE_GROUP="compile_group";
			
	public final static String WATER_WATHER_COACH="wather_coach";
			
	public final static String WATER_JOIN_TRUCKBED="join_truckbed";
			
	public final static String WATER_ISGETWATER="isgetwater";
			
	public final static String WATER_SHOULDWATERCOUNT="shouldwatercount";
			
	public final static String WATER_COUNT="count";
			
	public final static String WATER_DATETIMES="datetimes";
			
	public final static String WATER_STATION_TPYE="station_tpye";
			
	public final static String WATER_AUTID="water_autid";
			
	public final static String WATER_AUTID_ID="water_autid_id";
		
	public final static String WATER_ISLIBRARY="islibrary";
			
	public final static String WATER_EID="eid";
			
	public final static String WATER_TRAIN_NO="water_train_no";
			
	public final static String WATER_REMARK="remark";
	
	public final static String WATER_DEPTNAME="deptname";
	
	public final static String WATER_TRAIN="train";
		
	public final static String WATER_TITLE="title";
		
	public final static String WATER_TOSTATION="tostation";
		
	public final static String WATER_SCONTENT="scontent";
		
	public final static String WATER_SENDER="sender";

	public final static String TRAIN_CODE = "trainCode";
	public final static String TRAIN_COUNT = "trainCount";
	public final static String NOW_TRAIN_STOP = "NowTrainStop";
	public final static String NOW_TRAIN_CODE = "NowTrainCode";
	public final static String STATION_CODE = "stationCode";
	public final static String EID_KEY = "eid";
	public final static String START_DATE_KEY = "startDate";
	public final static String WATER_UP_DATE_KEY = "waterUpDate";
	public final static String WATER_REMARK_KEY = "waterRecard";
	public final static String WATER_IN_KEY = "waterIn";
	public final static String WATER_TEAM_NUM = "waterTeamNum";
	public final static String WATER_RECORD = "waterRecord";
	public final static String WATER_RECORD_SEND = "waterRecordSend";
	public final static String WATER_START_TIME = "starttime";
	public final static String WATER_START_STATION = "StartStation";
	public final static String WATER_END_STATION = "EndStatin";
	public final static String WATER_COM_GROUP = "comGroup";

	// ================================================
	// WdInfo
	// ================================================
	public final static String WDINFO_GROUPNAME = "groupname";

	public final static String WDINFO_MINUTE = "minute";

	// ================================================
	// Rules
	// ================================================
	public final static String RULE_RULES = "rules";

	public final static String RULE_TABLE_ID = "table_id";

	public final static String RULE_ID = "id";

	public final static String RULE_RULECHAPTERNAME = "ruleChapterName";

	public final static String RULE_RULECHAPTERKEYWORDS = "ruleChapterKeyWords";

	public final static String RULE_PARENTID = "parentID";

	public final static String RULE_INDEXID = "indexID";

	public final static String RULE_DIRECTORYLEVELID = "directoryLevelID";

	public final static String RULE_RULENAME = "ruleName";

	public final static String RULE_RULETABLEID = "ruleTableID";

	public final static String RULE_NEWCHAPTERURL = "newChapterURL";

	public final static String RULE_NEWCHAPTERNAME = "newChapterName";

	public final static String RULE_DELCHAPTERSIGN = "delChapterSign";

	public final static String RULE_UPDATECHAPTERSIGN = "updateChapterSign";

	public final static String RULE_UPDATECHAPTERABSTRACT = "updateChapterAbstract";

	public final static String RULE_UPDATECHAPTERDATETIME = "updateChapterDateTime";

	public final static String RULE_POSITIONID = "positionID";

	public final static String RULE_TYPE = "type";
	
	// ShortMessage
	// ================================================
	public final static String MESSAGE_TID = "smid";

	public final static String MESSAGE_MSG = "msg";

	public final static String MESSAGE_TPA = "tpa";

	public final static String MESSAGE_TIME = "recvdatetime";

	public final static String MESSAGE_DEVNO = "devno";
		
	public final static String MESSAGE_SMSTYPE = "smstype";

	public final static String MESSAGE_MMSFILESID = "mmsfilesid";

	public final static String MESSAGE_SIGN = "sign";


	// RECvmessage================================================
	public final static String RECMESSAGE_RULE = "recmessage";
		
	public final static String RECMESSAGE_SMID = "rec_smid";

	public final static String RECMESSAGE_MSG = "rec_msg";
		
	public final static String RECMESSAGE_NUM = "rec_num";

	public final static String RECMESSAGE_NAME = "rec_name";
	
	//sql语句
	public final static String MESSAGE_BY_SQL = "message_by_sql";
	public final static String MESSAAGE_USER_FLAG = "message_user_flag"; //信息发送者标识
	
	//station
	public final static String STATION_FLAG = "station_flag"; //返回站次信息
	public final static String STATION_SID = "StationID";
	public final static String STATION_NAME = "StationName";
	public final static String STATION_ARRIVETIME = "ArriveTime";
	public final static String STATION_DAYS = "Days";
	public final static String STATION_STARTTIME = "StartTime";
	public final static String STATION_TRAINNAME = "TrainName";
	public final static String STATION_TRAINSIGN = "TrainSign";
	public final static String STATION_ID = "ID";
	
	//定位相关
	public final static String DW = "DW";
	public final static String DW_CID = "cid";
	public final static String DW_MNC = "mnc";
	public final static String DW_LAC = "lac";
	public final static String DW_TYPE = "zttype";
	public final static String DW_TIME = "zttime";
	public final static String DW_CHECI = "ztcheci";
	public final static String DW_TEL = "zttel";
	public final static String DW_BANZU = "banzu";
	public final static String DW_BIAOSHI = "biaoshi";
	public final static String DW_LNG = "lng";
	public final static String DW_LAT = "lat";
	public final static String DW_BUREAUCODE = "BureauNameCode";
	public final static String DW_DEPTCODE = "DeptNameCode";
	public final static String DW_TEAMCODE = "TeamCode";
	public final static String DW_GROUPCODE = "GroupCode";
	
	//基站采集
	public final static String CELLCJ = "caiji";
	public final static String TEAMNAME = "TeamNameOut";
	public final static String BUREAUCODE = "BureauCode";
	public final static String GROUPID= "GroupName";
	public final static String DATA = "upload_data";
	public final static String DEPTCODE = "DeptCode";
	public final static String TEAMID = "TeamId";
	public final static String BUREAUNAME = "BureauName";
	public final static String GROUPNAME = "GroupName";
	
	//速报
	public final static String MESSAGE = "SB";
	//电报
	public final static String DIANBAO = "dianbao";
	//领导检查
	public final static String LINGDAOJIANCHA = "JC";
	
	//乘务日志，乘务报告
	public final static String CW_BIAOSHI = "cw_biaoshi";//对应表名
	
	public final static String MYDEVICE = "my_device";
	
	
}