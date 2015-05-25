package com.example.pocket;

import com.example.pocket.DATA_TABLE.TableInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBoperations extends SQLiteOpenHelper {

	public static final int database_version=2;
	public String CREATE_QUERY = "CREATE TABLE "+TableInfo.TABLE_NAME+"("+TableInfo.USER_NAME+" TEXT,"+TableInfo.MONEY+" FLOAT,"+TableInfo.DATETIME+" TEXT);";
	
	public DBoperations(Context context) {
		super(context,TableInfo.DATABASE_NAME, null, database_version);
		Log.d("Database operations", "database created");
		
	}

	@Override
	public void onCreate(SQLiteDatabase sdb) {
		// TODO Auto-generated method stub
		sdb.execSQL(CREATE_QUERY);
		Log.d("Database operations", "table created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	public void putInformation(DBoperations dop,String name,float cash, String now)
	{
		SQLiteDatabase SQ=dop.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(TableInfo.USER_NAME,name);
		cv.put(TableInfo.MONEY, cash);
		cv.put(TableInfo.DATETIME,now);
		long k=SQ.insert(TableInfo.TABLE_NAME, null,cv);
		Log.d("Database operations", "one row inserted");
		
	}
	public Cursor getInformation(DBoperations dop)
	{
		SQLiteDatabase SQ =dop.getReadableDatabase();
		String[] columns={TableInfo.USER_NAME,TableInfo.MONEY,TableInfo.DATETIME};
		Cursor CR=SQ.query(TableInfo.TABLE_NAME, columns, null, null,null, null, null);
		return CR;
		
		
	}

	
}
