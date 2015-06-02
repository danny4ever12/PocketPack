package com.example.pocket;
import android.provider.BaseColumns;

public class DATA_TABLE {

	public DATA_TABLE()
	{
		
	}
	
	public static abstract class TableInfo implements BaseColumns
	{
		public static final String USER_NAME="user_name";
		public static final String TABLE_NAME="money_tab";
		
		public static final String MONEY="money";
		public static final String DATETIME="date_time";
		
		public static final String DATABASE_NAME="POCKET";
		
		public static final String OCCATION="occation";	
		public static final String TABLE_DLY="money_daily";
		
		public static final String TABLE_DLY_EXP="daily_total";
		public static final String DAY="day";
		public static final String DATE="date";
		public static final String TOTAL="total";
		
		public static final String TABLE_SUM="table_sum";
		public static final String SUM="daily_sum";
	}
	
	
	
	    
	   

	  
	
}
