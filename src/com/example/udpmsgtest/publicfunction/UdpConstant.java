package com.example.udpmsgtest.publicfunction;

public class UdpConstant {

	//�����ٱ��е�һЩKeyֵ������
	public static final String SbMsg = "SbMsg";
	public static final String SbSaveDbStr = "SbSaveDbStr";
	public static final String SbMsgType = "SbMsgType";
	public static final String SbIsNeedSave = "SbIsNeedSave";
	
	public static final String SbType = "SB";//�ٱ��ı�ʶ
	//����˷������ݵ�����趨
	public static final int ReceiveResultOk = 1;//���շ���˷��ص����ݣ��������ݾ������ĳɹ�ѶϢ
	public static final int ReceiveResultNull = 0;//���շ���˷��ص����ݣ�������������Ϊ�յ�ʧ�ܵ�ѶϢ
	public static final int ReceiveResultError = -1;//���շ���˷��ص����ݣ����е����ݶ�ʧ��ʧ��ѶϢ
	public static final int ReceiveResultTimeOut = 3;//���շ���˷��ص����ݣ����յ�ʱ�䳬ʱ
	public static final int ReceiveResultOther = 100;//���շ���˷��ص����ݣ����е����ݶ�ʧ��ʧ��ѶϢ
	public static final int UdpSending = 2;//��ʼ��������
	public static final String returnResoutOk = "O";//#���سɹ����ݱ�ʶ"WordGet"
	public static final String returnResoutNull = "N";//#���ؿ����ݱ�ʶ"WordNull"
	public static final String returnResoutError = "E";//#�������ݰ��������ݱ�ʶ��UDPPackagesError��
	
	//�رռ�����
	public static final String closeProgress = "closeProgress";
	//�޷���ȡ�ֻ���ǰ��ip��ַ
	public static final int getPhoneIp = 4;
	//ѡ��TCP������UDP����ģʽ
	public static final boolean isTcpOrUdp = false;//��ʶUDP����ģʽ
}
