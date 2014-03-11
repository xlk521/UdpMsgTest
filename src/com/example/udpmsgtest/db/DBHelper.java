package com.example.udpmsgtest.db;

import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public abstract class DBHelper<T> {
	protected SQLiteDatabase mSQLiteDatabase=null;
	protected DatabaseHelper mDatabaseHelper=null;
	protected Context mContext = null;
	protected Cursor cursor;
	
	public void open() {
		mDatabaseHelper = new DatabaseHelper(mContext);
		mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
	}

	public void close() {
		if (null != mDatabaseHelper) {
			mDatabaseHelper.close();
		}
		if (null != cursor) {
			cursor.close();
		}
	}
	
	/**
	 * 插入数据
	 * @param tableName 表名
	 * @param nullColumn null
	 * @param contentValues 名值对
	 * @return 新插入数据的ID，错误返回-1
	 * @throws Exception
	 */
	public long insert(String tableName, String nullColumn,
			ContentValues contentValues) throws Exception {
		try {
			return mSQLiteDatabase.insert(tableName, nullColumn, contentValues);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 通过主键ID删除数据
	 * @param tableName 表名
	 * @param key 主键名
	 * @param id 主键值
	 * @return 受影响的记录数
	 * @throws Exception
	 */
	public long delete(String tableName, String key, int id) throws Exception {
		try {
			return mSQLiteDatabase.delete(tableName, key + " = " + id, null);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public int delete(String coloum,String value,String tableName){
		try {
			return mSQLiteDatabase.delete(tableName, coloum!=null ? (coloum+ "=?") : null,
							coloum!=null ? (new String[]{value}):null);
		} catch (Exception e) {
			return -1;
		}
	}
	
	
	/**
	 * 查找表的所有数据
	 * @param tableName 表名
	 * @param columns 如果返回所有列，则填null
	 * @return
	 * @throws Exception
	 */
	public Cursor findAll(String tableName, String [] columns) throws Exception{
		try {
			cursor = mSQLiteDatabase.query(tableName, columns, null, null, null, null, null);
			cursor.moveToFirst();
			return cursor;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 根据主键查找数据
	 * @param tableName 表名
	 * @param key 主键名
	 * @param id  主键值
	 * @param columns 如果返回所有列，则填null
	 * @return Cursor游标
	 * @throws Exception 
	 */
	public Cursor findById(String tableName, String key, int id, String [] columns) throws Exception {
		try {
			return mSQLiteDatabase.query(tableName, columns, key + " = " + id, null, null, null, null);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 根据字段查询
	 * @param tableName 表名
	 * @param key 字段名
	 * @param value  字段值
	 * @param columns 如果返回所有列，则填null
	 * @return Cursor游标
	 * @throws Exception 
	 */
	public Cursor findByColumn(String tableName, String key, String value, String [] columns) throws Exception {
		try {
			return mSQLiteDatabase.query(tableName, columns, key + "=?", new String[]{value}, null, null, null);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 根据条件查询数据
	 * @param tableName 表名
	 * @param names 查询条件
	 * @param values 查询条件值
	 * @param columns 如果返回所有列，则填null
	 * @param orderColumn 排序的列
	 * @param limit 限制返回数
	 * @return Cursor游标
	 * @throws Exception
	 */
	public Cursor find(String tableName, String [] names, String [] values, String [] columns, String orderColumn, String limit) throws Exception{
		try {
			StringBuffer selection = new StringBuffer();
			for (int i = 0; i < names.length; i++) {
				selection.append(names[i]);
				selection.append(" = ?");
				if (i != names.length - 1) {
					selection.append(",");
				}
			}
			cursor = mSQLiteDatabase.query(true, tableName, columns, selection.toString(), values, null, null, orderColumn, limit);
			cursor.moveToFirst();
			return cursor;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * @param tableName 表名
	 * @param names 查询条件
	 * @param values 查询条件值
	 * @param args 更新列-值对
	 * @return true或false
	 * @throws Exception
	 */
	public boolean udpate(String tableName, String [] names, String [] values, ContentValues args) throws Exception{
		try {
			StringBuffer selection = new StringBuffer();
			for (int i = 0; i < names.length; i++) {
				selection.append(names[i]);
				selection.append(" = ?");
				if (i != names.length - 1) {
					selection.append(",");
				}
			}
			return mSQLiteDatabase.update(tableName, args, selection.toString(), values) > 0;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 执行sql语句，包括创建表、删除、插入
	 * 
	 * @param sql
	 */
	public void executeSql(String sql) {
		mSQLiteDatabase.execSQL(sql);
	}
	
	public abstract long insertOrUpdate(T t,String tableName);
	public abstract boolean delete(long _id,String tableName);
	public abstract boolean deleteAll(String tableName);
	public abstract boolean update(long _id,T t,String tableName);
	public abstract T queryElement(long _id,String tableName);
	public abstract List<T> queryElement(String column,String value,String tableName);
	public abstract List<T> queryElement(String column,T t,String tableName);
	public abstract List<T> queryAll(int num,String tableName);
	public abstract List<T> queryAll(String tableName);
	public abstract boolean isExists(long _id,String tableName);
	public abstract long getSize(String tableName,String column,String value);
}