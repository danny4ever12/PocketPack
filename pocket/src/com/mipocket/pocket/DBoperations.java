package com.mipocket.pocket;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mipocket.pocket.DATA_TABLE.TableInfo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBoperations extends SQLiteOpenHelper {

	public static final int database_version=2;

	public String CREATE_USERPASS="CREATE TABLE "+TableInfo.TABLE_USER+"("+TableInfo.NAME+" TEXT,"+TableInfo.PASSWORD+" TEXT);";
	
	//moneytab
	public String CREATE_QUERY = "CREATE TABLE "+TableInfo.TABLE_NAME+"("+TableInfo.USER_NAME+" TEXT,"+TableInfo.MONEY+" FLOAT,"+TableInfo.DATETIME+" TEXT);";
	
	//daily expense
	public String CREATE_DAILY="CREATE TABLE "+TableInfo.TABLE_DLY+"("+TableInfo.OCCATION+" TEXT,"+TableInfo.MONEY+" FLOAT,"+TableInfo.DATETIME+" TEXT);";
	
	//day's total
	public String CREATE_DAYS_TOTAL="CREATE TABLE "+TableInfo.TABLE_DLY_EXP+"("+TableInfo.DATE+" TEXT,"+TableInfo.DAY+" TEXT,"+TableInfo.TOTAL+" FLOAT);";
	
	//to store simple sum
	public String CREATE_SUM="CREATE TABLE "+TableInfo.TABLE_SUM+"("+TableInfo.SUM+" FLOAT);";
	
	public DBoperations(Context context) {
		super(context,TableInfo.DATABASE_NAME, null, database_version);
		Log.d("Database operations", "database created");
		
	}

	@Override
	public void onCreate(SQLiteDatabase sdb) {
		
		sdb.execSQL(CREATE_USERPASS);
		sdb.execSQL(CREATE_QUERY);	
		sdb.execSQL(CREATE_DAILY);
		sdb.execSQL(CREATE_DAYS_TOTAL);
		sdb.execSQL(CREATE_SUM);
		
		Log.d("Database operations", "table created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public void putNamePass(DBoperations dop, String name, String pass)
	{
		SQLiteDatabase SQ=dop.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(TableInfo.NAME,name);
		cv.put(TableInfo.PASSWORD, pass);
		@SuppressWarnings("unused")
		long k=SQ.insert(TableInfo.TABLE_USER, null,cv);
	}
	
	public void putSum(DBoperations dop, float cash)
	{
		SQLiteDatabase SQ=dop.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(TableInfo.SUM,cash);
		@SuppressWarnings("unused")
		long k=SQ.insert(TableInfo.TABLE_SUM, null,cv);
	}
	
	
	public void putInformation(DBoperations dop,String name,float cash, String now)
	{
		SQLiteDatabase SQ=dop.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(TableInfo.USER_NAME,name);
		cv.put(TableInfo.MONEY, cash);
		cv.put(TableInfo.DATETIME,now);
		@SuppressWarnings("unused")
		long k=SQ.insert(TableInfo.TABLE_NAME, null,cv);
		Log.d("Database operations", "one row inserted");
		
	}
	
	public void putDaily(DBoperations dop,String occatn,float cash,String now)
	{
		SQLiteDatabase SQ=dop.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(TableInfo.OCCATION,occatn);
		cv.put(TableInfo.MONEY, cash);
		cv.put(TableInfo.DATETIME,now);
		@SuppressWarnings("unused")
		long k=SQ.insert(TableInfo.TABLE_DLY, null,cv);
		Log.d("Database operations", "one row inserted");
	}
	
	@SuppressLint("SimpleDateFormat")
	public void putDaysTotal(DBoperations dop,float cash)
	{
		SQLiteDatabase SQ=dop.getWritableDatabase();
		ContentValues cv=new ContentValues();
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		
		Calendar calendar = Calendar.getInstance();
		int daynum = calendar.get(Calendar.DAY_OF_WEEK); 
		String day;
		if(daynum==1)
            day="sunday";
		else if(daynum==2)
			day="monday";
		else if(daynum==3)
			day="tuesday";
		else if(daynum==4)
			day="wednesday";
		else if(daynum==5)
			day="thursday";
		else if(daynum==6)
			day="friday";
		else if(daynum==7)
			day="saturday";
		else
			day="";
		cv.put(TableInfo.DATE,date);
		cv.put(TableInfo.TOTAL, cash);
		cv.put(TableInfo.DAY,day);
		@SuppressWarnings("unused")
		long k=SQ.insert(TableInfo.TABLE_DLY_EXP, null,cv);
		Log.d("Database operations", "one row inserted");
	}
	
	
	
	@SuppressLint("SimpleDateFormat")
	public int updateDebt(DBoperations dop, String dtm, Float mny)
	{
		SQLiteDatabase SQ=dop.getWritableDatabase();
		String DateandTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
		ContentValues cv=new ContentValues();
		cv.put(TableInfo.MONEY, mny);
		cv.put(TableInfo.DATETIME, DateandTime);
		String[] whereArgs={dtm};
		int count= SQ.update(TableInfo.TABLE_NAME,cv,TableInfo.DATETIME+" =? ",whereArgs);
		return count;
	}
	
	public int deleteUSRPSS(DBoperations dop)
	{
		SQLiteDatabase SQ=dop.getWritableDatabase();
		
		int count= SQ.delete(TableInfo.TABLE_USER,null,null);
		return count;
	}
	
	public int deleteDebt(DBoperations dop, String dtm)
	{
		SQLiteDatabase SQ=dop.getWritableDatabase();
		String[] whereArgs={dtm};
		int count= SQ.delete(TableInfo.TABLE_NAME,TableInfo.DATETIME+" =? ",whereArgs);
		return count;
	}
	
	
	public int deleteDaily(DBoperations dop, String dtm)
	{
		SQLiteDatabase SQ=dop.getWritableDatabase();
		String[] whereArgs={dtm};
		int count= SQ.delete(TableInfo.TABLE_DLY,TableInfo.DATETIME+" =? ",whereArgs);
		return count;
	}
	public int deleteDailyaLL(DBoperations dop)
	{
		SQLiteDatabase SQ=dop.getWritableDatabase();
	
		int count= SQ.delete(TableInfo.TABLE_DLY,null,null);
		return count;
	}
	
	
	//delete single day's expenditure
	public int deleteDailyTotal(DBoperations dop, String dtm)
	{
		SQLiteDatabase SQ=dop.getWritableDatabase();
		String[] whereArgs={dtm};
		int count= SQ.delete(TableInfo.TABLE_DLY_EXP,TableInfo.DATE+" =? ",whereArgs);
		return count;
	}
	
	//delete all day's expenditure
		public int deleteDailyTotalAll(DBoperations dop)
		{
			SQLiteDatabase SQ=dop.getWritableDatabase();
			int count= SQ.delete(TableInfo.TABLE_DLY_EXP,null,null);
			return count;
		}
	
		public void deleteSum(DBoperations dop)
		{
			SQLiteDatabase SQ=dop.getWritableDatabase();
			@SuppressWarnings("unused")
			int count= SQ.delete(TableInfo.TABLE_SUM,null,null);
		}
	
	
		public Cursor getUSRPSS(DBoperations dop)
		{
			SQLiteDatabase SQ =dop.getReadableDatabase();
			String[] columns={TableInfo.NAME,TableInfo.PASSWORD};
			Cursor CR=SQ.query(TableInfo.TABLE_USER, columns, null, null,null, null, null);
			return CR;
			
			
		}
		
		
	public Cursor getInformation(DBoperations dop)
	{
		SQLiteDatabase SQ =dop.getReadableDatabase();
		String[] columns={TableInfo.USER_NAME,TableInfo.MONEY,TableInfo.DATETIME};
		Cursor CR=SQ.query(TableInfo.TABLE_NAME, columns, null, null,null, null, null);
		return CR;
		
		
	}

	
	public Cursor getInfoDaily(DBoperations dop)
	{
		SQLiteDatabase SQ =dop.getReadableDatabase();
		String[] columns={TableInfo.OCCATION,TableInfo.MONEY,TableInfo.DATETIME};
		Cursor CR=SQ.query(TableInfo.TABLE_DLY, columns, null, null,null, null, null);
		return CR;
		
		
	}
	//for previous day
	public Cursor getInfoPrevDay(DBoperations dop)
	{
		SQLiteDatabase SQ =dop.getReadableDatabase();
		String[] columns={TableInfo.DATE,TableInfo.TOTAL,TableInfo.DAY};
		Cursor CR=SQ.query(TableInfo.TABLE_DLY_EXP, columns, null, null,null, null, null);
		return CR;
		
		
	}
	
	//for sum
	public Cursor getSum(DBoperations dop)
	{
		SQLiteDatabase SQ =dop.getReadableDatabase();
		String[] columns={TableInfo.SUM};
		Cursor CR=SQ.query(TableInfo.TABLE_SUM, columns, null, null,null, null, null);
		return CR;
		
		
	}
	
}
