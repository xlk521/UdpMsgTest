package com.example.udpmsgtest.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.udpmsgtest.entity.GpsEntity;
import com.example.udpmsgtest.login.entity.BureauEntity;
import com.example.udpmsgtest.login.entity.CrewEntity;
import com.example.udpmsgtest.login.entity.DeptEntity;
import com.example.udpmsgtest.login.entity.GroupEntity;
import com.example.udpmsgtest.login.entity.TeamEntity;
import com.example.udpmsgtest.login.entity.TrainLeaderTime;
import com.example.udpmsgtest.publicfunction.Constant;
import com.example.udpmsgtest.wenjian.entity.TkyDbTest;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * 
 * ���� �� ���ݿ�����࣬����ʹ����ormlite�������־û���� ����OrmLiteSqliteOpenHelper�������ݿ�
 * 
 * @author hejuan
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static DatabaseHelper dbconn = null;
	protected SQLiteDatabase mSQLiteDatabase=null;

	public DatabaseHelper(Context context) {
		super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
	}

	public static DatabaseHelper getDBHelperInstances(Context context) {
		if (dbconn == null){
			dbconn = new DatabaseHelper(context);
		}
		return dbconn;
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		/**
		 * ������ݿ���Ҫ���ĳ�ű������·���
		 * 
		 */
		try {
			TableUtils.createTable(connectionSource, TkyDbTest.class);
			TableUtils.createTable(connectionSource, GpsEntity.class);
			TableUtils.createTable(connectionSource, BureauEntity.class);
			TableUtils.createTable(connectionSource, CrewEntity.class);
			TableUtils.createTable(connectionSource, DeptEntity.class);
			TableUtils.createTable(connectionSource, GroupEntity.class);
			TableUtils.createTable(connectionSource, TeamEntity.class);
			TableUtils.createTable(connectionSource, TrainLeaderTime.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion,
			int newVersion) {
		/**
		 * ������ݿ�ĳ�ű���Ҫ������ֶ������·���������Ҫɾ�������´���
		 * 
		 *  Dao<Gift, Integer> dao = DaoManager.createDao(connectionSource, Gift.class);
		 *	dao.executeRaw("ALTER TABLE `account` ADD COLUMN age INTEGER;");
		 */
		
		try {
			TableUtils.createTable(connectionSource, CrewEntity.class);
			TableUtils.createTable(connectionSource, DeptEntity.class);
			TableUtils.createTable(connectionSource, GroupEntity.class);
			TableUtils.createTable(connectionSource, TeamEntity.class);
			TableUtils.createTable(connectionSource, TrainLeaderTime.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		super.close();
	}

	/**
	 * ����������dao����
	 * 
	 * @param clazz
	 *            ʵ���� �����
	 */
	public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) {
		try {
			return super.getDao(clazz);
		} catch (Exception e) {
			Log.e("DatabaseHelper -- getDao ", e.getMessage().toString());
			return null;
		}
	}
	

	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
//		Tools.Log("DatabaseHelper","getWritableDatabase;start;");
		if(mSQLiteDatabase != null){
//			Tools.Log("DatabaseHelper","getWritableDatabase;mSQLiteDatabase="+mSQLiteDatabase+";");
		}else{
//			Tools.Log("DatabaseHelper","getWritableDatabase;mSQLiteDatabase=null");
		}
		if (mSQLiteDatabase != null && mSQLiteDatabase.isOpen() && !mSQLiteDatabase.isReadOnly()) {
            return mSQLiteDatabase;  // The database is already open for business
        }else{
        	mSQLiteDatabase = SQLiteDatabase.openDatabase(Constant.DB_PATH, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);//SQLiteDatabase.openDatabase(Constant.DB_PATH, null,SQLiteDatabase.OPEN_READWRITE);
        	return mSQLiteDatabase;
        }
		
	}

	public synchronized SQLiteDatabase getReadableDatabase() {
		return SQLiteDatabase.openDatabase(Constant.DB_PATH, null,
				SQLiteDatabase.OPEN_READONLY);
	}

}
