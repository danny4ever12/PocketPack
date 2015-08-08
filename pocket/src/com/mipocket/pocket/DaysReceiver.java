package com.mipocket.pocket;

import com.mipocket.pocket.DATA_TABLE.TableInfo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class DaysReceiver extends BroadcastReceiver {
	public DaysReceiver() {
	}

	//for notification and for adding total daily expense to previous day
	@Override
	public void onReceive(Context context, Intent intent) {
		DBoperations DB=new DBoperations(context);
		Cursor cr=DB.getSum(DB);
	    cr.moveToFirst();
	    float cash = cr.getFloat(cr.getColumnIndex(TableInfo.SUM));
	    if (cash!=0.0)
	    {
		  DB.putDaysTotal(DB, cash);
		 Toast.makeText(context, "daystotal added", Toast.LENGTH_SHORT).show();
		  		  
	    }
	    
	    Cursor Mcr=DB.getInformation(DB);
	    if(Mcr.moveToFirst())
	    {
	    	String msg;
	    	String name = Mcr.getString(Mcr.getColumnIndex(TableInfo.USER_NAME));
        	float debt = Mcr.getFloat(Mcr.getColumnIndex(TableInfo.MONEY));	
        	if(debt<0)
        	{
        		Float k=-debt;
        		String amt=String.valueOf(k);
        		msg="you owe "+name+" Rs "+amt;
        		Notification(context, msg);
        	}
        	else
        	{
        		String amt=String.valueOf(debt);
        		msg=name+" owes you Rs "+amt;
        		Notification(context, msg);
        	}
	    }
	}
	
	public void Notification(Context context, String message) {
		// Set Notification Title
	
		// Open MoneyTab Class on Notification Click
		Intent intent = new Intent(context, MoneyTab.class);
		
		// Open NotificationView.java Activity
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
 
		// Create Notification using NotificationCompat.Builder
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context)
				// Set Icon
				.setSmallIcon(R.drawable.ic_launcher)
				// Set Ticker Message
				.setTicker(message)
				// Set Title
				.setContentTitle("mi pocket")
				// Set Text
				.setContentText(message)
				
				// Set PendingIntent into Notification
				.setContentIntent(pIntent)
				// Dismiss Notification
				.setAutoCancel(true);
 
		// Create Notification Manager
		NotificationManager notificationmanager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// Build Notification with Notification Manager
		notificationmanager.notify(0, builder.build());
 
	}
}
