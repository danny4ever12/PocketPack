package com.example.pocket;

import com.example.pocket.DATA_TABLE.TableInfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

public class DaysReceiver extends BroadcastReceiver {
	public DaysReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		DBoperations DB=new DBoperations(context);
		Cursor cr=DB.getSum(DB);
	    cr.moveToFirst();
	    float cash = cr.getFloat(cr.getColumnIndex(TableInfo.SUM));
	    if (cash!=0.0)
	    {
		  DB.putDaysTotal(DB, cash);
		  Toast.makeText(context,"days total added", Toast.LENGTH_SHORT).show();
	    }
		  
	}
}
