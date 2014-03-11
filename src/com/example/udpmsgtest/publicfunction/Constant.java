package com.example.udpmsgtest.publicfunction;

import java.util.ArrayList;

public class Constant {
	//数据库信息
	public static String DB_NAME = "tkyDB.db";
	public static String DB_FOLDER_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/kyd/";
	public static String DB_PATH = DB_FOLDER_PATH + DB_NAME;
	public final static int DB_VERSION = 2;
	public static String INITFILEBASEPATH = "KydUpLoad";
	public static String XINGZOU_PATH = DB_FOLDER_PATH + "KydUpLoad/ZouXingImg/";
	public static final String MMS_SQLLITE_DATA_PATH = DB_FOLDER_PATH + "crewBreaks/";
	public static final String CREW_BREAKS_TEST = DB_FOLDER_PATH + "TKY.jpg";
	public static final String APK_CHECK="http://115.29.172.156:8086/TKYServer/TKYServer";
	//短信拦截
	public final static String[] SMSCatchWords = {"请回复"};
	public final static String[] SMSCatchNumbers = {};//"10010","10086"};
	public final static int NO_NEED_CATCH = 0;
	public final static int NEED_CATCH = 1;
	public static String UPLOADFILEFOLDER = DB_FOLDER_PATH + "Upload/";
	public static String BASICFILEFOLDER = DB_FOLDER_PATH + "Basic/";
	
	//初始化时所要读取文件名称

	public static String INIT_CHENGWU_CwReport1_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport1.ini"; //乘务报告-头部信息
	public static String INIT_CHENGWU_CwReport2_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport2.ini"; //乘务报告-人员编组
	public static String INIT_CHENGWU_CwReport3_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport3.ini"; //乘务报告-晚点
	public static String INIT_CHENGWU_CwReport4_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport4.ini"; //乘务报告-生产
	public static String INIT_CHENGWU_CwReport5_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport5.ini"; //乘务报告-旅客事故
	public static String INIT_CHENGWU_CwReport6_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport6.ini"; //乘务报告-危险品
	public static String INIT_CHENGWU_CwReport7_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport7.ini"; //乘务报告-暂时不用
	public static String INIT_CHENGWU_CwReportName_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReportName.ini"; //乘务报告总列表

	public static String INIT_JCEMPLOYEE_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/JcEmployee.ini";  //列车人员
	public static String INIT_WATER_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/Wateri.ini";  //上水信息
	public static String INIT_UNIT_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/Telegraph_unit.ini"; // 单位信息
	public static String INIT_BUREAU_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/JcBureau.ini"; //路局信息
	public static String INIT_TRAIN_TIME_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/JcTime.ini"; //列车时刻信息
	public static String INIT_JCDEPT_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/JcDept.ini"; //客运段信息
	public static String INIT_JCTEAM_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/JcTeam.ini"; //车队信息
	public static String INIT_JCGROUP_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/JcGroup.ini"; //班组信息
	public static String INIT_GWKYDTYPETREE_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/GwKydTypeTree.ini"; //规章文电分类信息
	public static String INIT_GWKYDANDROID_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/GwKyd.ini"; //规章文电内容信息
	public static String INIT_CHENGWUREPORT_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/ChengWuReport.ini"; //乘务报告

	
	
	public final static String INIT_FILE_PATH = DB_FOLDER_PATH +"LeaderTrainStop.ini";  //列车长、车次、车站信息合并后文件
	public final static int INIT_NEED_FILE = 98;
	public final static int INIT_FINISH = 99;
	public final static int INIT_STATE = 100;
	
	
	public static String teleno = null;
	
	//信息分隔符
	public final static String INFO_SPLIT = "+";
	public final static String INFO_SPLIT1 = "，";
	public final static int INFO_MAST_LENGTH = 400;
	
	//文件分隔符的判定
	public static String Point = ".";//文件名中名称与顺序的分解符号
	public static String PointD = ".d";//文件名中名称与顺序的分解符号
	
	//是否退出
	public final static String APP_ISRUNING = "tkyclient_run_state";
	
	//信息发送方式，0为使用短信；1为使用3G或WIFI
	public final static int MESSAGE_SEND_TYPE = 1;
	//信息通过wifi发送途径；0为UDP,1为TCP
	public final static int INFO_SEND_TYPE = 0;
	
	//是否采集沿途基站信息
	public final static boolean IS_COLLECTED_CELLID = false;
	//是否需要发送定位基站信息
	public final static boolean IS_NEED_LOCATION = true;
	//获取基站间隔时间
	public final static int SLEEP_TIME = 60000; //1分钟
	public final static long SEND_SPLIT_TIME =  60000; //基站采集上传间隔时间；
	
	//是否从网络更新版本或获取基础初始化数据
	public final static boolean IS_DATA_FROM_WEB = false;
	public final static boolean IS_APK_FROM_WEB = true;
	
	//WIFI或3服务器地址
    public static String BASE_IP = "192.168.1.107";//"192.168.1.111";//"114.255.140.194";//"10.130.127.4";//"61.232.48.26";//"115.29.172.156";//济南服务器IP为："61.232.48.26";
    public static String IPFROMSETTING = "ip_from_setting";
    
    public static final String BASE_PORT = "8086";

	
	public static final String BASE_HTTP_URL = "http://"+BASE_IP+ ":"+BASE_PORT +"/TKYServer/TKYServer";

	public static final String BASE_HTTP_LOAD_URL = "http://"+BASE_IP+ ":"+BASE_PORT +"/TKYServer";

	public static final String BASE_PATH = "/kyd/";
	
	public static final int SOCKET_PORT = 8087;
	

	public static final int time = 4000;
	public final static String KEY = "10293847";
	
	//菜单项级别分类key值
	public final static int MAINTYPEKEY = 0;//无次级列表项的菜单项 
	public final static int REPORTMAINTYPEKEY = 1;//台帐管理主界面菜单项-台账资料
	public final static int RULEMAINTYPEKEY = 3;       //规章文电主界面菜单项
	public final static int BASICMAINTYPEKEY = 2;//基础管理主界面菜单项
	public final static int CHENGWUZUOYE = 4;//乘务作业

	public final static int REPORT_DIANBAO_MAINTYPEKEY = 01;//台帐管理--铁路电报列表界面项值 
	public final static int REPORT_ANQUAN_MAINTYPEKEY = 11;//台帐管理--安全管理列表界面项值 
	public final static int REPORT_ANQUAN_A = 111;//台帐管理--列车消防管理台账
	public final static int REPORT_ANQUAN_B = 112;//台帐管理--列车安检查维台账
	public final static int REPORT_ANQUAN_C = 113;//台帐管理--安全风险提示卡
	public final static int REPORT_ANQUAN_D = 114;//台帐管理--安全行走线路示意图
	public final static int REPORT_ANQUAN_CHAWEI = 115;//台帐管理--安全管理--查危台帐列表界面项值 
	public final static int REPORT_ANQUAN_CHAWEI_A = 116;//台帐管理--岗位职责
	public final static int REPORT_ANQUAN_CHAWEI_B = 117;//台帐管理--
	public final static int REPORT_ANQUAN_CHAWEI_C = 118;//台帐管理--查危文件
	
	public final static int REPORT_CHENGWU_MAINTYPEKEY = 12;//台帐管理--乘务管理列表界面项值 
	public final static int REPORT_CHENGWU_A = 121;//台帐管理--乘务报告
	public final static int REPORT_CHENGWU_B = 122;//台帐管理--乘务日志
	public final static int REPORT_CHENGWU_C = 123;//台帐管理--旅客密度表
	public final static int REPORT_CHENGWU_D = 124;//台帐管理--会议记录
	public final static int REPORT_CHENGWU_E = 125;//台帐管理--规章制度摘抄
	public final static int REPORT_CHENGWU_F = 126;//台帐管理--一次性作业指导书
	public final static int REPORT_CHENGWU_G = 127;//台帐管理--岗位职责责任制
	public final static int REPORT_CHENGWU_H = 128;//台帐管理--三乘联检
	public final static int REPORT_CHENGWU_J = 129;//台帐管理--客运记录
	
	public final static int REPORT_LUFENG_MAINTYPEKEY = 13;//台帐管理--路风管理列表界面项值 
	public final static int REPORT_LUFENG_A = 131;//台帐管理--路风风险提示卡
	public final static int REPORT_LUFENG_B = 132;//台帐管理--路风关键顶点防控措施
	public final static int REPORT_LUFENG_C = 133;//台帐管理--宿营车定位图
	public final static int REPORT_LUFENG_D = 134;//台帐管理--旅客越席探访登记
	
	public final static int REPORT_SHOURU_MAINTYPEKEY = 14;//台帐管理--收入管理 
	
	public final static int REPORT_ZHIJIAO_MAINTYPEKEY = 15;//台帐管理--职教培训
	public final static int REPORT_ZHIJIAO_PEIXUN = 151;//台帐管理--职教培训
	public final static int REPORT_ZHIJIAO_PEIXUN_A = 1511;//台帐管理--职教培训-段职工
	public final static int REPORT_ZHIJIAO_PEIXUN_B = 1512;//台帐管理--职教培训-车队职工
	public final static int REPORT_ZHIJIAO_PEIXUN_C = 1513;//台帐管理--职教培训-班组
	public final static int REPORT_ZHIJIAO_TIKU = 152;//台帐管理--职教培训
	public final static int REPORT_ZHIJIAO_TIKU_A = 1521;//台帐管理--职教培训-列车长
	public final static int REPORT_ZHIJIAO_TIKU_B = 1522;//台帐管理--职教培训-值班员
	public final static int REPORT_ZHIJIAO_TIKU_C = 1523;//台帐管理--职教培训-列车员
	public final static int REPORT_ZHIJIAO_TIKU_D = 1524;//台帐管理--职教培训-行李员
	
	public final static int BASIC_GAIKUANG_MAINTYPEKEY = 21;//基础管理--班组基本概况
	public final static int BASIC_DANGAN_MAINTYPEKEY = 22;//基础管理--个人工作档案
	public final static int BASIC_TIKU_MAINTYPEKEY = 23;//基础管理--问题动态库
	
	public final static int BASIC_DANGQUN_MAINTYPEKEY = 24;//基础管理--党群工作界面菜单项
	public final static int BASIC_DANGQUN_DANGJIAN = 241;//基础管理--党群工作界面菜单项-党支部建设
	public final static int BASIC_DANGQUN_DANGJIAN_A = 2411;//基础管理--党群工作界面菜单项-党支部建设-基本概况一览表
	public final static int BASIC_DANGQUN_DANGJIAN_B = 2412;//基础管理--党群工作界面菜单项-党支部建设-基本职责
	public final static int BASIC_DANGQUN_DANGJIAN_C = 2413;//基础管理--党群工作界面菜单项-党支部建设-基本知识
	public final static int BASIC_DANGQUN_JIAOYU = 242;//基础管理--党群工作界面菜单项-教育
	public final static int BASIC_DANGQUN_JIAOYU_A = 2421;//基础管理--党群工作界面菜单项-教育-形势任务
	public final static int BASIC_DANGQUN_JIAOYU_B = 2422;//基础管理--党群工作界面菜单项-教育-明白纸
	public final static int BASIC_DANGQUN_JIAOYU_C = 2423;//基础管理--党群工作界面菜单项-教育-文化建设
	public final static int BASIC_DANGQUN_JIAOYU_D = 2424;//基础管理--党群工作界面菜单项-教育-舆论宣传
	public final static int BASIC_DANGQUN_JIAOYU_E = 2425;//基础管理--党群工作界面菜单项-教育-政策解读
	public final static int BASIC_DANGQUN_GONGHUI = 243;//基础管理--党群工作界面菜单项-工会
	public final static int BASIC_DANGQUN_GONGHUI_A = 2431;//基础管理--党群工作界面菜单项-工会-小组概况
	public final static int BASIC_DANGQUN_GONGHUI_B = 2432;//基础管理--党群工作界面菜单项-工会-民主管理
	public final static int BASIC_DANGQUN_TUANJIAN = 244;//基础管理--党群工作界面菜单项-团支部建设
	public final static int BASIC_DANGQUN_TUANJIAN_A = 2441;//基础管理--党群工作界面菜单项-团支部建设-团支部概况
	public final static int BASIC_DANGQUN_TUANJIAN_B = 2442;//基础管理--党群工作界面菜单项-团支部建设-团支部活动
	
	public final static int RULEMAINTYPEKEY_A = 31;       //规章文电--法律法规
	public final static int RULEMAINTYPEKEY_B = 32;       //规章文电--客运规章
	public final static int RULEMAINTYPEKEY_C = 33;       //规章文电--有效技术规章
	public final static int RULEMAINTYPEKEY_D = 34;       //规章文电--有效文电查询
	public final static int RULEMAINTYPEKEY_E = 35;       //规章文电--应急预案
	public final static int RULEMAINTYPEKEY_F = 36;       //规章文电--路风规范
	
	public final static int SMSINFOLIST = 500;//已发送列车速报消息列表
	
	
	public final static String sharePerferceStr = "train_server_client";
	public final static String shareLocationTrainStr = "now_train_number";
	public final static String shareLocationSplitTime = "split_time";
	public final static String shareLocationTrainStart = "train_start_time";
	public final static String NEED_SET_LOCATION_INOF = "need_set_location_info";
	public final static String LOCATION_MSG_SEND_TIME = "location_msg_last_send_time";
	public final static String NOW_USER_E_ID = "now_user_e_id";
	public final static String NOW_BAN_ZU = "now_ban_zu";
	public final static String CHU_CHENG_TIME = "chu_cheng_time";
	
//xlk
	public static boolean isRizhiToubu = false;
	public static int reportId;
	public final static String[] biaoMing = {"CwReport1","CwReport2","CwReport3","CwReport4","CwReport5","CwReport6","CwReport7","CwReportName","CwReportWorkOver","Cwlogleader","Cwlogcondition","Cwlogexcuse","Cwlogtest"};
	public final static String[] Rizhi_biaoming = {"CwRiZhiName","CwReport9","CwReport20","CwReport10","CwReport11","CwReport12","CwReport13","CwReport14","CwReport15","CwReport16","CwReport17","CwReport18","CwReport19","CwReport21"};
	public final static String[] lieMingWhere = {"ReportID","ID","id","LogID","ReportCode","code"};
	public final static String INIT_CHENGWU_Name = "kyd.db";  //乘务报告数据库
	public final static String INIT_CHENGWU = DB_FOLDER_PATH +INIT_CHENGWU_Name;  //乘务报告数据库
	public final static String cheduiName = "长沙车队";
	public final static String banzuName = "长沙二组";
//	public static ArrayList<CwReport1> cwReport1 ;
	public final static String TEAM_ID = "now_team_id";
	public static int ChengWu_Leader_ID = 0;
	public static int ChengWu_Leader_IDCount = 0;
	public static String reportCode = "";
	public static ArrayList<String> whichJson = new ArrayList<String>();
	//安全走行线示意图
	
	public static String XINGZOU_NAME_A = XINGZOU_PATH + "jn.png";//济南客整场安全走行线示意图
	public static String XINGZOU_NAME_B = XINGZOU_PATH + "nt.png";//南通公寓乘务员安全走行线示意图
	public static String XINGZOU_NAME_C = XINGZOU_PATH + "wx.png";//乌西公寓乘务员安全走行线示意图
	public static String XINGZOU_NAME_D = XINGZOU_PATH + "yt.png";//烟台公寓乘务员安全走行线示意图
	public static int XINGZOU_NAME_KEY_A = 1141;//济南客整场安全走行线示意图
	public static int XINGZOU_NAME_KEY_B = 1142;//南通公寓乘务员安全走行线示意图
	public static int XINGZOU_NAME_KEY_C = 1143;//乌西公寓乘务员安全走行线示意图
	public static int XINGZOU_NAME_KEY_D = 1144;//烟台公寓乘务员安全走行线示意图
	//取出数据库内容，然后文档存储
	public static String Upload_Path = DB_FOLDER_PATH + "Upload/";
	public static String Upload_Dir_Path = DB_FOLDER_PATH + "Upload";
	public static String Upload_CW_Name = "CW+JNK+";
	public static String Upload_CWRZ_Name = "CWRZ+JNK+";
	public static String Upload_CwReport1_Name = Upload_Path +Upload_CW_Name+ "CwReport1";
	public static String Upload_CwReport2_Name = Upload_Path +Upload_CW_Name+ "CwReport2";
	public static String Upload_CwReport3_Name = Upload_Path +Upload_CW_Name+ "CwReport3";
	public static String Upload_CwReport4_Name = Upload_Path +Upload_CW_Name+ "CwReport4";
	public static String Upload_CwReport5_Name = Upload_Path +Upload_CW_Name+ "CwReport5";
	public static String Upload_CwReport6_Name = Upload_Path +Upload_CW_Name+ "CwReport6";
	public static String Upload_CwReportName_Name = Upload_Path +Upload_CW_Name+ "CwReportName";
//	public static String Upload_CwReportWorkOver_Name = Upload_Path + "Cwlogcondition";
//	public static String Upload_CwLogLeader_Name = Upload_Path + "Cwlogleader";
//	public static String Upload_Cwlogexcuse_Name = Upload_Path + "Cwlogexcuse";
//	public static String Upload_Cwlogtest_Name = Upload_Path + "Cwlogtest";
	public static String Upload_JSON = ".json";
	public static String groupNameOut = "";
//	public static RemeberGroupOut remeberGroupOut;
	//取出日志的信息
	public static String Upload_CwRiZhiName_Name = Upload_Path +Upload_CWRZ_Name+ "CwRiZhiName";
	public static String Upload_CwReport9_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport9";
	public static String Upload_CwReport10_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport10";
	public static String Upload_CwReport11_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport11";
	public static String Upload_CwReport12_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport12";
	public static String Upload_CwReport13_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport13";
	public static String Upload_CwReport14_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport14";
	public static String Upload_CwReport15_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport15";
	public static String Upload_CwReport16_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport16";
	public static String Upload_CwReport17_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport17";
	public static String Upload_CwReport18_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport18";
	public static String Upload_CwReport19_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport19";
	public static String Upload_CwReport20_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport20";
	public static String Upload_CwReport21_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport21";
	//生成乘务报告的json文档
	public final static boolean CHENGWU_JSON = false;
	public static int isAdapter_RiZhiRenYuan = 0;
//	public static AdapterForLinearLayout adpater;
	public static int SendJsonByThreadNum = 0;//记录需要发送的文件的总数
	public static int SendJsonByThreadCount = 0;//累加发送文件成功的个数
	public static String saveJsonResout = Upload_Path + "resout";
	
	public final static String BingCls = "病事假公休";
	public final static String NOW_GROUP = "now_group";
	public final static String NOW_GROUP_ID = "now_group_id"; //班组
	public final static String NOW_DEPT = "now_dept";
	public final static String NOW_BUREAU = "now_bureau"; //局
	
	public final static String NOW_TEAM_ID = "now_team_id";
	
	public final static String NOW_TEAM = "now_team_id";
	public final static String NOW_TRAINSIGN = "now_trainsign";
	
	public final static String DEPTSTR = "[{\"DeptName\":\"哈尔滨客运段\",\"DeptCode\":\"HBB\",\"BureauCode\":\"HEB\"}," +
			"{\"DeptName\":\"齐齐哈尔客运段\",\"DeptCode\":\"QHX\",\"BureauCode\":\"HEB\"}," +
	"{\"DeptName\":\"牡丹江客运段\",\"DeptCode\":\"MDB\",\"BureauCode\":\"HEB\"},{\"DeptName\":\"长春客运段\",\"DeptCode\":\"CCT\",\"BureauCode\":\"SY\"}," +
	"{\"DeptName\":\"沈阳客运段\",\"DeptCode\":\"SYT\",\"BureauCode\":\"SY\"},{\"DeptName\":\"大连客运段\",\"DeptCode\":\"DLT\",\"BureauCode\":\"SY\"}," +
	"{\"DeptName\":\"锦州客运段\",\"DeptCode\":\"JZD\",\"BureauCode\":\"SY\"},{\"DeptName\":\"吉林客运段\",\"DeptCode\":\"JLL\",\"BureauCode\":\"SY\"}," +
	"{\"DeptName\":\"北京客运段\",\"DeptCode\":\"BJP\",\"BureauCode\":\"BJ\"},{\"DeptName\":\"天津客运段\",\"DeptCode\":\"TJP\",\"BureauCode\":\"BJ\"}," +
	"{\"DeptName\":\"石家庄客运段\",\"DeptCode\":\"SJP\",\"BureauCode\":\"BJ\"},{\"DeptName\":\"太原客运段\",\"DeptCode\":\"TYV\",\"BureauCode\":\"BY\"}," +
	"{\"DeptName\":\"包头客运段\",\"DeptCode\":\"BTC\",\"BureauCode\":\"HHHT\"},{\"DeptName\":\"郑州客运段\",\"DeptCode\":\"ZZF\",\"BureauCode\":\"ZZ\"}," +
	"{\"DeptName\":\"武汉客运段\",\"DeptCode\":\"WHN\",\"BureauCode\":\"WH\"},{\"DeptName\":\"襄阳客运段\",\"DeptCode\":\"XFN\",\"BureauCode\":\"WH\"}," +
	"{\"DeptName\":\"西安客运段\",\"DeptCode\":\"XAY\",\"BureauCode\":\"XA\"},{\"DeptName\":\"济南客运段\",\"DeptCode\":\"JNK\",\"BureauCode\":\"JN\"}," +
	"{\"DeptName\":\"青岛客运段\",\"DeptCode\":\"QDK\",\"BureauCode\":\"JN\"},{\"DeptName\":\"合肥客运段\",\"DeptCode\":\"HFH\",\"BureauCode\":\"SH\"}," +
	"{\"DeptName\":\"南京客运段\",\"DeptCode\":\"NJH\",\"BureauCode\":\"SH\"},{\"DeptName\":\"上海客运段\",\"DeptCode\":\"SHH\",\"BureauCode\":\"SH\"}," +
	"{\"DeptName\":\"杭州客运段\",\"DeptCode\":\"HZH\",\"BureauCode\":\"SH\"},{\"DeptName\":\"南昌客运段\",\"DeptCode\":\"NCG\",\"BureauCode\":\"NC\"}," +
	"{\"DeptName\":\"福州客运段\",\"DeptCode\":\"FZS\",\"BureauCode\":\"NC\"},{\"DeptName\":\"广州客运段\",\"DeptCode\":\"GZQ\",\"BureauCode\":\"GZ\"}," +
	"{\"DeptName\":\"长沙客运段\",\"DeptCode\":\"CSQ\",\"BureauCode\":\"GZ\"},{\"DeptName\":\"广九客运段\",\"DeptCode\":\"SZQ\",\"BureauCode\":\"GZ\"}," +
	"{\"DeptName\":\"肇庆客运段\",\"DeptCode\":\"ZVQ\",\"BureauCode\":\"GZ\"},{\"DeptName\":\"东莞东客运段\",\"DeptCode\":\"DMQ\",\"BureauCode\":\"GZ\"}," +
	"{\"DeptName\":\"南宁客运段\",\"DeptCode\":\"NNZ\",\"BureauCode\":\"NN\"},{\"DeptName\":\"成都客运段\",\"DeptCode\":\"CDW\",\"BureauCode\":\"CD\"}," +
	"{\"DeptName\":\"重庆客运段\",\"DeptCode\":\"CQW\",\"BureauCode\":\"CD\"},{\"DeptName\":\"贵阳客运段\",\"DeptCode\":\"GIW\",\"BureauCode\":\"CD\"}," +
	"{\"DeptName\":\"昆明客运段\",\"DeptCode\":\"KMM\",\"BureauCode\":\"KM\"},{\"DeptName\":\"兰州客运段\",\"DeptCode\":\"LZJ\",\"BureauCode\":\"LZ\"}," +
	"{\"DeptName\":\"银川客运段\",\"DeptCode\":\"YIJ\",\"BureauCode\":\"LZ\"},{\"DeptName\":\"乌鲁木齐客运段\",\"DeptCode\":\"WMR\",\"BureauCode\":\"WLMQ\"}," +
	"{\"DeptName\":\"西宁客运段\",\"DeptCode\":\"XNO\",\"BureauCode\":\"QZ\"}]";
	
	
	public static final int VIDEO = 1;
	public static final int AUDIO = 2;
	public static final int IMAGE = 3;

	
	public static final int NEEDTIPWEB_NOTIFICATIN_ID = 98;
	
	public final static boolean ISENCRYPT = false;
	public final static int UDP_CLIENT_PORT = 8000; // UDP客户端监听的端口
	public final static int UDP_SERVER_PORT = 8088;//20001;//UDP服务端监听的端口
	public final static int TCP_SERVER_PORT = 8089; //TCP服务端监听端口
	public final static byte[] UDPRETURNSUCCESS = {0x10,0x06};
	public final static String IMEI = "imei";
	
	public Constant(String cw,String cwrz){
		Upload_CW_Name = cw;
		Upload_CWRZ_Name = cwrz;
		//乘务报告
		Upload_CwReport1_Name = Upload_Path +Upload_CW_Name+ "CwReport1";
		Upload_CwReport2_Name = Upload_Path +Upload_CW_Name+ "CwReport2";
		Upload_CwReport3_Name = Upload_Path +Upload_CW_Name+ "CwReport3";
		Upload_CwReport4_Name = Upload_Path +Upload_CW_Name+ "CwReport4";
		Upload_CwReport5_Name = Upload_Path +Upload_CW_Name+ "CwReport5";
		Upload_CwReport6_Name = Upload_Path +Upload_CW_Name+ "CwReport6";
		Upload_CwReportName_Name = Upload_Path +Upload_CW_Name+ "CwReportName";

		//取出日志的信息
		Upload_CwRiZhiName_Name = Upload_Path +Upload_CWRZ_Name+ "CwRiZhiName";
		Upload_CwReport9_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport9";
		Upload_CwReport10_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport10";
		Upload_CwReport11_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport11";
		Upload_CwReport12_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport12";
		Upload_CwReport13_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport13";
		Upload_CwReport14_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport14";
		Upload_CwReport15_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport15";
		Upload_CwReport16_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport16";
		Upload_CwReport17_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport17";
		Upload_CwReport18_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport18";
		Upload_CwReport19_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport19";
		Upload_CwReport20_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport20";
		Upload_CwReport21_Name = Upload_Path +Upload_CWRZ_Name+ "CwReport21";
	}
	
	public final static boolean USE_THIS_PHONE_NUM = false;
}
