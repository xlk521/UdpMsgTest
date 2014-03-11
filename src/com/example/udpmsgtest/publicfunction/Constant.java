package com.example.udpmsgtest.publicfunction;

import java.util.ArrayList;

public class Constant {
	//���ݿ���Ϣ
	public static String DB_NAME = "tkyDB.db";
	public static String DB_FOLDER_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/kyd/";
	public static String DB_PATH = DB_FOLDER_PATH + DB_NAME;
	public final static int DB_VERSION = 2;
	public static String INITFILEBASEPATH = "KydUpLoad";
	public static String XINGZOU_PATH = DB_FOLDER_PATH + "KydUpLoad/ZouXingImg/";
	public static final String MMS_SQLLITE_DATA_PATH = DB_FOLDER_PATH + "crewBreaks/";
	public static final String CREW_BREAKS_TEST = DB_FOLDER_PATH + "TKY.jpg";
	public static final String APK_CHECK="http://115.29.172.156:8086/TKYServer/TKYServer";
	//��������
	public final static String[] SMSCatchWords = {"��ظ�"};
	public final static String[] SMSCatchNumbers = {};//"10010","10086"};
	public final static int NO_NEED_CATCH = 0;
	public final static int NEED_CATCH = 1;
	public static String UPLOADFILEFOLDER = DB_FOLDER_PATH + "Upload/";
	public static String BASICFILEFOLDER = DB_FOLDER_PATH + "Basic/";
	
	//��ʼ��ʱ��Ҫ��ȡ�ļ�����

	public static String INIT_CHENGWU_CwReport1_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport1.ini"; //���񱨸�-ͷ����Ϣ
	public static String INIT_CHENGWU_CwReport2_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport2.ini"; //���񱨸�-��Ա����
	public static String INIT_CHENGWU_CwReport3_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport3.ini"; //���񱨸�-���
	public static String INIT_CHENGWU_CwReport4_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport4.ini"; //���񱨸�-����
	public static String INIT_CHENGWU_CwReport5_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport5.ini"; //���񱨸�-�ÿ��¹�
	public static String INIT_CHENGWU_CwReport6_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport6.ini"; //���񱨸�-Σ��Ʒ
	public static String INIT_CHENGWU_CwReport7_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReport7.ini"; //���񱨸�-��ʱ����
	public static String INIT_CHENGWU_CwReportName_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/CwReportName.ini"; //���񱨸����б�

	public static String INIT_JCEMPLOYEE_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/JcEmployee.ini";  //�г���Ա
	public static String INIT_WATER_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/Wateri.ini";  //��ˮ��Ϣ
	public static String INIT_UNIT_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/Telegraph_unit.ini"; // ��λ��Ϣ
	public static String INIT_BUREAU_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/JcBureau.ini"; //·����Ϣ
	public static String INIT_TRAIN_TIME_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/JcTime.ini"; //�г�ʱ����Ϣ
	public static String INIT_JCDEPT_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/JcDept.ini"; //���˶���Ϣ
	public static String INIT_JCTEAM_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/JcTeam.ini"; //������Ϣ
	public static String INIT_JCGROUP_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/JcGroup.ini"; //������Ϣ
	public static String INIT_GWKYDTYPETREE_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/GwKydTypeTree.ini"; //�����ĵ������Ϣ
	public static String INIT_GWKYDANDROID_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/GwKyd.ini"; //�����ĵ�������Ϣ
	public static String INIT_CHENGWUREPORT_PATH = DB_FOLDER_PATH + INITFILEBASEPATH + "/ChengWuReport.ini"; //���񱨸�

	
	
	public final static String INIT_FILE_PATH = DB_FOLDER_PATH +"LeaderTrainStop.ini";  //�г��������Ρ���վ��Ϣ�ϲ����ļ�
	public final static int INIT_NEED_FILE = 98;
	public final static int INIT_FINISH = 99;
	public final static int INIT_STATE = 100;
	
	
	public static String teleno = null;
	
	//��Ϣ�ָ���
	public final static String INFO_SPLIT = "+";
	public final static String INFO_SPLIT1 = "��";
	public final static int INFO_MAST_LENGTH = 400;
	
	//�ļ��ָ������ж�
	public static String Point = ".";//�ļ�����������˳��ķֽ����
	public static String PointD = ".d";//�ļ�����������˳��ķֽ����
	
	//�Ƿ��˳�
	public final static String APP_ISRUNING = "tkyclient_run_state";
	
	//��Ϣ���ͷ�ʽ��0Ϊʹ�ö��ţ�1Ϊʹ��3G��WIFI
	public final static int MESSAGE_SEND_TYPE = 1;
	//��Ϣͨ��wifi����;����0ΪUDP,1ΪTCP
	public final static int INFO_SEND_TYPE = 0;
	
	//�Ƿ�ɼ���;��վ��Ϣ
	public final static boolean IS_COLLECTED_CELLID = false;
	//�Ƿ���Ҫ���Ͷ�λ��վ��Ϣ
	public final static boolean IS_NEED_LOCATION = true;
	//��ȡ��վ���ʱ��
	public final static int SLEEP_TIME = 60000; //1����
	public final static long SEND_SPLIT_TIME =  60000; //��վ�ɼ��ϴ����ʱ�䣻
	
	//�Ƿ��������°汾���ȡ������ʼ������
	public final static boolean IS_DATA_FROM_WEB = false;
	public final static boolean IS_APK_FROM_WEB = true;
	
	//WIFI��3��������ַ
    public static String BASE_IP = "192.168.1.107";//"192.168.1.111";//"114.255.140.194";//"10.130.127.4";//"61.232.48.26";//"115.29.172.156";//���Ϸ�����IPΪ��"61.232.48.26";
    public static String IPFROMSETTING = "ip_from_setting";
    
    public static final String BASE_PORT = "8086";

	
	public static final String BASE_HTTP_URL = "http://"+BASE_IP+ ":"+BASE_PORT +"/TKYServer/TKYServer";

	public static final String BASE_HTTP_LOAD_URL = "http://"+BASE_IP+ ":"+BASE_PORT +"/TKYServer";

	public static final String BASE_PATH = "/kyd/";
	
	public static final int SOCKET_PORT = 8087;
	

	public static final int time = 4000;
	public final static String KEY = "10293847";
	
	//�˵�������keyֵ
	public final static int MAINTYPEKEY = 0;//�޴μ��б���Ĳ˵��� 
	public final static int REPORTMAINTYPEKEY = 1;//̨�ʹ���������˵���-̨������
	public final static int RULEMAINTYPEKEY = 3;       //�����ĵ�������˵���
	public final static int BASICMAINTYPEKEY = 2;//��������������˵���
	public final static int CHENGWUZUOYE = 4;//������ҵ

	public final static int REPORT_DIANBAO_MAINTYPEKEY = 01;//̨�ʹ���--��·�籨�б������ֵ 
	public final static int REPORT_ANQUAN_MAINTYPEKEY = 11;//̨�ʹ���--��ȫ�����б������ֵ 
	public final static int REPORT_ANQUAN_A = 111;//̨�ʹ���--�г���������̨��
	public final static int REPORT_ANQUAN_B = 112;//̨�ʹ���--�г������ά̨��
	public final static int REPORT_ANQUAN_C = 113;//̨�ʹ���--��ȫ������ʾ��
	public final static int REPORT_ANQUAN_D = 114;//̨�ʹ���--��ȫ������·ʾ��ͼ
	public final static int REPORT_ANQUAN_CHAWEI = 115;//̨�ʹ���--��ȫ����--��Σ̨���б������ֵ 
	public final static int REPORT_ANQUAN_CHAWEI_A = 116;//̨�ʹ���--��λְ��
	public final static int REPORT_ANQUAN_CHAWEI_B = 117;//̨�ʹ���--
	public final static int REPORT_ANQUAN_CHAWEI_C = 118;//̨�ʹ���--��Σ�ļ�
	
	public final static int REPORT_CHENGWU_MAINTYPEKEY = 12;//̨�ʹ���--��������б������ֵ 
	public final static int REPORT_CHENGWU_A = 121;//̨�ʹ���--���񱨸�
	public final static int REPORT_CHENGWU_B = 122;//̨�ʹ���--������־
	public final static int REPORT_CHENGWU_C = 123;//̨�ʹ���--�ÿ��ܶȱ�
	public final static int REPORT_CHENGWU_D = 124;//̨�ʹ���--�����¼
	public final static int REPORT_CHENGWU_E = 125;//̨�ʹ���--�����ƶ�ժ��
	public final static int REPORT_CHENGWU_F = 126;//̨�ʹ���--һ������ҵָ����
	public final static int REPORT_CHENGWU_G = 127;//̨�ʹ���--��λְ��������
	public final static int REPORT_CHENGWU_H = 128;//̨�ʹ���--��������
	public final static int REPORT_CHENGWU_J = 129;//̨�ʹ���--���˼�¼
	
	public final static int REPORT_LUFENG_MAINTYPEKEY = 13;//̨�ʹ���--·������б������ֵ 
	public final static int REPORT_LUFENG_A = 131;//̨�ʹ���--·�������ʾ��
	public final static int REPORT_LUFENG_B = 132;//̨�ʹ���--·��ؼ�������ش�ʩ
	public final static int REPORT_LUFENG_C = 133;//̨�ʹ���--��Ӫ����λͼ
	public final static int REPORT_LUFENG_D = 134;//̨�ʹ���--�ÿ�Խϯ̽�õǼ�
	
	public final static int REPORT_SHOURU_MAINTYPEKEY = 14;//̨�ʹ���--������� 
	
	public final static int REPORT_ZHIJIAO_MAINTYPEKEY = 15;//̨�ʹ���--ְ����ѵ
	public final static int REPORT_ZHIJIAO_PEIXUN = 151;//̨�ʹ���--ְ����ѵ
	public final static int REPORT_ZHIJIAO_PEIXUN_A = 1511;//̨�ʹ���--ְ����ѵ-��ְ��
	public final static int REPORT_ZHIJIAO_PEIXUN_B = 1512;//̨�ʹ���--ְ����ѵ-����ְ��
	public final static int REPORT_ZHIJIAO_PEIXUN_C = 1513;//̨�ʹ���--ְ����ѵ-����
	public final static int REPORT_ZHIJIAO_TIKU = 152;//̨�ʹ���--ְ����ѵ
	public final static int REPORT_ZHIJIAO_TIKU_A = 1521;//̨�ʹ���--ְ����ѵ-�г���
	public final static int REPORT_ZHIJIAO_TIKU_B = 1522;//̨�ʹ���--ְ����ѵ-ֵ��Ա
	public final static int REPORT_ZHIJIAO_TIKU_C = 1523;//̨�ʹ���--ְ����ѵ-�г�Ա
	public final static int REPORT_ZHIJIAO_TIKU_D = 1524;//̨�ʹ���--ְ����ѵ-����Ա
	
	public final static int BASIC_GAIKUANG_MAINTYPEKEY = 21;//��������--��������ſ�
	public final static int BASIC_DANGAN_MAINTYPEKEY = 22;//��������--���˹�������
	public final static int BASIC_TIKU_MAINTYPEKEY = 23;//��������--���⶯̬��
	
	public final static int BASIC_DANGQUN_MAINTYPEKEY = 24;//��������--��Ⱥ��������˵���
	public final static int BASIC_DANGQUN_DANGJIAN = 241;//��������--��Ⱥ��������˵���-��֧������
	public final static int BASIC_DANGQUN_DANGJIAN_A = 2411;//��������--��Ⱥ��������˵���-��֧������-�����ſ�һ����
	public final static int BASIC_DANGQUN_DANGJIAN_B = 2412;//��������--��Ⱥ��������˵���-��֧������-����ְ��
	public final static int BASIC_DANGQUN_DANGJIAN_C = 2413;//��������--��Ⱥ��������˵���-��֧������-����֪ʶ
	public final static int BASIC_DANGQUN_JIAOYU = 242;//��������--��Ⱥ��������˵���-����
	public final static int BASIC_DANGQUN_JIAOYU_A = 2421;//��������--��Ⱥ��������˵���-����-��������
	public final static int BASIC_DANGQUN_JIAOYU_B = 2422;//��������--��Ⱥ��������˵���-����-����ֽ
	public final static int BASIC_DANGQUN_JIAOYU_C = 2423;//��������--��Ⱥ��������˵���-����-�Ļ�����
	public final static int BASIC_DANGQUN_JIAOYU_D = 2424;//��������--��Ⱥ��������˵���-����-��������
	public final static int BASIC_DANGQUN_JIAOYU_E = 2425;//��������--��Ⱥ��������˵���-����-���߽��
	public final static int BASIC_DANGQUN_GONGHUI = 243;//��������--��Ⱥ��������˵���-����
	public final static int BASIC_DANGQUN_GONGHUI_A = 2431;//��������--��Ⱥ��������˵���-����-С��ſ�
	public final static int BASIC_DANGQUN_GONGHUI_B = 2432;//��������--��Ⱥ��������˵���-����-��������
	public final static int BASIC_DANGQUN_TUANJIAN = 244;//��������--��Ⱥ��������˵���-��֧������
	public final static int BASIC_DANGQUN_TUANJIAN_A = 2441;//��������--��Ⱥ��������˵���-��֧������-��֧���ſ�
	public final static int BASIC_DANGQUN_TUANJIAN_B = 2442;//��������--��Ⱥ��������˵���-��֧������-��֧���
	
	public final static int RULEMAINTYPEKEY_A = 31;       //�����ĵ�--���ɷ���
	public final static int RULEMAINTYPEKEY_B = 32;       //�����ĵ�--���˹���
	public final static int RULEMAINTYPEKEY_C = 33;       //�����ĵ�--��Ч��������
	public final static int RULEMAINTYPEKEY_D = 34;       //�����ĵ�--��Ч�ĵ��ѯ
	public final static int RULEMAINTYPEKEY_E = 35;       //�����ĵ�--Ӧ��Ԥ��
	public final static int RULEMAINTYPEKEY_F = 36;       //�����ĵ�--·��淶
	
	public final static int SMSINFOLIST = 500;//�ѷ����г��ٱ���Ϣ�б�
	
	
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
	public final static String INIT_CHENGWU_Name = "kyd.db";  //���񱨸����ݿ�
	public final static String INIT_CHENGWU = DB_FOLDER_PATH +INIT_CHENGWU_Name;  //���񱨸����ݿ�
	public final static String cheduiName = "��ɳ����";
	public final static String banzuName = "��ɳ����";
//	public static ArrayList<CwReport1> cwReport1 ;
	public final static String TEAM_ID = "now_team_id";
	public static int ChengWu_Leader_ID = 0;
	public static int ChengWu_Leader_IDCount = 0;
	public static String reportCode = "";
	public static ArrayList<String> whichJson = new ArrayList<String>();
	//��ȫ������ʾ��ͼ
	
	public static String XINGZOU_NAME_A = XINGZOU_PATH + "jn.png";//���Ͽ�������ȫ������ʾ��ͼ
	public static String XINGZOU_NAME_B = XINGZOU_PATH + "nt.png";//��ͨ��Ԣ����Ա��ȫ������ʾ��ͼ
	public static String XINGZOU_NAME_C = XINGZOU_PATH + "wx.png";//������Ԣ����Ա��ȫ������ʾ��ͼ
	public static String XINGZOU_NAME_D = XINGZOU_PATH + "yt.png";//��̨��Ԣ����Ա��ȫ������ʾ��ͼ
	public static int XINGZOU_NAME_KEY_A = 1141;//���Ͽ�������ȫ������ʾ��ͼ
	public static int XINGZOU_NAME_KEY_B = 1142;//��ͨ��Ԣ����Ա��ȫ������ʾ��ͼ
	public static int XINGZOU_NAME_KEY_C = 1143;//������Ԣ����Ա��ȫ������ʾ��ͼ
	public static int XINGZOU_NAME_KEY_D = 1144;//��̨��Ԣ����Ա��ȫ������ʾ��ͼ
	//ȡ�����ݿ����ݣ�Ȼ���ĵ��洢
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
	//ȡ����־����Ϣ
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
	//���ɳ��񱨸��json�ĵ�
	public final static boolean CHENGWU_JSON = false;
	public static int isAdapter_RiZhiRenYuan = 0;
//	public static AdapterForLinearLayout adpater;
	public static int SendJsonByThreadNum = 0;//��¼��Ҫ���͵��ļ�������
	public static int SendJsonByThreadCount = 0;//�ۼӷ����ļ��ɹ��ĸ���
	public static String saveJsonResout = Upload_Path + "resout";
	
	public final static String BingCls = "���¼ٹ���";
	public final static String NOW_GROUP = "now_group";
	public final static String NOW_GROUP_ID = "now_group_id"; //����
	public final static String NOW_DEPT = "now_dept";
	public final static String NOW_BUREAU = "now_bureau"; //��
	
	public final static String NOW_TEAM_ID = "now_team_id";
	
	public final static String NOW_TEAM = "now_team_id";
	public final static String NOW_TRAINSIGN = "now_trainsign";
	
	public final static String DEPTSTR = "[{\"DeptName\":\"���������˶�\",\"DeptCode\":\"HBB\",\"BureauCode\":\"HEB\"}," +
			"{\"DeptName\":\"����������˶�\",\"DeptCode\":\"QHX\",\"BureauCode\":\"HEB\"}," +
	"{\"DeptName\":\"ĵ�������˶�\",\"DeptCode\":\"MDB\",\"BureauCode\":\"HEB\"},{\"DeptName\":\"�������˶�\",\"DeptCode\":\"CCT\",\"BureauCode\":\"SY\"}," +
	"{\"DeptName\":\"�������˶�\",\"DeptCode\":\"SYT\",\"BureauCode\":\"SY\"},{\"DeptName\":\"�������˶�\",\"DeptCode\":\"DLT\",\"BureauCode\":\"SY\"}," +
	"{\"DeptName\":\"���ݿ��˶�\",\"DeptCode\":\"JZD\",\"BureauCode\":\"SY\"},{\"DeptName\":\"���ֿ��˶�\",\"DeptCode\":\"JLL\",\"BureauCode\":\"SY\"}," +
	"{\"DeptName\":\"�������˶�\",\"DeptCode\":\"BJP\",\"BureauCode\":\"BJ\"},{\"DeptName\":\"�����˶�\",\"DeptCode\":\"TJP\",\"BureauCode\":\"BJ\"}," +
	"{\"DeptName\":\"ʯ��ׯ���˶�\",\"DeptCode\":\"SJP\",\"BureauCode\":\"BJ\"},{\"DeptName\":\"̫ԭ���˶�\",\"DeptCode\":\"TYV\",\"BureauCode\":\"BY\"}," +
	"{\"DeptName\":\"��ͷ���˶�\",\"DeptCode\":\"BTC\",\"BureauCode\":\"HHHT\"},{\"DeptName\":\"֣�ݿ��˶�\",\"DeptCode\":\"ZZF\",\"BureauCode\":\"ZZ\"}," +
	"{\"DeptName\":\"�人���˶�\",\"DeptCode\":\"WHN\",\"BureauCode\":\"WH\"},{\"DeptName\":\"�������˶�\",\"DeptCode\":\"XFN\",\"BureauCode\":\"WH\"}," +
	"{\"DeptName\":\"�������˶�\",\"DeptCode\":\"XAY\",\"BureauCode\":\"XA\"},{\"DeptName\":\"���Ͽ��˶�\",\"DeptCode\":\"JNK\",\"BureauCode\":\"JN\"}," +
	"{\"DeptName\":\"�ൺ���˶�\",\"DeptCode\":\"QDK\",\"BureauCode\":\"JN\"},{\"DeptName\":\"�Ϸʿ��˶�\",\"DeptCode\":\"HFH\",\"BureauCode\":\"SH\"}," +
	"{\"DeptName\":\"�Ͼ����˶�\",\"DeptCode\":\"NJH\",\"BureauCode\":\"SH\"},{\"DeptName\":\"�Ϻ����˶�\",\"DeptCode\":\"SHH\",\"BureauCode\":\"SH\"}," +
	"{\"DeptName\":\"���ݿ��˶�\",\"DeptCode\":\"HZH\",\"BureauCode\":\"SH\"},{\"DeptName\":\"�ϲ����˶�\",\"DeptCode\":\"NCG\",\"BureauCode\":\"NC\"}," +
	"{\"DeptName\":\"���ݿ��˶�\",\"DeptCode\":\"FZS\",\"BureauCode\":\"NC\"},{\"DeptName\":\"���ݿ��˶�\",\"DeptCode\":\"GZQ\",\"BureauCode\":\"GZ\"}," +
	"{\"DeptName\":\"��ɳ���˶�\",\"DeptCode\":\"CSQ\",\"BureauCode\":\"GZ\"},{\"DeptName\":\"��ſ��˶�\",\"DeptCode\":\"SZQ\",\"BureauCode\":\"GZ\"}," +
	"{\"DeptName\":\"������˶�\",\"DeptCode\":\"ZVQ\",\"BureauCode\":\"GZ\"},{\"DeptName\":\"��ݸ�����˶�\",\"DeptCode\":\"DMQ\",\"BureauCode\":\"GZ\"}," +
	"{\"DeptName\":\"�������˶�\",\"DeptCode\":\"NNZ\",\"BureauCode\":\"NN\"},{\"DeptName\":\"�ɶ����˶�\",\"DeptCode\":\"CDW\",\"BureauCode\":\"CD\"}," +
	"{\"DeptName\":\"������˶�\",\"DeptCode\":\"CQW\",\"BureauCode\":\"CD\"},{\"DeptName\":\"�������˶�\",\"DeptCode\":\"GIW\",\"BureauCode\":\"CD\"}," +
	"{\"DeptName\":\"�������˶�\",\"DeptCode\":\"KMM\",\"BureauCode\":\"KM\"},{\"DeptName\":\"���ݿ��˶�\",\"DeptCode\":\"LZJ\",\"BureauCode\":\"LZ\"}," +
	"{\"DeptName\":\"�������˶�\",\"DeptCode\":\"YIJ\",\"BureauCode\":\"LZ\"},{\"DeptName\":\"��³ľ����˶�\",\"DeptCode\":\"WMR\",\"BureauCode\":\"WLMQ\"}," +
	"{\"DeptName\":\"�������˶�\",\"DeptCode\":\"XNO\",\"BureauCode\":\"QZ\"}]";
	
	
	public static final int VIDEO = 1;
	public static final int AUDIO = 2;
	public static final int IMAGE = 3;

	
	public static final int NEEDTIPWEB_NOTIFICATIN_ID = 98;
	
	public final static boolean ISENCRYPT = false;
	public final static int UDP_CLIENT_PORT = 8000; // UDP�ͻ��˼����Ķ˿�
	public final static int UDP_SERVER_PORT = 8088;//20001;//UDP����˼����Ķ˿�
	public final static int TCP_SERVER_PORT = 8089; //TCP����˼����˿�
	public final static byte[] UDPRETURNSUCCESS = {0x10,0x06};
	public final static String IMEI = "imei";
	
	public Constant(String cw,String cwrz){
		Upload_CW_Name = cw;
		Upload_CWRZ_Name = cwrz;
		//���񱨸�
		Upload_CwReport1_Name = Upload_Path +Upload_CW_Name+ "CwReport1";
		Upload_CwReport2_Name = Upload_Path +Upload_CW_Name+ "CwReport2";
		Upload_CwReport3_Name = Upload_Path +Upload_CW_Name+ "CwReport3";
		Upload_CwReport4_Name = Upload_Path +Upload_CW_Name+ "CwReport4";
		Upload_CwReport5_Name = Upload_Path +Upload_CW_Name+ "CwReport5";
		Upload_CwReport6_Name = Upload_Path +Upload_CW_Name+ "CwReport6";
		Upload_CwReportName_Name = Upload_Path +Upload_CW_Name+ "CwReportName";

		//ȡ����־����Ϣ
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
