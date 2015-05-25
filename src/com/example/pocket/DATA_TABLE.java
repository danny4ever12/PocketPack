package com.example.pocket;
import android.provider.BaseColumns;

public class DATA_TABLE {

	public DATA_TABLE()
	{
		
	}
	
	public static abstract class TableInfo implements BaseColumns
	{
		public static final String USER_NAME="user_name";
		public static final String MONEY="money";
		public static final String DATETIME="date_time";
		public static final String DATABASE_NAME="POCKET";
		public static final String TABLE_NAME="money_tab";
	}
}
