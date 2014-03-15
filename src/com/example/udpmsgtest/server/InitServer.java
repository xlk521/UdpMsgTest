package com.example.udpmsgtest.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.udpmsgtest.dao.BaseDao;
import com.example.udpmsgtest.db.DatabaseHelper;
import com.example.udpmsgtest.entity.BaseEntity;
import com.example.udpmsgtest.publicfunction.Constant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class InitServer <T extends BaseEntity>{
	private final static String TAG = "InitService";
	public static final int INIT_PARKYARD = 0;
	public static final int INIT_PERFERENTIAL = 1;

	private BaseDao<T> dao;
	protected DatabaseHelper mDatabaseHelper=null;

	public InitServer(Context context, Class className) {
		dao = new BaseDao<T>(context, className);
		mDatabaseHelper = new DatabaseHelper(context);
	}
	
	/**
	 * 初始化数据
	 * 
	 * @author hejuan
	 * 
	 * @param datalist
	 * @return
	 */
	public boolean initTrain(List<T> dataList) {
		int count = 0;
		boolean delete = deleteTrain();//删除所有的原数据
//		Tools.Log(TAG, "initTrain;delete="+delete);
		for (T t : dataList) {
//			Tools.Log(TAG, "initTrain;befor insert count="+count);
			count += dao.insert(t);
//			Tools.Log(TAG, "initTrain;after insert count="+count);
		}
//		Tools.Log(TAG, "initTrain;insert count="+count);
		dao.close();
		if(count == dataList.size()){
			return true;
		}
		return false;
	}
	
	/**
	 * 初始化数据;不先删除原有数据，直接往数据库里添加
	 * 
	 * @author hejuan
	 * 
	 * @param datalist
	 * @return
	 */
	public boolean initNoDeleteTrain(List<T> dataList) {
		int count = 0;
		for (T t : dataList) {
//			Tools.Log(TAG, "initTrain;befor insert count="+count);
			count += dao.insert(t);
//			Tools.Log(TAG, "initTrain;after insert count="+count);
		}
//		Tools.Log(TAG, "initTrain;insert count="+count);
		dao.close();
		if(count == dataList.size()){
			return true;
		}
		return false;
	}
	
	public boolean deleteTrain(){
		int count = 0;
		count = dao.delAllData();
		dao.close();
		if(count > 0){
			return true;
		}
		return false;
	}
	/**
	 * 获取数据;查询
	 *
	 * @author Hejuan; 
	 *
	 *@param params	类型  
	 * @return
	 */
	public List<T> getClientInfoList(Map<String, Object> params) {
		List<T> dataList = new ArrayList<T>();
		if (params == null) {
			dataList = dao.getList();
		} else {
			dataList = dao.getByParams(params);
		}
		dao.close();
		if(dataList != null && dataList.size() > 0) {
			return dataList;
		}
		return null;
	}

	/**
	 * 获取数据;查询
	 *
	 * @author Hejuan; 
	 *
	 *@param params	类型  
	 * @return
	 */
	public List<T> getClientInfoList() {
		return getClientInfoList(null);
	}
	
	/**
	 * 获取数据;模糊查询
	 *
	 * @author Hejuan; 
	 *
	 *@param params	类型  
	 * @return
	 */
	public List<T> getInfoListFoggy(String str, String obj) {
		List<T> dataList = new ArrayList<T>();
		dataList = dao.getBYFoggy(str, obj);
//	    Tools.Log(TAG, "getInfoList;dao.getTest(str, obj)="+dataList.size());
	    dao.close();
		if(dataList != null && dataList.size() > 0) {
			return dataList;
		}
		return null;
	}
	
	/**
	 * 获取数据; 查询某字段某区间内数据
	 *
	 * @author Hejuan; 
	 *
	 *@param params	类型  
	 * @return
	 */
	public List<T> getInfoListQuality(String columnName,String str, String obj) {
		List<T> dataList = new ArrayList<T>();
		dataList = dao.getBYQuality(columnName,str, obj);
//	    Tools.Log(TAG, "getInfoListQuality;result="+dataList.size());
	    dao.close();
		if(dataList != null && dataList.size() > 0) {
			return dataList;
		}
		return null;
	}
	
	/**
	 * 获取数据; 按条件返回数据
	 *
	 * @author Hejuan; 
	 *
	 *@param params	类型  
	 * @return
	 */
	public List<T> getInfoListQualityByNum(String columnName,String str, String obj) {
		List<T> dataList = new ArrayList<T>();
		dataList = dao.getBYQuality(columnName,str, obj);
//	    Tools.Log(TAG, "getInfoListQuality;result="+dataList.size());
	    dao.close();
		if(dataList != null && dataList.size() > 0) {
			return dataList;
		}
		return null;
	}
	
	/**
	 * 添加多条数据
	 * 
	 * @author hejuan
	 * 
	 * @param dataList
	 * @return
	 */
	public boolean insertTrainClientList(List<T> dataList) {
		int count = 0;
		for (T t : dataList) {
			count += dao.insert(t);
		}
		dao.close();
		if (count == dataList.size()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 添加一条数据
	 * 
	 * @author hejuan
	 * 
	 * @param dataList
	 * @return
	 */
	public boolean insertTrainClient(T data) {
		int count = 0;
		count = dao.insert(data);
		dao.close();
		if(count > 0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 按照id查询详情
	 */
	public List<T> getOneDataByID(String Id){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", Id);
		List<T> list = dao.getByParams(params);
		dao.close();
		return list;
	}
	
	/**
	 * 按照id删除数据
	 */
	public int deleteOneById(String Id){
		int result = dao.delById(Id);
		dao.close();
		return result;
	}
	
	/**
	 * 删除一条数据
	 */
	public int deleteOneData(T data){
		int result = dao.delObject(data);
		dao.close();
		return result;
	}
	
	
	/**
	 * 按照条件查询详情
	 */
	public List<T> getOneDataByMyPar(Map<String, Object> params){
		List<T> list = dao.getByParams(params);
		dao.close();
		return list;
	}
	
	/**
	 * 按某字段进行排序;专用于乘务日志历史记录查询
	 */
	public List<T> getByParamAndOrder(String orderColumn, boolean ascending,String queryColumnName, String queryValue){
		List<T> list = dao.getOrderBuilder(orderColumn, ascending,queryColumnName,queryValue);
		dao.close();
		return list;
	}
	
	/**
	 * 更新新某一行信息
	 */
	public boolean updateObject(T t){
		boolean state = false;
		int result = dao.updateObject(t);
		if(result == 1){
			state = true;
		}
		return state;
	}
	
	/**
	 * 获取数据库中的第一条信息;用于获取局/段/车队/班组/人员信息,当前系统初始化数据时此类信息仅有当前出乘列车长相应的信息,故只取第一条即可
	 */
	public T getFirstOneData(){
		List<T> list = getClientInfoList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 按照 某一列值来取数据
	 */
	public T getOneDataByOneColum(String value,String columName){
		T t = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(columName, value);
		List<T> list = dao.getByParams(params);
		if(list != null && list.size() > 0){
			t = list.get(0);
		}
		dao.close();
		return t;
	}
	
	/**
	 * 获取数据; 查询某字段某区间内数据
	 *
	 * @author Hejuan; 
	 *
	 *@param params	类型  
	 * @return
	 */
	public List<T> getInfoListBAB(String columnName,String low, String high,String columnName1, String column1Value) {
		List<T> dataList = new ArrayList<T>();
		dataList = dao.getBetweenAndByCol(columnName, low, high, columnName1, column1Value);
//	    Tools.Log(TAG, "getInfoListQuality;result="+dataList.size());
	    dao.close();
		if(dataList != null && dataList.size() > 0) {
			return dataList;
		}
		return null;
	}
	
	public void insertAll(String databaseName, List<ContentValues> valuesArr) {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(Constant.DB_PATH, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
		db.beginTransaction();
		for (ContentValues val : valuesArr) {
			db.insert(databaseName, null, val);
		}

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}
}
