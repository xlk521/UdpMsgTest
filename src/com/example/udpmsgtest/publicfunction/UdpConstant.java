package com.example.udpmsgtest.publicfunction;

public class UdpConstant {

	//关于速报中的一些Key值的设置
	public static final String SbMsg = "SbMsg";
	public static final String SbSaveDbStr = "SbSaveDbStr";
	public static final String SbMsgType = "SbMsgType";
	public static final String SbIsNeedSave = "SbIsNeedSave";
	
	public static final String SbType = "SB";//速报的标识
	//服务端返回数据的相关设定
	public static final int ReceiveResultOk = 1;//接收服务端返回的数据：包和数据均完整的成功讯息
	public static final int ReceiveResultNull = 0;//接收服务端返回的数据：包完整，数据为空的失败的讯息
	public static final int ReceiveResultError = -1;//接收服务端返回的数据：包中的数据丢失的失败讯息
	public static final int ReceiveResultTimeOut = 3;//接收服务端返回的数据：接收的时间超时
	public static final int ReceiveResultOther = 100;//接收服务端返回的数据：包中的数据丢失的失败讯息
	public static final int UdpSending = 2;//开始发送数据
	public static final String returnResoutOk = "O";//#返回成功数据标识"WordGet"
	public static final String returnResoutNull = "N";//#返回空数据标识"WordNull"
	public static final String returnResoutError = "E";//#返回数据包错误数据标识“UDPPackagesError”
	
	//关闭加载条
	public static final String closeProgress = "closeProgress";
	//无法获取手机当前的ip地址
	public static final int getPhoneIp = 4;
	//选择TCP或者是UDP发送模式
	public static final boolean isTcpOrUdp = false;//标识UDP发送模式
}
