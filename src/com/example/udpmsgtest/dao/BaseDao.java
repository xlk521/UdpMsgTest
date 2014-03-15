package com.example.udpmsgtest.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;

import com.example.udpmsgtest.db.DBHelper;
import com.example.udpmsgtest.db.DatabaseHelper;
import com.example.udpmsgtest.entity.BaseEntity;
import com.j256.ormlite.dao.Dao;

/** 
 * 
 * @author hejuan   
 * @version 创建时间：2013-7-26
 */

public class BaseDao<T extends BaseEntity> extends DBHelper<T> { 

	private final static String TAG = "BaseDao";
	private Dao<T, String> dao ;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseDao (Context context,Class beanClass) {
//		Tools.Log(TAG, "BaseDao");
		DatabaseHelper helper = DatabaseHelper.getDBHelperInstances(context);
		dao = helper.getDao(beanClass);
		mSQLiteDatabase = helper.getWritableDatabase();
	}
	/**
	 * 查询全部实体
	 * **/
	public List<T> getList() {
		try {
			return dao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 插入一个实体
	 * **/
	public List<T> getList(Map<String, Object> params) {
		try {
			return dao.queryForFieldValues(params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int insert(T t) {
		try {
			return dao.create(t);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 根据某个属性查询实体
	 * **/
	public List<T> getByParams(String field,Serializable fieldValue){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put(field, fieldValue);
		try {
			return dao.queryForFieldValues(params);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据属性查询实体
	 * **/
	public List<T> getByParams(Map<String,Object> params){
		try {
			return dao.queryForFieldValues(params);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 模糊查询
	 * **/
	public List<T> getBYFoggy(String str, String obj){
		try {
			return dao.queryBuilder().where().like(str, obj).query();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 查询某区间内数据
	 * **/
	public List<T> getBYQuality(String columnName,String low, String high){
		try {
			return dao.queryBuilder().where().between(columnName, low, high).query();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 查询某区间内数据
	 * **/
	public List<T> getBetweenAndByCol(String betcolumnName,String low, String high,String columnName,String value){
		try {
			return dao.queryBuilder().where().between(betcolumnName, low, high).ge(columnName, value).query();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//排序
	public List<T> getOrderBuilder(String columnName, boolean ascending,String queryColumnName, String queryValue){
			try {
				if(queryColumnName == null){
					return (dao.queryBuilder().orderBy(columnName, ascending)).query();
				}else{
					return (dao.queryBuilder().orderBy(columnName, ascending)).where().ge(queryColumnName, queryValue).query();//where().like(queryColumnName, queryValue).query();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	
//	//查询并按某字段排序结果
//	@SuppressWarnings("unchecked")
//	public List<T> getByParamAndOrder(String columnName,boolean ascending,Map<String,Object> params){
//		QueryBuilder<T, String> bulider = getOrderBuilder(columnName,ascending);
//		try {
//			if(params == null){
//				return bulider.query();
//			}else{
//				return bulider.where().eq(columnName, value);//((Dao<T, String>) bulider).queryForFieldValues(params);	
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	
//	/**
//	 * 查询一条数据
//	 * **/
//	public T getOneBYQuality(PreparedQuery<T> query){
//		try {
//			PreparedQuery<T> preparedQuery = new PreparedQuery<T>() {
//
//				@Override
//				public CompiledStatement compile(DatabaseConnection arg0,
//						StatementType arg1) throws SQLException {
//					// TODO Auto-generated method stub
//					return null;
//				}
//
//				@Override
//				public CompiledStatement compile(DatabaseConnection arg0,
//						StatementType arg1, int arg2) throws SQLException {
//					// TODO Auto-generated method stub
//					return null;
//				}
//
//				@Override
//				public String getStatement() throws SQLException {
//					// TODO Auto-generated method stub
//					return null;
//				}
//
//				@Override
//				public StatementType getType() {
//					// TODO Auto-generated method stub
//					return null;
//				}
//
//				@Override
//				public void setArgumentHolderValue(int arg0, Object arg1)
//						throws SQLException {
//					// TODO Auto-generated method stub
//					
//				}
//
//				@Override
//				public T mapRow(DatabaseResults arg0) throws SQLException {
//					// TODO Auto-generated method stub
//					return null;
//				}
//			};
//			return dao.queryForFirst(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	/**
	 * 按ID过滤掉重复项
	 * **/
	public List<T> getDistinctList(String columnName){
		try {
			return dao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 通过ID删除某一条数据
	 *
	 * @author hejuan
	 * @version 2013-7-26
	 *
	 * @param id
	 * @return
	 */
	public int delById(String id) {
		try {
			return dao.deleteById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 删除某一条数据
	 *
	 * @author hejuan
	 * @version 2013-7-26
	 *
	 * @param id
	 * @return
	 */
	public int delObject(T t) {
		try {
			return dao.delete(t);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 删除所有数据
	 *
	 * @author hejuan
	 * @version 2013-7-26
	 *
	 * @return
	 */
	public int delAllData() {
		int count = 0;
		try {
//			count += dao.delete(this.getList());
			for(T t : this.getList()){
				count += dao.delete(t);
			}
//			Tools.Log(TAG, "delAllData;after delte all count="+count);
		} catch (SQLException e) {
			e.printStackTrace();
			return count; 
		}
		return count;
	}
	
	public int delByParam() {
		return 0;
	}
	
	/**
	 * 更新对象
	 *
	 * @author hejuan
	 * @version 2013-7-26
	 *
	 * @param t
	 * @return
	 */
	public int updateObject(T t) {
		try {
			return dao.update(t);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public long insertOrUpdate(T t, String tableName) {
		return 0;
	}
	@Override
	public boolean delete(long _id, String tableName) {
		return false;
	}
	@Override
	public boolean deleteAll(String tableName) {
		return false;
	}
	@Override
	public boolean update(long _id, T t, String tableName) {
		return false;
	}
	@Override
	public T queryElement(long _id, String tableName) {
		return null;
	}
	@Override
	public List<T> queryElement(String column, String value, String tableName) {
		return null;
	}
	@Override
	public List<T> queryElement(String column, T t, String tableName) {
		return null;
	}
	@Override
	public List<T> queryAll(int num, String tableName) {
		return null;
	}
	@Override
	public List<T> queryAll(String tableName) {
		return null;
	}
	@Override
	public boolean isExists(long _id, String tableName) {
		return false;
	}
	@Override
	public long getSize(String tableName, String column, String value) {
		return 0;
	}
}
