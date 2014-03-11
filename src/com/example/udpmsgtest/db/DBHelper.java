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
	 * ��������
	 * @param tableName ����
	 * @param nullColumn null
	 * @param contentValues ��ֵ��
	 * @return �²������ݵ�ID�����󷵻�-1
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
	 * ͨ������IDɾ������
	 * @param tableName ����
	 * @param key ������
	 * @param id ����ֵ
	 * @return ��Ӱ��ļ�¼��
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
	 * ���ұ����������
	 * @param tableName ����
	 * @param columns ������������У�����null
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
	 * ����������������
	 * @param tableName ����
	 * @param key ������
	 * @param id  ����ֵ
	 * @param columns ������������У�����null
	 * @return Cursor�α�
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
	 * �����ֶβ�ѯ
	 * @param tableName ����
	 * @param key �ֶ���
	 * @param value  �ֶ�ֵ
	 * @param columns ������������У�����null
	 * @return Cursor�α�
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
	 * ����������ѯ����
	 * @param tableName ����
	 * @param names ��ѯ����
	 * @param values ��ѯ����ֵ
	 * @param columns ������������У�����null
	 * @param orderColumn �������
	 * @param limit ���Ʒ�����
	 * @return Cursor�α�
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
	 * @param tableName ����
	 * @param names ��ѯ����
	 * @param values ��ѯ����ֵ
	 * @param args ������-ֵ��
	 * @return true��false
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
	 * ִ��sql��䣬����������ɾ��������
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